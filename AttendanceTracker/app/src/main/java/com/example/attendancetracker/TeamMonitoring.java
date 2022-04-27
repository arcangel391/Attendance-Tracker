package com.example.attendancetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class TeamMonitoring extends AppCompatActivity {
    private Button btnReport1, btnReport2,btnMember,btnLeader;
    private RecyclerView recyclerView2;
    String uRlMember = "http://192.168.1.5/MCC-AttendanceTracker/v1/get_monitoringMember.php";
    String uRlLeader = "http://192.168.1.5/MCC-AttendanceTracker/v1/get_monitoringLeader.php";
    private ArrayList<MonitoringModel> monitoringModelArrayList;
    private MonitoringAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_monitoring);

        btnReport1 = (Button) findViewById(R.id.btnReport1);
        btnReport2 = (Button) findViewById(R.id.btnReport2);
        btnMember = (Button) findViewById(R.id.btnMembers);
        btnLeader = (Button) findViewById(R.id.btnLeaders);
        recyclerView2 = findViewById(R.id.recyclerMonitoring);
        recyclerView2.setLayoutManager(new LinearLayoutManager(TeamMonitoring.this));
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

        //default recyclerload
        viewMonitoring(uRlLeader);


// leader btn clicked
        btnLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fetch data
                viewMonitoring(uRlLeader);
            }
        });


        // member btn clicked
        btnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////fetch data
                viewMonitoring(uRlMember);
            }
        });

    }


    private void viewMonitoring(String Url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET , Url,(response) ->{

            try{
                JSONArray monitoring = new JSONArray(response);
                monitoringModelArrayList = new ArrayList<>();

                for(int i=0; i<monitoring.length(); i++){
                    JSONObject monitoringObject = monitoring.getJSONObject(i);
                    String name = monitoringObject.getString("MonitoringName");
                    String role = monitoringObject.getString("MonitoringRole");
                    String team = monitoringObject.getString("MonitoringTeam");


                    MonitoringModel monitoringModel = new MonitoringModel(name, role, team);
                    monitoringModelArrayList.add(monitoringModel);


                }
                adapter = new MonitoringAdapter(TeamMonitoring.this, monitoringModelArrayList);
                recyclerView2.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e){

                e.printStackTrace();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TeamMonitoring.this , error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(TeamMonitoring.this .getApplicationContext());
        requestQueue.add(stringRequest);
    }


}