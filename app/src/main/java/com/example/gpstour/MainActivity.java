package com.example.gpstour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null){

            setContentView(R.layout.activity_main);
        }
        else{
            Intent myIn = new Intent(MainActivity.this,MyNavigationActivity.class);
            startActivity(myIn);
            finish();
        }

    }
        public void irLogin(View v){
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(myIntent);
        }

        public void  irRegistrarse(View v){
            Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(myIntent);
        }
}