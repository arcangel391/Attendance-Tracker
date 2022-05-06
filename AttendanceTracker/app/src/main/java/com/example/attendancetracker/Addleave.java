package com.example.attendancetracker;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Addleave extends AppCompatActivity {

    EditText etdepartment, etdatestart, etdateend, etreason, etdescription;
    TextView fl_back;
    String Department, DateStart, DateEnd, LeaveReason, LeaveReasonDetails;
    Button btnSave, btnCancel;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_leave_form);

        etdepartment = findViewById(R.id.etdepartment);
        etdatestart = findViewById(R.id.etdatestart);
        etdateend = findViewById(R.id.etdateend);
        etreason = findViewById(R.id.etreason);
        etdescription = findViewById(R.id.etdescription);
        fl_back = findViewById(R.id.fl_back);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }
            private void updateCalendar(){
                String Format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(Format,Locale.TAIWAN);

                etdatestart.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener date1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }
            private void updateCalendar(){
                String Format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(Format,Locale.TAIWAN);

                etdateend.setText(sdf.format(calendar.getTime()));
            }
        };
        etdatestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Addleave.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etdateend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Addleave.this, date1, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        StrictMode.enableDefaults();

        fl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getApplicationContext(),LeaveStatus.class);
                startActivity(i);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int UserID = 1;

                String Department = URLEncoder.encode(etdepartment.getText().toString());
                String LeaveReason = URLEncoder.encode(etreason.getText().toString());
                String LeaveReasonDetails = URLEncoder.encode(etdescription.getText().toString());
                String DateStart = URLEncoder.encode(etdatestart.getText().toString());
                String DateEnd = URLEncoder.encode(etdateend.getText().toString());





                if(TextUtils.isEmpty(etdepartment.getText().toString())){
                    etdepartment.setError("This field is empty");
                }
                else if(TextUtils.isEmpty(etreason.getText().toString())){
                    etreason.setError("This field is empty");
                }
                else if(TextUtils.isEmpty(etdescription.getText().toString())){
                    etdescription.setError("This field is empty");
                }
                else
                {

                    try {

                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost =  new HttpPost("http://192.168.56.1/android_API/crud_api/add.php?user_acc_id="+UserID+"&department="+Department+"&leave_type="+LeaveReason+"&reason_leave="+LeaveReasonDetails+"&leave_from="+DateStart+"&leave_to="+DateEnd);

                        HttpResponse response = httpClient.execute(httpPost);
                        Toast toast2 = Toast.makeText(getApplicationContext(), "File Leave Successful" ,Toast.LENGTH_SHORT);
                        toast2.show();
                        finish();
                        Intent i = new Intent(getApplicationContext(),LeaveStatus.class);
                        startActivity(i);
                    }catch (Exception e){

                        Toast toast1 = Toast.makeText(getApplicationContext(),String.valueOf(e),Toast.LENGTH_LONG);
                        toast1.show();


                    }

                }
            }

        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getApplicationContext(),LeaveStatus.class);
                startActivity(i);
            }
        });

    }
}
