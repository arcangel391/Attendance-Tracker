package com.example.attendancetracker;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.squareup.picasso.Picasso;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.w3c.dom.Text;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage3 extends AppCompatActivity {

    EditText gDrive;
    EditText dcName;
    Button upload;
    TextView signup;
    TextView previous3;
    ImageView dPage;
    ImageView dPage1;
    ImageView dPage2;
    ImageView dPage3;
    CharSequence[] options = {"Gallery","Cancel"};
    String selectedImage;
    ImageView imgView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page3);

        gDrive = (EditText) findViewById(R.id.et_gdrive);
        dcName = (EditText) findViewById(R.id.et_dcname);
        upload = (Button) findViewById(R.id.btn_upload);
        signup = (TextView) findViewById(R.id.tv_signup);
        previous3 = (TextView) findViewById(R.id.tv_previous3);
        dPage = (ImageView) findViewById(R.id.dPage);
        dPage1 = (ImageView) findViewById(R.id.dPage1);
        dPage2 = (ImageView) findViewById(R.id.dPage2);
        dPage3 = (ImageView) findViewById(R.id.dPage3);
        imgView = (ImageView) findViewById(R.id.imgView);
        requirePermission();

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String gDriveLink;
                String discName;
                gDriveLink = String.valueOf(gDrive.getText());
                discName = String.valueOf(dcName.getText());
                if (!gDriveLink.equals("") && !discName.equals("")) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "GoogleDriveLink";
                            field[1] = "DiscordName";
                            String[] data = new String[2];
                            data[0] = "GoogleDriveLink";
                            data[1] = "DiscordName";
                            PutData putData = new PutData("http://localhost/Register/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign up success")) {
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
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });




        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPage3.this);
                builder.setTitle("Select Image");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (options[i].equals("Gallery")) {
                            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gallery, 0);
                        }
                        else {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFileToServer();
            }
        });


        dPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage3.this, RegisterPage.class));
            }
        });

        dPage1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage3.this, RegisterPage1.class));
            }
        });

        dPage2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage3.this, RegisterPage2.class));
            }
        });

        dPage3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage3.this, RegisterPage3.class));
            }
        });

        previous3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage3.this, RegisterPage2.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode !=RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if(resultCode == RESULT_OK && data !=null) {
                        Bitmap image = (Bitmap) data.getExtras().get("data");
                        selectedImage = FileUtils.getPath(RegisterPage3.this, getImageUri(RegisterPage3.this,image));
                        imgView.setImageBitmap(image);
                    }
                    break;
                case 1:
                    if(resultCode == RESULT_OK && data !=null) {

                        Uri image = data.getData();
                        selectedImage = FileUtils.getPath(RegisterPage3.this,image);
                        Picasso.get().load(image).into(imgView);
                    }
            }
        }
    }

    Uri getImageUri(Context context, Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "myImage", "");
    return Uri.parse(path);
    }

    void requirePermission() {
        ActivityCompat.requestPermissions(RegisterPage3.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }
    void uploadFileToServer() {

        File file = new File(Uri.parse(selectedImage).getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("sendimage",file.getName(),requestBody);
        HttpService service = RetrofitBuilder.getClient().create(HttpService.class);
        Call<FileModel> call = service.callUpload(filePart);
        call.enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                FileModel fileModel = response.body();
                Toast.makeText(RegisterPage3.this, fileModel.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                Toast.makeText(RegisterPage3.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }




}