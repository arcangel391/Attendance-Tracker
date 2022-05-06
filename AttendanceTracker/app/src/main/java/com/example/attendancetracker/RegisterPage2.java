package com.example.attendancetracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;

public class RegisterPage2 extends AppCompatActivity {

    EditText startDate;
    EditText endDate;
    EditText requiredHrs;
    Spinner companyName;
    String[] company = {"Select Company", "Melham Construction Company", "Anafara Corporation", "VisVis Travel & Tours"};
    Spinner department;
    String[] dept = {"Select Department", "Information Technology", "Mechanical Engineering", "Mechatronics Engineering", "Industrial Engineering", "Petroleum Engineering", "Electrical Engineering", "Electronics Engineering", "Industrial Technology", "Civil Engineering", "Sales and Marketing Department", "Operations Department", "Creative Team Department", "Business Development Department", "Liaison Department", "Client Relations Department", "Social Media Marketing Department", "Human Resources Department", "Quality Control Department", "Learning and Development Department", "Manager", "General Manager", "Supervisor", "Officer-in-Charge", "Over all Officer-in-Charge"};
    TextView next2;
    TextView previous2;
    ImageView vpage;
    ImageView vpage1;
    ImageView vpage2;
    ImageView vpage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page2);


        requiredHrs = (EditText) findViewById(R.id.et_reqhrs);
        vpage = (ImageView) findViewById(R.id.vpage);
        vpage1 = (ImageView) findViewById(R.id.vpage1);
        vpage2 = (ImageView) findViewById(R.id.vpage2);
        vpage3 = (ImageView) findViewById(R.id.vpage3);
        next2 = (TextView) findViewById(R.id.tv_next2);

        companyName = (Spinner) findViewById(R.id.sp_company);
        ArrayAdapter<String> companyValue = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,company);
        companyName.setAdapter(companyValue);

        department = (Spinner) findViewById(R.id.sp_department);
        ArrayAdapter<String> departmentValue = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,dept);
        department.setAdapter(departmentValue);

        startDate = (EditText) findViewById(R.id.et_sdate);
        startDate.setKeyListener(null);
        Calendar sCalendar = Calendar.getInstance();
        final int sYear = sCalendar.get(Calendar.YEAR);
        final int sMonth = sCalendar.get(Calendar.MONTH);
        final int sDay = sCalendar.get(Calendar.DAY_OF_MONTH);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterPage2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int sYear, int sMonth, int sDay) {
                        sMonth = sMonth+1;
                        String sDate = sDay+"/"+sMonth+"/"+sYear;
                        startDate.setText(sDate);
                    }
                },sYear,sMonth,sDay);
                datePickerDialog.show();

            }
        });

        endDate = (EditText) findViewById(R.id.et_edate);
        endDate.setKeyListener(null);
        Calendar eCalendar = Calendar.getInstance();
        final int eYear = eCalendar.get(Calendar.YEAR);
        final int eMonth = eCalendar.get(Calendar.MONTH);
        final int eDay = eCalendar.get(Calendar.DAY_OF_MONTH);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterPage2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int eYear, int eMonth, int eDay) {
                        eMonth = eMonth+1;
                        String eDate = eDay+"/"+eMonth+"/"+eYear;
                        endDate.setText(eDate);
                    }
                },eYear,eMonth,eDay);
                datePickerDialog.show();

            }
        });

        next2 = (TextView) findViewById(R.id.tv_next2);
        next2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(RegisterPage2.this, RegisterPage3.class));
                String StartDate;
                String EndDate;
                String RequiredHours;
                String Company;
                String Department;

                StartDate = String.valueOf(startDate.getText());
                EndDate = String.valueOf(endDate.getText());
                RequiredHours = String.valueOf(requiredHrs.getText());
                Company = (companyName.getSelectedItem().toString());
                Department = (department.getSelectedItem().toString());
                if (!StartDate.equals("") && !EndDate.equals("") && !RequiredHours.equals("") && !Company.equals("") && !Department.equals("")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[5];
                            field[0] = "StartDate";
                            field[1] = "EndDate";
                            field[2] = "RequiredHours";
                            field[3] = "CompanyName";
                            field[4] = "Department";
                            String[] data = new String[5];
                            data[0] = "StartDate";
                            data[1] = "EndDate";
                            data[2] = "RequiredHours";
                            data[3] = "CompanyName";
                            data[4] = "Department";
                            PutData putData = new PutData("http://localhost/Register/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("")) {
                                        Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT);
                                        Intent intent = new Intent(getApplicationContext(), RegisterPage3.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT);
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vpage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage2.this, RegisterPage.class));
            }
        });

        vpage1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage2.this, RegisterPage1.class));
            }
        });

        vpage2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage2.this, RegisterPage2.class));
            }
        });

        vpage3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage2.this, RegisterPage3.class));
            }
        });

        previous2 = (TextView) findViewById(R.id.tv_previous2);
        previous2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage2.this, RegisterPage1.class));
            }
        });
    }


}