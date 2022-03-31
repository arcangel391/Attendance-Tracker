package com.example.attendancetracker;

import android.os.Bundle;
import android.widget.EditText;
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

public class EditProfile extends AppCompatActivity {
    EditText street, barangay, city, contactNumber, email, department, driveLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        street = findViewById(R.id.tbxEditStreet);
        barangay = findViewById(R.id.tbxEditBarangay);
        city = findViewById(R.id.tbxEditCity);
        contactNumber = findViewById(R.id.tbxEditContactNumber);
        email = findViewById(R.id.tbxEditEmail);
        department = findViewById(R.id.tbxEditDepartment);
        driveLink = findViewById(R.id.tbxEditGoogleDriveLink);
        fillInTextbox();
    }

    private void fillInTextbox(){
        final String userEmail = "intensityg36@gmail.com";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_VIEW_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){

                                street.setText(obj.getString("street"));
                                barangay.setText(obj.getString("barangay"));
                                city.setText(obj.getString("city"));
                                contactNumber.setText(obj.getString("contact_number"));
                                email.setText(obj.getString("email"));
                                department.setText(obj.getString("department"));
                                driveLink.setText(obj.getString("drive"));

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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params  = new HashMap<>();
                params.put("email", userEmail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void updateProfile(){
        final String userEmail = "intensityg36@gmail.com";
        final String userStreet = street.getText().toString().trim();
        final String userBarangay = barangay.getText().toString().trim();
        final String userCity = city.getText().toString().trim();
        final String userEmailAddress = email.getText().toString().trim();
        final String userContactNumber = contactNumber.getText().toString().trim();
        final String userDepartment = department.getText().toString().trim();
        final String userDrive = driveLink.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UPDATE_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

    }
}
