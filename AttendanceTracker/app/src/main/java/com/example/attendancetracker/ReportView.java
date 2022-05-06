package com.example.attendancetracker;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ReportView extends AppCompatActivity {

    TextView etTitle, etDate,etDetails, etLink, back;
    String text1 = "";
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.view_report);

        back = findViewById(R.id.tvBack);
        etTitle = findViewById(R.id.etTime);
        etDate = findViewById(R.id.etDate);
        etDetails = findViewById(R.id.etDetails);
        etLink = findViewById(R.id.etLink);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new Connection().execute();



        etLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ia = new Intent(Intent.ACTION_VIEW);
                ia.setData(Uri.parse(link));
                startActivity(ia);
            }
        });
    }
    class Connection extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            Bundle extras = getIntent().getExtras();
            int id=0;
            int user=0;
            if(extras!=null){
                id = Integer.parseInt(extras.getString("id"));
                user = Integer.parseInt(extras.getString("user"));
            }
            String result = "asd";
            String host ="http://192.168.1.19/upload_file/project.php?report_id="+id;

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
                    text1 = proj.getString("report_subject");
                    etTitle.setText(text1);
                    etDate.setText(proj.getString("date_submitted"));
                    etDetails.setText(proj.getString("report_details"));

                    link = proj.getString("gdrive_link");
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }
}


