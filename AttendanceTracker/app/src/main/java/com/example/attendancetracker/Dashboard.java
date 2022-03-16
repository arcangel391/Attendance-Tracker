package com.example.attendancetracker;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class Dashboard extends AppCompatActivity {
    TextView  day, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        day = findViewById(R.id.txtDay);
        date = findViewById(R.id.txtDate);

        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        String[] splitDate = formattedDate.split(",");

        Log.d("myLog", currentTime.toString());
        Log.d("myLog", formattedDate);
        day.setText(splitDate[0]);
        date.setText(splitDate[1]+","+splitDate[2]);
        Log.d("myLog", splitDate[0].trim());
        Log.d("myLog", splitDate[1].trim());
        Log.d("myLog", splitDate[2].trim());

    }
}