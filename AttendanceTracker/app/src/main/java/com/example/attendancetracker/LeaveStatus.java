package com.example.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class LeaveStatus extends AppCompatActivity {
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> userID = new ArrayList<>();
    ArrayList<String> LeaveReason = new ArrayList<>();
    ArrayList<String> DateStart = new ArrayList<>();
    ArrayList<String> LeaveStatus = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    int click = 0;
    TextView l_refresh, l_filter;
    EditText l_search;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> filterListui = new ArrayList<>();
    ArrayList<String> filterListt = new ArrayList<>();
    ArrayList<String> filterListd = new ArrayList<>();
    ArrayList<String> filterListu = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_status);
        new Connection().execute();
        floatingActionButton = findViewById(R.id.addleave);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),Addleave.class);
               finish();
               startActivity(intent);
            }
        });

    }

    class Connection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "asd";
            String host ="http://192.168.56.1/android_API/crud_api/select.php";

            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer = new StringBuffer("");

                String line ="";
                while((line= reader.readLine()) != null){
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result = stringBuffer.toString();
            }
            catch (Exception e){

                return new String(e.getMessage());
            }




            return result;
        }
        @Override
        protected void onPostExecute(String result){


            recyclerView = findViewById(R.id.list);
            l_search = findViewById(R.id.l_search);
            l_filter = findViewById(R.id.l_filter);
            l_refresh = findViewById(R.id.l_refresh);

            try {
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");
                JSONArray projs = jsonObject.getJSONArray("projs");
                for(int i=0; i<projs.length(); i++){
                    JSONObject leave = projs.getJSONObject(i);
                    id.add(String.valueOf(leave.getInt("leave_id")));
                    userID.add(String.valueOf(leave.getInt("user_acc_id")));
                    LeaveReason.add(leave.getString("leave_type"));
                    DateStart.add(leave.getString("leave_from"));
                    LeaveStatus.add(leave.getString("leave_status"));





                }

                LeaveAdapter adapter = new LeaveAdapter(id,LeaveReason,DateStart,LeaveStatus,userID);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);



                l_filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (click==0){
                            linearLayoutManager.setReverseLayout(true);
                            linearLayoutManager.setStackFromEnd(true);
                            click=1;
                        }
                        else {
                            linearLayoutManager.setReverseLayout(false);
                            linearLayoutManager.setStackFromEnd(false);
                            click=0;
                        }
                    }
                });



                l_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });
                l_search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        filterListui.clear();
                        filterListt.clear();
                        filterListd.clear();
                        filterListu.clear();

                        String asd = s.toString();
                        String reason = "usd";
                        String l_id = "";
                        String start = "";
                        String stat = "";


                        for (int i=0; i<LeaveReason.size(); i++){
                            reason=LeaveReason.get(i);
                            l_id=id.get(i);
                            start=DateStart.get(i);
                            stat=LeaveStatus.get(i);


                            if(reason.toLowerCase().contains(asd.toLowerCase())){
                                filterListt.add(reason);
                                filterListui.add(l_id);
                                filterListd.add(start);
                                filterListu.add(stat);
                            }
                        }
                        if (!filterListui.isEmpty()){
                            adapter.filterList(filterListui,filterListt,filterListd,filterListu);

                        }


                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }


}