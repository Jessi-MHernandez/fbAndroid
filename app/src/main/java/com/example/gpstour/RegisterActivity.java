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
    EditText e1_email;
    FirebaseAuth auth;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        e1_email = (EditText)findViewById(R.id.regEmail);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
    }

    public void irPasswordActivity(View v){

        dialog.setMessage("Verificando correo electrónico");
        dialog.show();
        //comprobar si el correo esta ya esta registrado o no
        auth.fetchSignInMethodsForEmail(e1_email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        if(task.isSuccessful()){
                            dialog.dismiss();
                            boolean check = !task.getResult().getSignInMethods().isEmpty();

                            if(!check){
                                // el correo electrónico no existe, por lo que podemos crear este correo electrónico con el usuario
                                Intent myIntent = new Intent(RegisterActivity.this,PasswordActivity.class);
                                myIntent.putExtra("email", e1_email.getText().toString());
                                startActivity(myIntent);

                            }else{
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Este correo electrónico ya está registrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}

