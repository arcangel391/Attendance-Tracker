package com.example.attendancetracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ViewProfile extends Fragment implements View.OnClickListener{

    com.github.clans.fab.FloatingActionButton fabSignOut;
    com.github.clans.fab.FloatingActionButton fabChangePassword;
    com.github.clans.fab.FloatingActionButton fabEditProfile;
    com.github.clans.fab.FloatingActionButton fabViewLeave;

    TextView name, applicationID, email, contactNumber, street, address, birthdate, gender;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_profile, container, false);

        name = v.findViewById(R.id.txtUserName);
        applicationID = v.findViewById(R.id.txtApplicationID);
        email = v.findViewById(R.id.txtUserEmail);
        contactNumber = v.findViewById(R.id.txtUserContactNumber);
        street = v.findViewById(R.id.txtStreetName);
        address = v.findViewById(R.id.txtAddress);
        birthdate = v.findViewById(R.id.txtBirthdate);
        gender = v.findViewById(R.id.txtGender);

        fabSignOut = v.findViewById(R.id.fabSignOut);
        fabChangePassword = v.findViewById(R.id.fabChangePassword);
        fabViewLeave = v.findViewById(R.id.fabLeave);
        fabEditProfile = v.findViewById(R.id.fabEditProfile);

        fabSignOut.setOnClickListener(this);
        fabChangePassword.setOnClickListener(this);
        fabViewLeave.setOnClickListener(this);
        fabEditProfile.setOnClickListener(this);

        getUserData();
        return v;
    }

    private void getUserData(){
        final String userEmail = "intensityg36@gmail.com";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_VIEW_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                String full_name = obj.getString("first_name") + " " + obj.getString("last_name");
                                name.setText(full_name);
                                applicationID.setText(obj.getString("application_id"));
                                email.setText(obj.getString("email"));
                                contactNumber.setText(obj.getString("contact_number"));
                                street.setText(obj.getString("street"));
                                address.setText(obj.getString("address"));
                                birthdate.setText(obj.getString("birthdate"));
                                gender.setText(obj.getString("gender"));

                            }else{
                                Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }



    public void onClick(View v){
        if(v == fabEditProfile){
            Intent intent = new Intent(getActivity(), EditProfile.class);
            startActivity(intent);
        }

        if(v==fabChangePassword){
            Intent intent = new Intent(getActivity(), ChangePassword.class);
            startActivity(intent);
        }
    }

}