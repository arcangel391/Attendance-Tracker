package com.example.attendancetracker;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import androidx.appcompat.app.AppCompatActivity;


public class ProjectView extends AppCompatActivity {
TextView psback;
Button btn_signin;
String text1 ="",link="";
TextView taskid ,fname,depart ,tname ,file_form,dateass ,datesub ,g_link;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.project_status_view);
            btn_signin = findViewById(R.id.btn_signin);
            psback = findViewById(R.id.psback);
        Bundle extras = getIntent().getExtras();
        int id=0;
        int user=0;
        int stat=0;
        if(extras!=null){
            id = Integer.parseInt(extras.getString("id"));
            user = Integer.parseInt(extras.getString("user"));
            stat= Integer.parseInt(extras.getString("status"));
        }
        taskid = findViewById(R.id.task_id);
                fname = findViewById(R.id.fname);
                depart = findViewById(R.id.dept);
                tname = findViewById(R.id.task_name);
                file_form = findViewById(R.id.file_form);
                dateass = findViewById(R.id.date_ass);
                datesub = findViewById(R.id.date_sub);
                g_link = findViewById(R.id.g_link);



            psback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        new Connection().execute();

        if(stat==1){
            btn_signin.setEnabled(false);
        }
        else {
            btn_signin.setEnabled(true);
        }

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                Intent in = new Intent(getApplicationContext(),EditProject.class);
                in.putExtra("id",extras.getString("id"));
                in.putExtra("user",extras.getString("user"));
                in.putExtra("status",extras.getString("status"));
                startActivity(in);
                finish();
            }
        });

        g_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ia = new Intent(Intent.ACTION_VIEW);
                ia.setData(Uri.parse(link));
                startActivity(ia);
            }
        });


    }


    class Connection extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            Bundle extras = getIntent().getExtras();
            int id=0;
            int user=0;
            int stat=0;
            if(extras!=null){
                id = Integer.parseInt(extras.getString("id"));
                user = Integer.parseInt(extras.getString("user"));
                stat= Integer.parseInt(extras.getString("status"));
            }
            String result = "asd";

            String host ="http://192.168.1.4/crud/show.php?project_id="+id+"&userid="+user;

            if(stat==0){
                 host ="http://192.168.1.4/crud/show.php?project_id="+id+"&userid="+user;

            }else{
              host ="http://192.168.1.4/crud/shows.php?project_id="+id+"&userid="+user;

            }

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

            int stat=0;
            if(extras!=null){
                stat= Integer.parseInt(extras.getString("status"));
            }

            try{

                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");
                JSONArray projs = jsonObject.getJSONArray("projs");
                for(int i=0; i<projs.length(); i++){
                    JSONObject proj = projs.getJSONObject(i);
                    text1 = proj.getString("firstname")+" "+proj.getString("lastname");
                    taskid.setText(extras.getString("id"));
                    fname.setText(text1);
                    if(stat==0){
                        depart.setText(proj.getString("department"));
                    }else{
                        depart.setText("Team Project");
                    }

                    tname.setText(proj.getString("task_name"));
                    file_form.setText(proj.getString("file_formats"));
                    dateass.setText(proj.getString("date_assigned"));
                    datesub.setText(proj.getString("date_submitted"));
                    link = proj.getString("gdrive_link");
                    g_link.setText("Gdrive link");



                }

            }catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }
}
