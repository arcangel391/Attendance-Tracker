package com.example.attendancetracker;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterPage1 extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    View footer;
    TextView previous, next;
    ImageView dot1, dot2;
    LinearLayout header;
    LinearLayout dotLayout;
    EditText street,barangay,city,birthdate;
    Spinner gender, civilStatus;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page1);
        getWindow().setEnterTransition(null);
        sharedPreferences = getApplicationContext().getSharedPreferences("RegisterSharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();



        footer = findViewById(R.id.footer);
        previous = footer.findViewById(R.id.btnPrevious);
        next = footer.findViewById(R.id.btnNext);
        previous.setVisibility(View.VISIBLE);

        header = findViewById(R.id.header);
        dotLayout = footer.findViewById(R.id.dotLayout);
        dot1 = footer.findViewById(R.id.dot1);
        dot2 = footer.findViewById(R.id.dot2);
        dot1.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        dot2.setBackgroundTintList(getResources().getColorStateList(R.color.sky));

        street = findViewById(R.id.et_streetbldg);
        barangay = findViewById(R.id.et_brgy);
        city = findViewById(R.id.et_city);
        birthdate =findViewById(R.id.et_birthdate);
        gender = findViewById(R.id.sp_gender);
        civilStatus = findViewById(R.id.sp_civilstat);



        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterPage1.this, date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        String[] genderItems = {"Male", "Female", "Rather Not Say"};
        String[] civilStatusItems = {"Single", "Married", "Widowed"};
        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), R.layout.custom_spinner, genderItems);
        aa.setDropDownViewResource(R.layout.custom_spinner_background);
        gender.setAdapter(aa);

        ArrayAdapter bb = new ArrayAdapter(getApplicationContext(), R.layout.custom_spinner, civilStatusItems);
        bb.setDropDownViewResource(R.layout.custom_spinner_background);
        civilStatus.setAdapter(bb);
        getData();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData(street.getText().toString().trim(), barangay.getText().toString().trim(),
                        city.getText().toString().trim(), birthdate.getText().toString().trim(),
                        gender.getSelectedItem().toString().trim(), civilStatus.getSelectedItem().toString().trim());
                Intent intent = new Intent(getApplicationContext(), RegisterPage2.class);
                Pair[] pairs = new Pair[4];

                pairs[0] = new Pair<View, String>(previous, "transition-previous-btn");
                pairs[1] = new Pair<View, String>(next, "transition-next-btn");
                pairs[2] = new Pair<View, String>(header, "transition-header");
                pairs[3] = new Pair<View, String>(dotLayout, "transition-dot");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterPage1.this,pairs);
                getWindow().setExitTransition(null);
                startActivity(intent, options.toBundle());
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData(street.getText().toString().trim(), barangay.getText().toString().trim(),
                        city.getText().toString().trim(), birthdate.getText().toString().trim(),
                        gender.getSelectedItem().toString().trim(), civilStatus.getSelectedItem().toString().trim());
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                Pair[] pairs = new Pair[4];

                pairs[0] = new Pair<View, String>(previous, "transition-previous-btn");
                pairs[1] = new Pair<View, String>(next, "transition-next-btn");
                pairs[2] = new Pair<View, String>(header, "transition-header");
                pairs[3] = new Pair<View, String>(dotLayout, "transition-dot");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterPage1.this,pairs);
                getWindow().setExitTransition(null);
                startActivity(intent, options.toBundle());
            }
        });
    }

    private void updateLabel(){
        String myFormat="yyyy/MM/dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        birthdate.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    protected void onDestroy() {
        editor.clear();
        editor.commit();
        super.onDestroy();
    }

    private void getData(){
        if(sharedPreferences.contains("street")){
            street.setText(sharedPreferences.getString("street", ""));
        }
        if(sharedPreferences.contains("barangay")){
            barangay.setText(sharedPreferences.getString("barangay", ""));
        }
        if(sharedPreferences.contains("city")){
            city.setText(sharedPreferences.getString("city", ""));
        }
        if (sharedPreferences.contains("birthdate")){
            birthdate.setText(sharedPreferences.getString("birthdate", ""));
        }
        if(sharedPreferences.contains("gender")){
            for(int i = 0; i< gender.getAdapter().getCount(); i++){
                if(sharedPreferences.getString("gender", "").equals(gender.getItemAtPosition(i))){
                    gender.setSelection(i);
                }
            }

        }
        if(sharedPreferences.contains("civil_status")){
            for(int i = 0; i< civilStatus.getAdapter().getCount(); i++){
                if(sharedPreferences.getString("civil_status", "").equals(civilStatus.getItemAtPosition(i))){
                    civilStatus.setSelection(i);
                }
            }
        }

    }

    private void inputData(String street, String barangay, String city, String birthdate, String gender, String civil_status){
        editor.putString("street", street);
        editor.putString("barangay", barangay);
        editor.putString("city", city);
        editor.putString("birthdate", birthdate);
        editor.putString("gender", gender);
        editor.putString("civil_status", civil_status);
        editor.commit();
    }
}
