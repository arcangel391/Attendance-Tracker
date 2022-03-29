package com.example.attendancetracker;

import android.content.Context;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener{
    TextView eye1, eye2;
    EditText newPassword, confirmNewPassword;
    Button btnSavePassword, btnCancel;
    SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        sp = getApplicationContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        eye1 = findViewById(R.id.txtEye1);
        eye2 = findViewById(R.id.txtEye2);
        newPassword = findViewById(R.id.tbxNewPassword);
        confirmNewPassword = findViewById(R.id.tbxConfirmNewPassword);
        btnSavePassword = findViewById(R.id.btnResetPassword);
        btnCancel = findViewById(R.id.btnCancel);

        btnSavePassword.setOnClickListener(this);

    }

    private void passwordReset(){
        final String inputEmail = sp.getString("email", "");
        final String userPassword = newPassword.getText().toString().trim();
        final String userConfirmPassword = confirmNewPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_RESET_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            if(userPassword.equals(userConfirmPassword)){
                                if(!obj.getBoolean("error")){
                                    Toast.makeText(getApplicationContext(), "Password has successfully changed.", Toast.LENGTH_LONG).show();

                                }else{
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Password did not match.", Toast.LENGTH_LONG).show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params  = new HashMap<>();
                params.put("email", inputEmail);
                params.put("password", userPassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void onClick(View v){
        if(v==btnSavePassword){
            passwordReset();
        }
    }
}
