package com.example.gpstour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText e1, e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1 = (EditText)findViewById(R.id.IngEmail);
        e2 = (EditText)findViewById(R.id.IngPass);
        auth = FirebaseAuth.getInstance();

    }
    public void login(View v){
        auth.signInWithEmailAndPassword(e1.getText().toString(), e2.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Usuario logueado", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this,MyNavigationActivity.class);
                            startActivity(i);
                        }else {
                            Toast.makeText(getApplicationContext(), "Email o contraseña incorrecta", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}
