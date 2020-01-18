package com.example.gpstour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    String email;
    EditText e2_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        e2_password = (EditText)findViewById(R.id.RegPass);

        Intent myIntent = getIntent();
        if(myIntent != null){
            email = myIntent.getStringExtra("email");
        }
    }

    public  void irPerfilActivity(View v){
        if(e2_password.getText().toString().length() >6 ){
            Intent myIntent = new Intent(PasswordActivity.this, PerfilActivity.class);
            myIntent.putExtra("email", email);
            myIntent.putExtra("password", e2_password.getText().toString());
            startActivity(myIntent);
        }
        else{
            Toast.makeText(getApplicationContext(), "La contraseña debe ser mayor a 6 caracteres", Toast.LENGTH_LONG).show();

        }


    }
}
