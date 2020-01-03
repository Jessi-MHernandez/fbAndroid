package com.example.gpstour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "GPS_TOUR";
    private Button register1, login1;
    private FirebaseAuth mAuth; //Autentificacion de firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText ingEmail, ingPass;
    private ProgressDialog progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Crear instancia a la base de datos
        mAuth = FirebaseAuth.getInstance();
        //Instanciar el boton de xml
        ingEmail = (EditText)findViewById(R.id.ingEmail);
        ingPass = (EditText)findViewById(R.id.ingPass);
        login1 = (Button)findViewById(R.id.login);
        //REGISTRAR NUEVO USUARIO
        register1 = (Button)findViewById(R.id.register);
        //inicializamos la barra de progreso
        progressDialog1= new ProgressDialog(this);


        //Se establece la habilidad de escuchar eventos y se define como va a actuar
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //El metodo que respondera al evento
                Intent objetoIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(objetoIntent);
            }
        });



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(MainActivity.this,InviteCodeActivity.class));
                }
                else {
                    // Toast.makeText(MainActivity.this, "Datos Incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        };

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogearUser();
            }
        });
    }

    /*verifica si el usuario ya esta logueado o no
    si esta logueado lo manda  a la siguiente activity
    */


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }


    //metodo para verificar si el usuario ya esta registrado en la bd
    private void LogearUser(){
        //agregas un mensaje en el ProgressDialog
        progressDialog1.setMessage("Iniciando sesión");
        //muestras el ProgressDialog
        progressDialog1.show();

        String email = ingEmail.getText().toString();
        String password = ingPass.getText().toString();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(MainActivity.this, "¡Introduce tu correo electrónico!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this, "¡Introduce tu contraseña!", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog1.dismiss();
                            Log.i(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            progressDialog1.dismiss();
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(MainActivity.this, "Datos Incorrectos.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    public void irRegistro(View v) {
        Intent myIntentR = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(myIntentR);


    }
}
