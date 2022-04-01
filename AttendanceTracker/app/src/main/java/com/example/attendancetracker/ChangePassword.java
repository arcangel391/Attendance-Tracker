package com.example.attendancetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class ChangePassword extends AppCompatActivity implements View.OnClickListener{
    EditText tbxOldPassword, tbxNewPassword, tbxConfirmNewPassword;
    TextView txtEye1, txtEye2, txtEye3;
    Button btnSave, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        tbxOldPassword = findViewById(R.id.tbxOldPassword);
        tbxNewPassword = findViewById(R.id.tbxNewPassword);
        tbxConfirmNewPassword = findViewById(R.id.tbxConfirmNewPassword);
        txtEye1 = findViewById(R.id.txtEye1);
        txtEye2 = findViewById(R.id.txtEye2);
        txtEye3 = findViewById(R.id.txtEye3);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        txtEye1.setOnClickListener(this);
        txtEye2.setOnClickListener(this);
        txtEye3.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


    }

    private void changePassword(){
        final String userEmail = "intensityg36@gmail.com";
        final String oldPassword = tbxOldPassword.getText().toString().trim();
        final String newPassword = tbxNewPassword.getText().toString().trim();
        final String confirmPassword = tbxConfirmNewPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_CHANGE_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ChangePassword.this, Navbar.class);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params  = new HashMap<>();
                params.put("email", userEmail);
                params.put("old_password", oldPassword);
                params.put("new_password", newPassword);
                params.put("confirm_password", confirmPassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v){

        if(v==btnSave){
            changePassword();
        }

    }
}
