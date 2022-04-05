package com.example.attendancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;



import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;

public class AnnouncementsDetails extends AppCompatActivity {
    TextView titleCard,dateCard,detailsCard,backbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_announcements);

        titleCard = findViewById(R.id.textTitle);
        dateCard = findViewById(R.id.textDate);
        detailsCard = findViewById(R.id.textAnnouncements);
        backbtn = findViewById(R.id.backbtn);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String date = i.getStringExtra("date");
        String details = i.getStringExtra("details");

        detailsCard.setText(details);
        dateCard.setText(date);
        titleCard.setText(title);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AnnouncementsDetails.this,Navbar.class);
                startActivity(i);
                finish();
            }
        });

    }
}
