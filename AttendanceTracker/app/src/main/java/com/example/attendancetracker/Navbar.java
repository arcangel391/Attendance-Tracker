package com.example.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Navbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navigationSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Dashboard()).commit();

    }

    private BottomNavigationView.OnItemSelectedListener navigationSelectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.firstFragment:
                    selectedFragment = new Dashboard();
                    break;
                case R.id.secondFragment:
                    selectedFragment = new ViewProfile();
                    break;
                case R.id.thirdFragment:
                    selectedFragment = new ProjectStatus();
                    break;
                case R.id.fourthFragment:
                    selectedFragment = new Reports();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };





}