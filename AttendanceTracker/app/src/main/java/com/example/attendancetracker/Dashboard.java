package com.example.attendancetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

//import com.android.volley.Request;
//import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Dashboard extends Fragment {
    TextView  day, date, time;
    Button btnTime;
    Context context;
    String mess = "time in";
    int nswitch = 0;
    ListView listView;
    String uRl = "http://192.168.1.50/MCC-AttendanceTracker/v1/view_announcements.php";
    ArrayList<String> announceList = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        time = (TextView)view.findViewById(R.id.digitalClock);
        btnTime = (Button)view.findViewById(R.id.btnAttendance);
        btnTime.setOnClickListener(this::clickTimeBtn );
       // listView= (ListView)view.findViewById(R.id.listAnnounceView);

        return view;




    }

    /*
    private void viewAnnouncements(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uRl,(response) ->{
            announceList.clear();

            try{
                JSONArray announceResponce = new JSONArray(response);

                for(int i=0; i<announceResponce.length(); i++){
                    JSONObject announcementsObject = announceResponce.getJSONObject(i);
                    announceList.add(announcementsObject.getString("id"));


                    // adapter
                }


            } catch (JSONException e){

                e.printStackTrace();
            }


        }, (error)->{


        });

    }
*/

    public void clickTimeBtn (View v){
        message();
    }






    //method for btn color switching
    public void clicked (Button btnTime1, int n ){

        if (n==0){
            btnTime1.setText("TIME OUT");
            btnTime1.setBackgroundColor(btnTime1.getContext().getResources().getColor(R.color.red));
            mess = "time-out";
            nswitch = 1;

        }
        else{
            btnTime1.setText("TIME IN");
            btnTime1.setBackgroundColor(btnTime1.getContext().getResources().getColor(R.color.green));
            mess = "time-in";
            nswitch = 0;

        }


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

}}