package com.example.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SubmitReport extends AppCompatActivity {

    TextView back;
    EditText etTitle, etDetails, etDrive;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_report);

        back = findViewById(R.id.tvBack);
        etTitle = findViewById(R.id.etTime);
        etDetails = findViewById(R.id.etDetails);
        etDrive = findViewById(R.id.etDrive);
        btnSubmit = findViewById(R.id.btnSubmit);

        StrictMode.enableDefaults();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int fid = 1;

                String reportsubject = URLEncoder.encode(etTitle.getText().toString());
                String reportdetails = URLEncoder.encode(etDetails.getText().toString());
                String drive = URLEncoder.encode(etDrive.getText().toString());
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                String datesub = URLEncoder.encode(df.format(date));




                if(TextUtils.isEmpty(etTitle.getText().toString())){
                    etTitle.setError("This field is empty");
                }
               else if(TextUtils.isEmpty(etDetails.getText().toString())){
                    etDetails.setError("This field is empty");
                }
               else if(TextUtils.isEmpty(etDrive.getText().toString())){
                    etDrive.setError("This field is empty");
                }
                else if(!URLUtil.isValidUrl(etDrive.getText().toString())){
                    etDrive.setError("Must be link");
                }
                else
                {

                    try {

                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost =  new HttpPost("http://192.168.1.19/upload_file/upload.php?user_acc_id="+fid+"&report_subject="+reportsubject+"&report_details="+reportdetails+"&gdrive_link="+drive+"&date_submitted="+datesub);

                        HttpResponse response = httpClient.execute(httpPost);
                        Toast toast2 = Toast.makeText(getApplicationContext(), "Report Successfully" ,Toast.LENGTH_SHORT);
                        toast2.show();
                        finish();
                    }catch (Exception e){

                        Toast toast1 = Toast.makeText(getApplicationContext(),String.valueOf(e),Toast.LENGTH_LONG);
                        toast1.show();


                    }

                }
                }

        });
    }
}

