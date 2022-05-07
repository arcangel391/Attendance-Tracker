package com.example.attendancetracker;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class RegisterPage extends AppCompatActivity {
    View footer;
    TextView previous, next;
    LinearLayout header;
    LinearLayout dotLayout;
    EditText last,first,middle, cnum, email;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        getWindow().setEnterTransition(null);
        sharedPreferences = getApplicationContext().getSharedPreferences("RegisterSharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        footer = findViewById(R.id.footer);
        previous = footer.findViewById(R.id.btnPrevious);
        next = footer.findViewById(R.id.btnNext);
        previous.setVisibility(View.INVISIBLE);

        header = findViewById(R.id.header);
        dotLayout = footer.findViewById(R.id.dotLayout);

        last = findViewById(R.id.et_lastname);
        first = findViewById(R.id.et_firstname);
        middle = findViewById(R.id.et_middlename);
        cnum = findViewById(R.id.et_contact);
        email = findViewById(R.id.et_email);

        if(sharedPreferences.contains("first_name")){
            first.setText(sharedPreferences.getString("first_name", ""));
        }
        if(sharedPreferences.contains("middle_name")){
            middle.setText(sharedPreferences.getString("middle_name", ""));
        }
        if(sharedPreferences.contains("last_name")){
            last.setText(sharedPreferences.getString("last_name", ""));
        }
        if(sharedPreferences.contains("contact_number")){
            cnum.setText(sharedPreferences.getString("contact_number", ""));
        }
        if(sharedPreferences.contains("email")){
            email.setText(sharedPreferences.getString("email", ""));
        }


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(last.getText().toString().equals("")){
                    last.setError("This field is required.");
                }else if(first.getText().toString().equals("")){
                    first.setError("This field is required.");
                }else if(middle.getText().toString().equals("")){
                    middle.setError("This field is required.");
                }else if(cnum.getText().toString().equals("")){
                    cnum.setError("This field is required.");
                }else if(email.getText().toString().equals("")){
                    email.setError("This field is required");
                }else{*/
                    editor.putString("first_name", first.getText().toString().trim());
                    editor.putString("middle_name", middle.getText().toString().trim());
                    editor.putString("last_name", last.getText().toString().trim());
                    editor.putString("contact_number", cnum.getText().toString().trim());
                    editor.putString("email", email.getText().toString().trim());
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), RegisterPage1.class);
                    Pair[] pairs = new Pair[4];

                    pairs[0] = new Pair<View, String>(previous, "transition-previous-btn");
                    pairs[1] = new Pair<View, String>(next, "transition-next-btn");
                    pairs[2] = new Pair<View, String>(header, "transition-header");
                    pairs[3] = new Pair<View, String>(dotLayout, "transition-dot");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterPage.this,pairs);
                    getWindow().setExitTransition(null);
                    startActivity(intent, options.toBundle());
                /*}*/


            }
        });
    }

    @Override
    protected void onDestroy() {
        editor.clear();
        editor.commit();
        super.onDestroy();
    }
}
