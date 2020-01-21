    package com.example.gpstour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

    public class PerfilActivity extends AppCompatActivity {

    String email, password;
    EditText e3_name;
    CircleImageView circleImageView;

        Uri resultUri;
        Date myDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        e3_name = (EditText)findViewById(R.id.regName);
        circleImageView = (CircleImageView)findViewById(R.id.circleImageView);

        Intent myIntent = getIntent();
        if(myIntent != null){
            email = myIntent.getStringExtra("email");
            password = myIntent.getStringExtra("password");

        }

    }


    //generar codigo de excursion aleatorio
    public void generarCode(View v){
       // Date myDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        String date = format1.format(myDate);
        Random r = new Random();

        int n = 100000 + r.nextInt(900000);
        String code = String.valueOf(n);

        if(resultUri!=null){
            Intent myIntent = new Intent(PerfilActivity.this, InviteCodeActivity.class);
            myIntent.putExtra("name", e3_name.getText().toString());
            myIntent.putExtra("email", email);
            myIntent.putExtra("password",password);
            myIntent.putExtra("date", date);
            myIntent.putExtra("isSharing", "false");
            myIntent.putExtra("code", code);
            myIntent.putExtra("imageUri", resultUri);

            startActivity(myIntent);
            finish();


        }else{
            Toast.makeText(getApplicationContext(), "Ingresa tu foto de perfil", Toast.LENGTH_LONG).show();
        }

    }

    //seleccionar imagen de perfil
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
                    //Uri resultUri = result.getUri();
                    circleImageView.setImageURI(resultUri);
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                }

            }

        }

}
