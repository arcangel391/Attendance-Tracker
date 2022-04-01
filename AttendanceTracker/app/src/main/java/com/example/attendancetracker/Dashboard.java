package com.example.attendancetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class Dashboard extends Fragment {
    TextView  day, date;
    Button btnTime;
    Context context;
    String mess = "time in";
    int nswitch = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        this.context =context;
        btnTime = (Button)view.findViewById(R.id.btnAttendance);
        btnTime.setOnClickListener(this::clicked );





        return view;




    }

    public void clicked (View v){
        message();


    }




    //method for btn color switching
    public void clicked (Button btnTime1, int n ){


        if (n==0){
            btnTime1.setText("TIME OUT");
            btnTime1.setBackgroundColor(Color.RED);
            mess = "time-out";
            nswitch = 1;

        }
        else{
            btnTime1.setText("TIME IN");
            btnTime1.setBackgroundColor(Color.GREEN);
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

}}