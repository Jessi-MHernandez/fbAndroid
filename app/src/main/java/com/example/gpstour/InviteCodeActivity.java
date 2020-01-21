package com.example.gpstour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InviteCodeActivity extends AppCompatActivity {

    String email, password, name, date, issharing, code;
    Uri imageUri;
    ProgressDialog progressDialog;
    TextView t1;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        t1 = (TextView)findViewById(R.id.textView1);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Intent myIntent  = getIntent();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        if(myIntent != null){
            name = myIntent.getStringExtra("name");
            email = myIntent.getStringExtra("email");
            password = myIntent.getStringExtra("password");
            code = myIntent.getStringExtra("code");
            issharing = myIntent.getStringExtra("isSharing");
            imageUri = myIntent.getParcelableExtra("uri");

        }
        t1.setText(code);
    }

    public void registerUser(View v){
        progressDialog.setMessage("Espera un momento");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //insertar valores en tiempo real a la BD
                            CreateUser createUser = new CreateUser(name, email,password,code,"false","na","na", "na");

                            user = auth.getCurrentUser();
                            userId = user.getUid();

                            reference.child(userId).setValue(createUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(),"Usuario registrado",Toast.LENGTH_LONG).show();
                                                finish();
                                                Intent myIntent = new Intent(InviteCodeActivity.this,MyNavigationActivity.class);
                                                startActivity(myIntent);
                                            }
                                            else{
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(),"No se pudo registrar",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    }
                });


    }
}