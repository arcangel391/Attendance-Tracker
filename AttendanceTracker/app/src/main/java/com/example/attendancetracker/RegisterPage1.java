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

public class RegisterPage1 extends AppCompatActivity {

    EditText streetBldg;
    EditText barangay;
    EditText city;
    Spinner spGender;
    String[] gender = {"Select", "Male", "Female"};
    Spinner spCivilStatus;
    String[] civilStat = {"Select", "Single", "Married", "Divorced", "Widowed"};
    EditText birthDate;
    TextView next1;
    TextView previous1;
    ImageView page;
    ImageView page1;
    ImageView page2;
    ImageView page3;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page1);

        streetBldg = (EditText) findViewById(R.id.et_streetbldg);
        barangay = (EditText) findViewById(R.id.et_brgy);
        city = (EditText) findViewById(R.id.et_city);
        page = (ImageView) findViewById(R.id.page);
        page1 = (ImageView) findViewById(R.id.page1);
        page2 = (ImageView) findViewById(R.id.page2);
        page3 = (ImageView) findViewById(R.id.page3);

        spGender = (Spinner) findViewById(R.id.sp_gender);
        ArrayAdapter<String> genderValue = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,gender);
        spGender.setAdapter(genderValue);

        spCivilStatus = (Spinner) findViewById(R.id.sp_civilstat);
        ArrayAdapter<String> civilStatValue = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,civilStat);
        spCivilStatus.setAdapter(civilStatValue);

        birthDate = (EditText) findViewById(R.id.et_birthdate);
        birthDate.setKeyListener(null);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterPage1.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        birthDate.setText(date);

                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        next1 = (TextView) findViewById(R.id.tv_next1);
        next1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(RegisterPage1.this, RegisterPage2.class));
                String StreetBuilding;
                String Barangay;
                String City;
                String Gender;
                String CivilStatus;
                String Birthdate;

                StreetBuilding = String.valueOf(streetBldg.getText());
                Barangay = String.valueOf(barangay.getText());
                City = String.valueOf(city.getText());
                Gender = (spGender.getSelectedItem().toString());
                CivilStatus = (spCivilStatus.getSelectedItem().toString());
                Birthdate = String.valueOf(birthDate.getText());

                if (!StreetBuilding.equals("") && !Barangay.equals("") && !City.equals("") && !Gender.equals("") && !CivilStatus.equals("") && !Birthdate.equals("")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[6];
                            field[0] = "StreetBuilding";
                            field[1] = "Barangay";
                            field[2] = "Gender";
                            field[3] = "City";
                            field[4] = "CivilStatus";
                            field[5] = "Birthdate";
                            String[] data = new String[6];
                            data[0] = "StreetBuilding";
                            data[1] = "Barangay";
                            data[2] = "Gender";
                            data[3] = "City";
                            data[4] = "CivilStatus";
                            data[5] = "Birthdate";
                            PutData putData = new PutData("http://localhost/Register/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("")) {
                                        Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT);
                                        Intent intent = new Intent(getApplicationContext(), RegisterPage2.class);
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



        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage1.this, RegisterPage.class));
            }
        });

        page1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage1.this, RegisterPage1.class));
            }
        });

        page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage1.this, RegisterPage2.class));
            }
        });

        page3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage1.this, RegisterPage3.class));
            }
        });

        previous1 = (TextView) findViewById(R.id.tv_previous1);
        previous1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage1.this, RegisterPage.class));
            }
        });
    }


}