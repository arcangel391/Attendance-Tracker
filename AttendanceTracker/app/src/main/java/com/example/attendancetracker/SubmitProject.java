package com.example.attendancetracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;



public class SubmitProject extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText department,task,dateassigned,googlelink;
    Spinner fileformat;
    Button submitproj;
    String dept,taskn,dateass,glink,ff,datesub;
    Integer fid= 267;
    TextView psback;
    String[] schemes = {"http","https"};
    Calendar calendar;
    String[] filef = {"IMAGE","DOCUMENT","PPTX","XLS","VIDEO","AUDIO","OTHERS"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_project_status);

        department = findViewById(R.id.txt_password);
        task = findViewById(R.id.txt_task);
        dateassigned = findViewById(R.id.txt_dateass);
        googlelink = findViewById(R.id.txt_link);
        fileformat = findViewById(R.id.spn_proj);
        submitproj = findViewById(R.id.btn_submitp);
        fileformat = findViewById(R.id.spn_proj);
        psback = findViewById(R.id.psback);
        StrictMode.enableDefaults();
        fileformat.setOnItemSelectedListener(this);
        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item,filef);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileformat.setAdapter(adapter);
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

                SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                dateassigned.setText(sdf.format(calendar.getTime()));
            }
        };
        dateassigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SubmitProject.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        submitproj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dept = URLEncoder.encode(department.getText().toString());
                taskn = URLEncoder.encode(task.getText().toString());
                dateass = dateassigned.getText().toString();
                glink = URLEncoder.encode(googlelink.getText().toString());
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                datesub = df.format(date);






                if(TextUtils.isEmpty(dept)){
                   department.setError("This field is empty");
                    return;
                }
                else if(TextUtils.isEmpty(taskn)){
                    task.setError("This field is empty");
                    return;
                }
                else if(TextUtils.isEmpty(dateass)){
                    dateassigned.setError("This field is empty");
                }
                else if(TextUtils.isEmpty(glink)){
                    googlelink.setError("This field is empty");
                }
                else if(!URLUtil.isValidUrl(googlelink.getText().toString())){
                    googlelink.setError("Must be a link");
                }
                else{


                   try {

                       HttpClient httpClient = new DefaultHttpClient();
                       HttpPost httpPost =  new HttpPost("http://192.168.1.4/crud/insert.php?userid="+fid+"&department="+dept+"&taskname="+taskn+"&dateassigned="+dateass+"&datesubmitted="+datesub+"&fileformat="+ff+"&gdrivelink="+glink);

                       HttpResponse response = httpClient.execute(httpPost);
                       Toast toast2 = Toast.makeText(getApplicationContext(),"Recorded Successfully",Toast.LENGTH_SHORT);
                       toast2.show();
                       finish();
                   }catch (Exception e){

                            Toast toast1 = Toast.makeText(getApplicationContext(),String.valueOf(e),Toast.LENGTH_LONG);
                            toast1.show();


                   }





                }
            }
        });

                    psback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ff= filef[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
