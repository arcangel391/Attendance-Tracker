package com.example.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    Button btnSignup;
    TextView txtUsername, txtPass;
    Button register,login;
    CheckBox checkedStatus;
    SharedPreferences sharedPreferences;
    public static final String ROOT_URL="http://192.168.1.110/MelhamApp/";
    public static final String URL_LOGIN ="login.php";
    public static final String prefstatus1 ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        btnSignup = findViewById(R.id.btn_signin);
        txtUsername = findViewById(R.id.txt_email);
        txtPass = findViewById(R.id.txt_password);
//        checkedStatus = findViewById(R.id.cbsamp);
        sharedPreferences = getSharedPreferences("a_tracker", Context.MODE_PRIVATE);
        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.prefStatus),"");
        if (loginStatus.equals("loggedin")){
            startActivity(new Intent(LoginPage.this,Navbar.class));




    }

        //Button is pressed
       btnSignup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String username = txtUsername.getText().toString();
               String password = txtPass.getText().toString();

               if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                   Toast.makeText(LoginPage.this, "All Fields Required", Toast.LENGTH_SHORT).show();
               }
               else{
                   LoginMet(username,password);

               }
           }
       });


    }

    private void LoginMet(final String username, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);
        progressDialog.setTitle("");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "http://192.168.1.110/Melham/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, ROOT_URL+URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Login Success")){
                    Toast.makeText(LoginPage.this, response, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    /*if (checkedStatus.isChecked()){
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedin");
                    }
                    else{
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedout");
                    }*/
                    editor.apply();
                    startActivity(new Intent(LoginPage.this, Navbar.class));
                    progressDialog.dismiss();
                    finish();

                }
                else {
                    Toast.makeText(LoginPage.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginPage.this, error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("username",username);
                param.put("password",password);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(LoginPage.this).addToRequestQueue(request);
    }


}