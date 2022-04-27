package com.example.attendancetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeamMonitoring extends AppCompatActivity {
    private Button btnReport1, btnReport2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_monitoring);

        btnReport1 = (Button) findViewById(R.id.btnReport1);
        btnReport2 = (Button) findViewById(R.id.btnReport2);
        btnReport1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TeamMonitoring.this);
                View view = getLayoutInflater().inflate(R.layout.layout_dialog,null);
                builder.setView(view);
                builder.setTitle("Report Team Member");
                builder.show();
            }
        });
    }
    public void openDialog(){

    }

}