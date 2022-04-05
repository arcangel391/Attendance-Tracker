package com.example.attendancetracker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Map;

public class AttendanceLog extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AttendanceLogAdapter adapter;
    private ArrayList<AttendanceLogModel> attendanceLogArrayList;

    String uRl = "http://192.168.1.110/MCC-AttendanceTracker/v1/get_attendance_log.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_log);

        recyclerView = findViewById(R.id.attendanceRecyclerView);
        getAttendanceLogs();


    }

    private void getAttendanceLogs(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET , uRl,(response) ->{

            try{
                JSONArray attendanceLog = new JSONArray(response);
                attendanceLogArrayList = new ArrayList<>();

                for(int i=0; i<attendanceLog.length(); i++){
                    JSONObject announcementsObject = attendanceLog.getJSONObject(i);
                    String date = announcementsObject.getString("date");
                    String day = announcementsObject.getString("day");
                    String hours_left = announcementsObject.getString("hours_left");
                    String rendered_hours = announcementsObject.getString("rendered_hours");
                    String time_in = announcementsObject.getString("time_in");
                    String time_out = announcementsObject.getString("time_out");

                    AttendanceLogModel attendanceLogModel = new AttendanceLogModel(date, day, rendered_hours, time_in, time_out, hours_left);
                    attendanceLogArrayList.add(attendanceLogModel);


                }
                adapter = new AttendanceLogAdapter(getApplicationContext(), attendanceLogArrayList);
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
                
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
