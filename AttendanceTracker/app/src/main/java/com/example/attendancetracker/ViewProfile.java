package com.example.attendancetracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ViewProfile extends Fragment {

    FloatingActionButton fabSignOut;
    FloatingActionButton fabCP;
    FloatingActionButton fabEditProfile;

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

        return v;
    }

    private void getUserData(){
        final String userEmail = "intensityg36@gmail.com";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.)
    }

}