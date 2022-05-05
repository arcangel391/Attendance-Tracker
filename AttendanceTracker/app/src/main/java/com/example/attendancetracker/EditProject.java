package com.example.attendancetracker;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class EditProject extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button cancel,p_save;
String text1="";
Spinner fileformats;
EditText dept,t_name,date_ass,gd_link;
String spin ="",dep="",dep1="",tname="",tname1="",dateas="",dateas1="",gdlink="",gdlink1="",filefs="";
Calendar calendar;
String[] filef = {"IMAGE","DOCUMENT","PPTX","XLS","VIDEO","AUDIO","OTHERS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.edit_project);

        cancel = findViewById(R.id.btn_can);
        p_save = findViewById(R.id.p_save);
        dept = findViewById(R.id.txt_password);
        t_name = findViewById(R.id.t_name);
        date_ass = findViewById(R.id.date_ass);
        gd_link = findViewById(R.id.gd_link);
        fileformats = findViewById(R.id.fileformats);
        StrictMode.enableDefaults();

        fileformats.setOnItemSelectedListener(this);
        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item,filef);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileformats.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        String text = extras.getString("id");
        int stat=0;
        if (stat==1){
            dept.setEnabled(false);
        }
        else {
            dept.setEnabled(true);
        }


        new Connection().execute();


        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }
            private void updateCalendar(){

                SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                date_ass.setText(sdf.format(calendar.getTime()));
            }
        };


        date_ass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditProject.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });





        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        p_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dep1 = URLEncoder.encode(dept.getText().toString());
                tname1 = URLEncoder.encode(t_name.getText().toString());
                dateas1 = date_ass.getText().toString();
                gdlink1 = gd_link.getText().toString();
                int userid =Integer.parseInt(text);
                Bundle extras = getIntent().getExtras();
                int stat=0;
                if(extras!=null){
                    stat= Integer.parseInt(extras.getString("status"));
                }

                if(TextUtils.isEmpty(dept.getText().toString())&&stat==0){
                    dept.setError("This field is empty");

                }else if(TextUtils.isEmpty(t_name.getText().toString())){
                    t_name.setError("This field is empty");

                }else if(TextUtils.isEmpty(gd_link.getText().toString())){
                    gd_link.setError("This field is empty");

                }
                else if(!URLUtil.isValidUrl(gd_link.getText().toString())){
                    gd_link.setError("Enter a valid link");

                }else if(filefs.equals(spin)&&dep.equals(dept.getText().toString())&&tname.equals(t_name.getText().toString())&&gdlink.equals(gd_link.getText().toString())&&dateas.equals(date_ass.getText().toString())){
                  Toast toast1 = Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT);
                  toast1.show();
                }else {
                    try {
                        if(extras!=null){
                            stat= Integer.parseInt(extras.getString("status"));
                        }

                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost =  new HttpPost("http://192.168.1.4/crud/update.php?project_id="+userid+"&department="+dep1+"&taskname="+tname1+"&dateassigned="+dateas1+"&fileformat="+filefs+"&gdrivelink="+gdlink1);

                        if(stat==0){
                            httpPost =  new HttpPost("http://192.168.1.4/crud/update.php?project_id="+userid+"&department="+dep1+"&taskname="+tname1+"&dateassigned="+dateas1+"&fileformat="+filefs+"&gdrivelink="+gdlink1);


                        }else{
                           httpPost =  new HttpPost("http://192.168.1.4/crud/updates.php?project_id="+userid+"&taskname="+tname1+"&dateassigned="+dateas1+"&fileformat="+filefs+"&gdrivelink="+gdlink1);


                        }

                        HttpResponse response = httpClient.execute(httpPost);
                        Toast toast2 = Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT);
                        toast2.show();
                        finish();
                    }catch (Exception e){

                        Toast toast12 = Toast.makeText(getApplicationContext(),String.valueOf(e),Toast.LENGTH_LONG);
                        toast12.show();


                    }
                }


            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        filefs = filef[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            String host="";
            if(stat==0){
                host = "http://192.168.1.4/crud/show.php?project_id=" + id + "&userid=" + user;
            }else {
                 host = "http://192.168.1.4/crud/shows.php?project_id=" + id + "&userid=" + user;
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
                   text1 = proj.getString("task_name");

                    if(stat==0){
                        dept.setText(proj.getString("department"));

                    }else{




                    }
                   t_name.setText(proj.getString("task_name"));
                   date_ass.setText(proj.getString("date_assigned"));
                   gd_link.setText(proj.getString("gdrive_link"));
                   spin = proj.getString("file_formats");


                   dep =proj.getString("department");
                   tname=proj.getString("task_name");
                   dateas=proj.getString("date_assigned");
                   gdlink=proj.getString("gdrive_link");





                }
                if(spin.equals(filef[0])){
                    fileformats.setSelection(0);
                }else if(spin.equals(filef[1])){
                    fileformats.setSelection(1);
                }else if(spin.equals(filef[2])){
                    fileformats.setSelection(2);
                }else if(spin.equals(filef[3])){
                    fileformats.setSelection(3);
                }else if(spin.equals(filef[4])){
                    fileformats.setSelection(4);
                }else if(spin.equals(filef[5])){
                    fileformats.setSelection(5);
                }else {
                    fileformats.setSelection(6);
                }


            }catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}