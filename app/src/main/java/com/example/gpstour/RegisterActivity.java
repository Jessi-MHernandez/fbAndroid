package com.example.gpstour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.security.Provider;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    EditText e1_email, e2_password, e3_name;
    CircleImageView e4_profile;

    FirebaseAuth auth;
    ProgressDialog dialog;
    FirebaseStorage storage;
    FirebaseDatabase database;

    Uri resultUri;
    Date myDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        e1_email = (EditText)findViewById(R.id.regEmail);
        e2_password = (EditText)findViewById(R.id.regPass);
        e3_name= (EditText)findViewById(R.id.regName);
        e4_profile = (CircleImageView)findViewById(R.id.profileImage);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        dialog = new ProgressDialog(this);

    }


    public void generarCode(View v){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        String date = format1.format(myDate);
        Random r = new Random();

        int n = 100000 + r.nextInt(900000);
        String code = String.valueOf(n);

        if(resultUri!=null){
            Intent myIntent = new Intent(RegisterActivity.this, InviteCodeActivity.class);

/*
            Map<String, Object> datosUser = new HashMap<>();
            datosUser.put("email",e1_email.getText().toString());
            datosUser.put("password", e2_password.getText().toString());
            datosUser.put("name",e3_name.getText().toString());
            datosUser.put("date", date);
            datosUser.put("isSharing", "false");
            datosUser.put("code", code);
            datosUser.put("imageUri",resultUri);

*/
            myIntent.putExtra("email", e1_email.getText().toString());
            myIntent.putExtra("password",e2_password.getText().toString());
            myIntent.putExtra("name", e3_name.getText().toString());
            myIntent.putExtra("date", date);
            myIntent.putExtra("isSharing", "false");
            myIntent.putExtra("code", code);
            myIntent.putExtra("imageUri", resultUri);


            startActivity(myIntent);


        }else{
            Toast.makeText(getApplicationContext(), "Ingresa tu foto de perfil, por favor", Toast.LENGTH_LONG).show();
        }

    }

            /*

            myIntent.putExtra("email", e1_email.getText().toString());
            myIntent.putExtra("password",e2_password.getText().toString());
            myIntent.putExtra("name", e3_name.getText().toString());
            myIntent.putExtra("date", date);
            myIntent.putExtra("isSharing", "false");
            myIntent.putExtra("code", code);
            myIntent.putExtra("imageUri", resultUri);

            startActivity(myIntent);


        }else{
            Toast.makeText(getApplicationContext(), "Ingresa tu foto de perfil", Toast.LENGTH_LONG).show();
        }

    }

*/
    public void selectImage(View v){
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 12  && resultCode == RESULT_OK && data!=null){
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){
                resultUri = result.getUri();
                e4_profile.setImageURI(resultUri);
                //Uri resultUri = result.getUri();
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }

        }
    }


}

