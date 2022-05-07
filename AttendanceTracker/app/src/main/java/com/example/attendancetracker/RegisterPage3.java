package com.example.attendancetracker;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterPage3 extends AppCompatActivity implements View.OnClickListener {
    View footer;
    TextView previous, next, imageText;
    ImageView dot3, dot4, dot1, img2x2, imageLayout;
    LinearLayout header;
    LinearLayout dotLayout, uploadImageLayout;
    EditText googleDrive, discordName;

    Boolean checkPermission= false;
    private static final int STORAGE_PERMISSION_CODE = 0701;
    private static final int PICK_IMAGE_REQUEST = 0173;

    private Uri filePath;
    private Bitmap bitmap;
    String storeImage = "";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page3);
        getWindow().setEnterTransition(null);
        sharedPreferences = getApplicationContext().getSharedPreferences("RegisterSharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        footer = findViewById(R.id.footer);
        previous = footer.findViewById(R.id.btnPrevious);
        next = footer.findViewById(R.id.btnNext);
        previous.setVisibility(View.VISIBLE);
        next.setText("Sign-up");

        header = findViewById(R.id.header);
        dotLayout = footer.findViewById(R.id.dotLayout);
        dot1 = footer.findViewById(R.id.dot1);
        dot1.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        dot3 = footer.findViewById(R.id.dot3);
        dot4 = footer.findViewById(R.id.dot4);
        dot3.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        dot4.setBackgroundTintList(getResources().getColorStateList(R.color.sky));

        imageLayout = findViewById(R.id.imageLayout);
        imageText = findViewById(R.id.imageTextView);
        uploadImageLayout = findViewById(R.id.uploadImageLayout);
        img2x2 = findViewById(R.id.img2x2);
        uploadImageLayout.setOnClickListener(this);
        img2x2.setOnClickListener(this);

        googleDrive = findViewById(R.id.et_gdrive);
        discordName = findViewById(R.id.et_dcname);



        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(googleDrive.getText().toString().trim(), discordName.getText().toString().trim(),
                        storeImage);
                Intent intent = new Intent(getApplicationContext(), RegisterPage2.class);
                Pair[] pairs = new Pair[4];

                pairs[0] = new Pair<View, String>(previous, "transition-previous-btn");
                pairs[1] = new Pair<View, String>(next, "transition-next-btn");
                pairs[2] = new Pair<View, String>(header, "transition-header");
                pairs[3] = new Pair<View, String>(dotLayout, "transition-dot");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterPage3.this,pairs);
                getWindow().setExitTransition(null);
                startActivity(intent, options.toBundle());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataToServer();
            }
        });

        getData();
    }

    private void insertDataToServer(){
        final String appID = "2000304";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            editor.clear();
                            editor.commit();
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
                params.put("applicationID", appID);
                params.put("first_name", sharedPreferences.getString("first_name", ""));
                params.put("middle_name", sharedPreferences.getString("middle_name", ""));
                params.put("last_name", sharedPreferences.getString("last_name", ""));
                params.put("contact_number", sharedPreferences.getString("contact_number", ""));
                params.put("street", sharedPreferences.getString("street", ""));
                params.put("barangay", sharedPreferences.getString("barangay", ""));
                params.put("city", sharedPreferences.getString("city", ""));
                params.put("gender", sharedPreferences.getString("gender", ""));
                params.put("birthdate", sharedPreferences.getString("birthdate", ""));
                params.put("civil_status", sharedPreferences.getString("civil_status", ""));
                params.put("start_date", sharedPreferences.getString("start_date", ""));
                params.put("end_date", sharedPreferences.getString("end_date", ""));
                params.put("required_hours", sharedPreferences.getString("required_hours", ""));
                params.put("company", sharedPreferences.getString("company", ""));
                params.put("department", sharedPreferences.getString("department", ""));
                params.put("image", sharedPreferences.getString("image", ""));
                params.put("google_drive_link", sharedPreferences.getString("google_drive_link", ""));
                params.put("discord_name", sharedPreferences.getString("discord_name", ""));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void requestStoragePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            checkPermission = true;
            return;
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Please accept permission.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Bitmap lastBitmap = null;
                lastBitmap = bitmap;
                String image = getStringImage(lastBitmap);
                storeImage = image;
                Log.d("image", image);
                img2x2.setImageBitmap(bitmap);
                img2x2.setVisibility(View.VISIBLE);
                imageLayout.setVisibility(View.GONE);
                imageText.setVisibility(View.GONE);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void showFileChooser(){
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageIntent.setType("image/*");
        pickImageIntent.putExtra("aspectX", 1);
        pickImageIntent.putExtra("aspectY", 1);
        pickImageIntent.putExtra("scale", true);
        pickImageIntent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onClick(View v) {

        if(v == uploadImageLayout || v == img2x2){
            requestStoragePermission();
            if(checkPermission){
                showFileChooser();
            }else{
                requestStoragePermission();
            }

        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    private void getData(){
        if(sharedPreferences.contains("google_drive_link")){
            googleDrive.setText(sharedPreferences.getString("google_drive_link", ""));
        }
        if(sharedPreferences.contains("discord_name")){
            discordName.setText(sharedPreferences.getString("discord_name", ""));
        }
        if(sharedPreferences.contains("image")){
            Log.i("echo_path", sharedPreferences.getString("image", ""));
            if(sharedPreferences.getString("image", "").equals("")){
                img2x2.setVisibility(View.GONE);
                imageLayout.setVisibility(View.VISIBLE);
                imageText.setVisibility(View.VISIBLE);
            }else{
                img2x2.setVisibility(View.VISIBLE);
                imageLayout.setVisibility(View.GONE);
                imageText.setVisibility(View.GONE);
                String encoded = sharedPreferences.getString("image", "");
                byte[] imageAsByte = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
                img2x2.setImageBitmap(BitmapFactory.decodeByteArray(imageAsByte, 0, imageAsByte.length));
            }
        }
    }

    private void insertData(String gdrive, String discord, String image){
        editor.putString("google_drive_link", gdrive);
        editor.putString("discord_name", discord);
        editor.putString("image", image);
        editor.commit();
    }
}
