package com.example.gpstour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class InviteCodeActivity extends AppCompatActivity {

    String email, password, name, date, issharing, code;
    Uri imageUri;

    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        t1 = (TextView)findViewById(R.id.tV1);

        Intent myIntent  = getIntent();
        if(myIntent != null){
            email = myIntent.getStringExtra("email");
            password = myIntent.getStringExtra("password");
            name = myIntent.getStringExtra("name");
            code = myIntent.getStringExtra("code");
            issharing = myIntent.getStringExtra("issharing");
            imageUri = myIntent.getParcelableExtra("uri");

        }
        t1.setText(code);

    }
}
