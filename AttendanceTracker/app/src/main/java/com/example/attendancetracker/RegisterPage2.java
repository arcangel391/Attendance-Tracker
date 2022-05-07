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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterPage2 extends AppCompatActivity implements View.OnClickListener{
    final Calendar myCalendar= Calendar.getInstance();
    View footer;
    TextView previous, next;
    ImageView dot2, dot3, dot1;
    LinearLayout header;
    LinearLayout dotLayout;
    EditText startDate, endDate, requiredHrs;
    Spinner company, department;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page2);
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
        dot1.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        dot2 = footer.findViewById(R.id.dot2);
        dot3 = footer.findViewById(R.id.dot3);

        dot2.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        dot3.setBackgroundTintList(getResources().getColorStateList(R.color.sky));

        startDate = findViewById(R.id.et_sdate);
        endDate = findViewById(R.id.et_edate);
        requiredHrs = findViewById(R.id.et_reqhrs);
        company = findViewById(R.id.sp_company);
        department = findViewById(R.id.sp_department);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        getData();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData(startDate.getText().toString().trim(), endDate.getText().toString().trim(),
                        requiredHrs.getText().toString().trim(), company.getSelectedItem().toString().trim(),
                        department.getSelectedItem().toString().trim());
                Intent intent = new Intent(getApplicationContext(), RegisterPage3.class);
                Pair[] pairs = new Pair[4];

                pairs[0] = new Pair<View, String>(previous, "transition-previous-btn");
                pairs[1] = new Pair<View, String>(next, "transition-next-btn");
                pairs[2] = new Pair<View, String>(header, "transition-header");
                pairs[3] = new Pair<View, String>(dotLayout, "transition-dot");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterPage2.this,pairs);
                getWindow().setExitTransition(null);
                startActivity(intent, options.toBundle());
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData(startDate.getText().toString().trim(), endDate.getText().toString().trim(),
                        requiredHrs.getText().toString().trim(), company.getSelectedItem().toString().trim(),
                        department.getSelectedItem().toString().trim());
                Intent intent = new Intent(getApplicationContext(), RegisterPage1.class);
                Pair[] pairs = new Pair[4];

                pairs[0] = new Pair<View, String>(previous, "transition-previous-btn");
                pairs[1] = new Pair<View, String>(next, "transition-next-btn");
                pairs[2] = new Pair<View, String>(header, "transition-header");
                pairs[3] = new Pair<View, String>(dotLayout, "transition-dot");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterPage2.this,pairs);
                getWindow().setExitTransition(null);
                startActivity(intent, options.toBundle());

            }
        });

        String[] companyItems = {"Melham Construction Corporation", "Anafara", "Visvis"};
        String[] departmentItems = {"Information Technology", "Accounting"};
        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), R.layout.custom_spinner, companyItems);
        aa.setDropDownViewResource(R.layout.custom_spinner_background);
        company.setAdapter(aa);

        ArrayAdapter bb = new ArrayAdapter(getApplicationContext(), R.layout.custom_spinner, departmentItems);
        bb.setDropDownViewResource(R.layout.custom_spinner_background);
        department.setAdapter(bb);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_sdate:
                DatePickerDialog.OnDateSetListener date = updateLabel(startDate);
                new DatePickerDialog(RegisterPage2.this, date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.et_edate:
                DatePickerDialog.OnDateSetListener date1 = updateLabel(endDate);
                new DatePickerDialog(RegisterPage2.this, date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;


        }
    }

    private DatePickerDialog.OnDateSetListener updateLabel(EditText editText){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                String myFormat="yyyy/MM/dd";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                editText.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        return date;
    }

    private void getData(){
        if(sharedPreferences.contains("start_date")){
            startDate.setText(sharedPreferences.getString("start_date", "").trim());
        }
        if(sharedPreferences.contains("end_date")){
            endDate.setText(sharedPreferences.getString("end_date", "").trim());
        }
        if(sharedPreferences.contains("required_hours")){
            requiredHrs.setText(sharedPreferences.getString("required_hours", "").trim());
        }
        if(sharedPreferences.contains("company")){
            for(int i = 0; i< company.getAdapter().getCount(); i++){
                if(sharedPreferences.getString("company","").equals(company.getItemAtPosition(i))){
                    company.setSelection(i);
                }
            }
        }
        if(sharedPreferences.contains("department")){
            for(int i = 0; i< department.getAdapter().getCount(); i++){
                if(sharedPreferences.getString("department","").equals(department.getItemAtPosition(i))){
                    department.setSelection(i);
                }
            }
        }
    }

    private void inputData(String start, String end, String reqHrs, String companyName, String departmentName){
        editor.putString("start_date", start);
        editor.putString("end_date", end);
        editor.putString("required_hours", reqHrs);
        editor.putString("company", companyName);
        editor.putString("department", departmentName);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        editor.clear();
        editor.commit();
        super.onDestroy();
    }
}
