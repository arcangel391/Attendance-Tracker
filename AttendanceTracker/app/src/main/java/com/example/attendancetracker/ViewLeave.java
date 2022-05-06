package com.example.attendancetracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class ViewLeave extends AppCompatActivity {
TextView leaveid, username, group, etreason, start, end, status, l_back;
Button cancelleave;
Integer l_id=0;
    int id=0;
    boolean a;
    int stats=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.leave_status_view);

        leaveid = findViewById(R.id.leaveid);
        username = findViewById(R.id.username);
        group = findViewById(R.id.group);
        etreason = findViewById(R.id.etreason);
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        status = findViewById(R.id.status);
        l_back = findViewById(R.id.l_back);


        StrictMode.enableDefaults();

        Bundle extras = getIntent().getExtras();

        int user=0;
        if(extras!=null){
            id = Integer.parseInt(extras.getString("id"));
            user = Integer.parseInt(extras.getString("userID"));
        }
        cancelleave = findViewById(R.id.cancelleave);

        cancelleave.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (l_id==3){


                } else{
                    try{
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost =  new HttpPost("http://192.168.56.1/android_API/crud_api/updateleave.php?leave_id="+id);
                        HttpResponse response = httpClient.execute(httpPost);
                        Toast toast2 = Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT);
                        toast2.show();
                        finish();
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        l_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        new Connection().execute();


    }
    class Connection extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            Bundle extras = getIntent().getExtras();
            int id=0;
            int user=0;
            if(extras!=null){
                id = Integer.parseInt(extras.getString("id"));
                user = Integer.parseInt(extras.getString("userID"));
            }
            String result = "asd";
            String host ="http://192.168.56.1/android_API/crud_api/viewleave.php?leave_id="+id+"&user_acc_id="+user;

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

            Bundle extras = getIntent().getExtras();

            try{

                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");
                JSONArray projs = jsonObject.getJSONArray("projs");
                for(int i=0; i<projs.length(); i++){
                    JSONObject proj = projs.getJSONObject(i);
                    leaveid.setText(proj.getString("leave_id"));
                    username.setText(proj.getString("firstname")+" "+proj.getString("lastname"));
                    group.setText(proj.getString("department"));
                    etreason.setText(proj.getString("leave_type"));
                    start.setText(proj.getString("leave_from"));
                    end.setText(proj.getString("leave_to"));
                    l_id = Integer.parseInt(proj.getString("leave_status"));
                    stats = Integer.parseInt(proj.getString("leave_status"));


                    if (stats==0){
                        status.setText("Pending");
                    }else if (stats==1){
                        status.setText("Confirmed");
                        status.setTextColor(Color.parseColor("#81c639"));
                    } else if(stats==2){
                        status.setText("Declined");
                        status.setTextColor(Color.parseColor("#c6393a"));
                    } else{
                        status.setText("Cancelled");
                        status.setTextColor(Color.parseColor("#000000"));
                    }

                }

            }catch (JSONException e) {
                e.printStackTrace();
            }
            if (l_id.equals(0)){
                cancelleave.setEnabled(true);
            }else {
                cancelleave.setEnabled(false);
            }





        }

    }
}


