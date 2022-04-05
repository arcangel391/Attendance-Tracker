package com.example.attendancetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

//import com.android.volley.Request;
//import com.android.volley.toolbox.StringRequest;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Dashboard extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private AnnouncementAdapter adapter;
    private ArrayList<AnnouncementsModel> announcementsArrayList;
    
    TextView  day, date;
    TextClock time;
    Button btnTime;

    Context context;
    String mess = "time in";
    int nswitch = 0;
    ListView listView;
    String uRl = "http://192.168.1.110/MCC-AttendanceTracker/v1/get_announcements.php";
    String uRl1 = "http://192.168.1.110/MCC-AttendanceTracker/v1/time.php";
    /*ArrayList<String> announceList = new ArrayList<String>();*/


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        time = (TextClock) view.findViewById(R.id.txtTime);
        date = (TextView)view.findViewById(R.id.txtDate);
        day = (TextView)view.findViewById(R.id.txtDay);
        btnTime = (Button)view.findViewById(R.id.btnAttendance);
        btnTime.setOnClickListener(this);


       // listView= (ListView)view.findViewById(R.id.listAnnounceView);
        recyclerView = view.findViewById(R.id.recyclerAnnouncement);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        time.setTimeZone("Asia/Manila");
        getTime();
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

    public void getTime(){
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
            b.setText("TIME OUT");
            mess = "time-out";
            nswitch = 1;
        }else{
            btnTime.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            b.setText("TIME IN");
            mess = "time-in";
            nswitch = 0;
        }
    }


}