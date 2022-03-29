package com.example.attendancetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmailVerificationFP extends AppCompatActivity implements View.OnClickListener{

    EditText inputCode;
    TextView resendCode;
    Button submit;
    SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_verification);

        sp = getApplicationContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        inputCode = findViewById(R.id.tbxVerificationCode);
        resendCode = findViewById(R.id.txtResendCode);
        submit = findViewById(R.id.btnSubmitCode);
        submit.setOnClickListener(this);


    }

    private void verifyCode(String code){
        final String codeInput = inputCode.getText().toString().trim();
        final String inputEmail = sp.getString("email", "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_VERIFY_CODE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                if(codeInput == code) {
                                    Toast.makeText(getApplicationContext(), "Verified successfully.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(EmailVerificationFP.this, EmailVerificationFP.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Wrong code.", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params  = new HashMap<>();
                params.put("code", codeInput);
                params.put("email", inputEmail);
                return params;
            }
        };


    }


    public void onClick(View v){
        if(v == submit){
            String verificationCode = sp.getString("code", "");
            verifyCode(verificationCode);
        }
    }
}
