package com.example.attendancetracker;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.attendancetracker.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard2);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navigationSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Dashboard()).commit();

    }

    private BottomNavigationView.OnItemSelectedListener navigationSelectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.mainActivity:
                    selectedFragment = new Dashboard();
                    break;
                case R.id.first2Fragment:
                    selectedFragment = new First2Fragment();
                    break;
                case R.id.second2Fragment:
                    selectedFragment = new Second2Fragment();
                    break;
                case R.id.fragment_third:
                    selectedFragment = new fragment_third();
                    break;
            }

            return true;
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard2);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}