package com.example.attendancetracker;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendanceLog extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AttendanceLogAdapter adapter;
    private ArrayList<AttendanceLogModel> attendanceLogArrayList;

    TextView refresh, filter;
    EditText txtSearch;

    String uRl = "http://192.168.1.110/MCC-AttendanceTracker/v1/get_attendance_log.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_log);

        refresh = findViewById(R.id.txtRefresh);
        filter = findViewById(R.id.txtFilter);
        txtSearch = findViewById(R.id.tbxSearch);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAttendanceLogs();
            }
        });

        filter.setText("\uf0b0");

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filter.getText().toString().equalsIgnoreCase("\uf0b0") ||
                filter.getText().toString().equalsIgnoreCase("\uf160")){
                    filter.setText("\uf161");
                    getAttendanceLogs();
                }else if(filter.getText().toString().equalsIgnoreCase("\uf161")){
                    filter.setText("\uf160");
                    getAttendanceLogs();
                }
            }
        });

        recyclerView = findViewById(R.id.attendanceRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getAttendanceLogs();

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getAttendanceLogs();
            }
        });


    }

    private void getAttendanceLogs(){
        final String userEmail = "intensityg36@gmail.com";
        final String search = txtSearch.getText().toString().trim();
        String filterBy = "NA";

        if(filter.getText().toString().trim().equalsIgnoreCase("\uf160")){
            filterBy = "ASC";
        }else{
            filterBy = "DESC";
        }

        final String userFilter = filterBy;

        StringRequest stringRequest = new StringRequest(Request.Method.POST , uRl,(response) ->{

            try{
                JSONArray attendanceLog = new JSONArray(response);
                attendanceLogArrayList = new ArrayList<>();

                for(int i=0; i<attendanceLog.length(); i++){
                    JSONObject announcementsObject = attendanceLog.getJSONObject(i);
                    String date = announcementsObject.getString("date");
                    String day = announcementsObject.getString("day");
                    String time_in = announcementsObject.getString("time_in");
                    String time_out = announcementsObject.getString("time_out");

                    AttendanceLogModel attendanceLogModel = new AttendanceLogModel(date, day, time_in, time_out);
                    attendanceLogArrayList.add(attendanceLogModel);


                }

                adapter = new AttendanceLogAdapter(AttendanceLog.this, attendanceLogArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } catch (JSONException e){

                e.printStackTrace();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params  = new HashMap<>();
                params.put("email", userEmail);
                params.put("search", search);
                params.put("filter", userFilter);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}
