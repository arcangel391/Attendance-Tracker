package com.example.attendancetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

//import com.android.volley.Request;
//import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Dashboard extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private AnnouncementAdapter adapter;
    private ArrayList<AnnouncementsModel> announcementsArrayList;

    TextView  day, date;
    TextClock time;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button btnTime;

    Context context;
    String mess = "time in";
    int nswitch = 0;

    String uRl = "http://192.168.1.110/MCC-AttendanceTracker/v1/get_announcements.php";
    String uRl1 = "http://192.168.1.110/MCC-AttendanceTracker/v1/time.php";
    String uRl2 = "http://192.168.1.110/MCC-AttendanceTracker/v1/user_time_logs.php";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        time = (TextClock) view.findViewById(R.id.txtTime);
        date = (TextView)view.findViewById(R.id.txtDate);
        day = (TextView)view.findViewById(R.id.txtDay);
        btnTime = (Button)view.findViewById(R.id.btnAttendance);
        btnTime.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.recyclerAnnouncement);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                compareTime(time.getText().toString(), "05:00:00 PM");
                getDate();

            }
        });

        getDate();
        viewAnnouncements();
        return view;

    }

    private void viewAnnouncements(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET , uRl,(response) ->{

            try{
                JSONArray announcements = new JSONArray(response);
                announcementsArrayList = new ArrayList<>();

                for(int i=0; i<announcements.length(); i++){
                    JSONObject announcementsObject = announcements.getJSONObject(i);
                    String title = announcementsObject.getString("title");
                    String date = announcementsObject.getString("date");
                    String time = announcementsObject.getString("time");
                    String details = announcementsObject.getString("details");

                    AnnouncementsModel announcementsModel = new AnnouncementsModel(title, date, time, details);
                    announcementsArrayList.add(announcementsModel);


                }
                adapter = new AnnouncementAdapter(getContext(), announcementsArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e){

                e.printStackTrace();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void onClick (View v){
            message();

    }

    public void getDate(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET , uRl1,(response) ->{

            try{
                JSONObject obj = new JSONObject(response);

                String date1 = obj.getString("date");
                String day1 = obj.getString("day");

                date.setText(date1);
                day.setText(day1);


            } catch (JSONException e){

                e.printStackTrace();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void message (){


        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        internLogged();
                        clicked(btnTime,nswitch);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity() );
        builder.setMessage("Are you sure you want to "+ mess + "? \n (" + time.getText().toString() +")").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    public void clicked(Button b, int a){


        if(a==0){
            btnTime.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
            b.setText("TIME-OUT");
            mess = "time-out";
            nswitch = 1;
        }else{
            btnTime.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            b.setText("TIME-IN");
            b.setEnabled(false);
            mess = "time-in";
            nswitch = 0;
        }
    }

    public void compareTime(String inputTime, String time){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            Date timeInput = sdf.parse(inputTime);
            Date timeScheduled = sdf.parse(time);
            Date timeReset = sdf.parse("12:00:00 AM");

            if(btnTime.getText().toString().equalsIgnoreCase("time-out")){
                if(timeInput.equals(timeScheduled) || timeInput.after(timeScheduled)){
                   btnTime.setEnabled(true);
                }else{
                    btnTime.setEnabled(false);
                }
            }

            if(timeInput.equals(timeReset)){
                btnTime.setEnabled(true);
            }

        }catch (ParseException ex){
            ex.printStackTrace();
        }

    }

    private void internLogged(){
        final String userEmail = "intensityg36@gmail.com";
        final String btnName = btnTime.getText().toString().toUpperCase().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uRl2,
                (response) ->{

                        try{
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                if(btnName.equalsIgnoreCase("time-in")){
                                    editor.putInt("last_id", obj.getInt("last_id"));
                                    editor.commit();
                                }
                                Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params  = new HashMap<>();
                params.put("email", userEmail);
                params.put("btnName", btnName);
                params.put("last_id", String.valueOf(sharedPreferences.getInt("last_id", 0)));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }


}