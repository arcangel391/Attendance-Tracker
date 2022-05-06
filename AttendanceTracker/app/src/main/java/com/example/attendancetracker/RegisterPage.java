package com.example.attendancetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegisterPage extends AppCompatActivity {


    EditText lastName, firstName, middleName, contactNumber, email;
    TextView next;
    ImageView iv_page;
    ImageView iv_page1;
    ImageView iv_page2;
    ImageView iv_page3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        lastName = findViewById(R.id.et_lastname);
        firstName = findViewById(R.id.et_firstname);
        middleName = findViewById(R.id.et_middlename);
        contactNumber = findViewById(R.id.et_contact);
        email = findViewById(R.id.et_email);
        iv_page = (ImageView) findViewById(R.id.iv_page);
        iv_page1 = (ImageView) findViewById(R.id.iv_page1);
        iv_page2 = (ImageView) findViewById(R.id.iv_page2);
        iv_page3 = (ImageView) findViewById(R.id.iv_page3);



        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("lastName", "");
        editor.apply();


        next = (TextView) findViewById(R.id.tv_next);
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(RegisterPage.this, RegisterPage1.class));
                String LastName;
                String FirstName;
                String MiddleName;
                String ContactNumber;
                String Email;
                LastName = String.valueOf(lastName.getText());
                FirstName = String.valueOf(firstName.getText());
                MiddleName = String.valueOf(middleName.getText());
                ContactNumber = String.valueOf(contactNumber.getText());
                Email = String.valueOf(email.getText());
                if (!LastName.equals("") && !FirstName.equals("") && !MiddleName.equals("") && !ContactNumber.equals("") && !Email.equals("")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[5];
                            field[0] = "LastName";
                            field[1] = "FirstName";
                            field[2] = "MiddleName";
                            field[3] = "ContactNumber";
                            field[4] = "Email";
                            String[] data = new String[5];
                            data[0] = "LastName";
                            data[1] = "FirstName";
                            data[2] = "MiddleName";
                            data[3] = "ContactNumber";
                            data[4] = "Email";
                            PutData putData = new PutData("http://localhost/Register/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                String result = putData.getResult();
                                    if (result.equals("")) {
                                        //startActivity(new Intent(RegisterPage.this, RegisterPage1.class));
                                        Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT);
                                        Intent intent = new Intent(getApplicationContext(), RegisterPage1.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT);
                                    }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
                //


            }
        });


        iv_page3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage.this, RegisterPage3.class));
            }
        });

        iv_page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage.this, RegisterPage2.class));
            }
        });

        iv_page1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage.this, RegisterPage1.class));
            }
        });

        iv_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage.this, RegisterPage.class));
            }
        });

//        next.setOnClickListener(new View.OnClickListener() {
//            String lName = lastName.getEditText().toString();
//            String fName = firstName.getEditText().toString();
//            String mName = middleName.getEditText().toString();
//            @Override
//            public void onClick(View view) {
//                if (!validateName() | !validateContact() | !validateEmail()) {
//                    return;
//                }
//                Intent intent = new Intent(getApplicationContext(), RegisterPage1.class);
//                intent.putExtra("LastName", lName);
//                intent.putExtra("FirstName", fName);
//                intent.putExtra("MiddleName", mName);
//            }
//        });

//        public void callNextRegister(View view) {
//
//            if (!validateName() | !validateContact() | !validateEmail()) {
//                return;
//            }
//
//
//
//        }

    }
//    public void callNext(View view) {
//        String LastName;
//        String FirstName;
//        String MiddleName;
//        String ContactNumber;
//        String Email;
//        LastName = String.valueOf(lastName.getText());
//        FirstName = String.valueOf(firstName.getText());
//        MiddleName = String.valueOf(middleName.getText());
//        ContactNumber = String.valueOf(contactNumber.getText());
//        Email = String.valueOf(email.getText());
//        if (!LastName.equals("") && !FirstName.equals("") && !MiddleName.equals("") && !ContactNumber.equals("") && !Email.equals("")) {
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    String[] field = new String[5];
//                    field[0] = "LastName";
//                    field[1] = "FirstName";
//                    field[2] = "MiddleName";
//                    field[3] = "ContactNumber";
//                    field[4] = "Email";
//                    String[] data = new String[5];
//                    data[0] = "LastName";
//                    data[1] = "FirstName";
//                    data[2] = "MiddleName";
//                    data[3] = "ContactNumber";
//                    data[4] = "Email";
//                    PutData putData = new PutData("http://192.168.1.104/register/signup.php", "POST", field, data);
//                    if (putData.startPut()) {
//                        String result = putData.getResult();
//
//                        if (result.equals("")) {
//                            //startActivity(new Intent(RegisterPage.this, RegisterPage1.class));
//                            Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT);
//                            Intent intent = new Intent(getApplicationContext(), RegisterPage1.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                        else {
//                            Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT);
//                        }
//
//                    }
//                }
//            });
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
//        }
//
//    }




//    private boolean validateName(){
//        String lName = lastName.getEditText().getText().toString().trim();
//        String fName = firstName.getEditText().getText().toString().trim();
//        String mName = middleName.getEditText().getText().toString().trim();
//
//        if (lName.isEmpty() && fName.isEmpty() && mName.isEmpty()) {
//            lastName.setError("Field can not be empty");
//            firstName.setError("Field can not be empty");
//            middleName.setError("Field can not be empty");
//            return false;
//        }
//        else {
//            lastName.setError(null);
//            firstName.setError(null);
//            middleName.setError(null);
//            lastName.setErrorEnabled(false);
//            firstName.setErrorEnabled(false);
//            middleName.setErrorEnabled(false);
//            return true;
//        }
//    }

//    private boolean validateContact() {
//        String contact = contactNumber.getEditText().getText().toString().trim();
//
//        if (contact.isEmpty()) {
//            contactNumber.setError("Field can not be empty");
//            return false;
//        }
//        else {
//            contactNumber.setError(null);
//            contactNumber.setErrorEnabled(false);
//            return true;
//        }
//    }

//    private boolean validateEmail() {
//        String val = email.getEditText().getText().toString().trim();
//        String checkEmail = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//        if (val.isEmpty()) {
//            email.setError("Field can not be empty");
//            return false;
//        }else if (!val.matches(checkEmail)){
//            return false;
//        }
//        else {
//            email.setError(null);
//            email.setErrorEnabled(false);
//            return true;
//        }
//    }

}
