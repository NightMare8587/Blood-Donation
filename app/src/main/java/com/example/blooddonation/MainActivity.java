package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText emailLogin,passwordLogin;
    Button login,forgotPass,createNewAccount;
    FirebaseAuth loginAuth;
    FirebaseUser loginUser;
    String Email,Pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        if(loginUser != null){
            startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailLogin.length() == 0){
                    emailLogin.requestFocus();
                    emailLogin.setError("Field can't be Empty");
                    return;
                }else if(passwordLogin.length() == 0){
                    passwordLogin.requestFocus();
                    passwordLogin.setError("Field can't be Empty");
                    return;
                }
                Email = emailLogin.getText().toString();
                Pass = passwordLogin.getText().toString();

                startLoginProcedure(Email,Pass);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPasswordReset();
            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });

    }

    private void startLoginProcedure(String email, String pass) {
        loginAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(),HomePage.class));
                     finish();
                 }else {
                     Toast.makeText(MainActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                     return;
                 }
            }
        });
    }

    private void startPasswordReset() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Password Reset");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setMessage("Enter Email to Send Password Reset Link");
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               loginAuth.sendPasswordResetEmail(input.getText().toString()).addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(MainActivity.this, "Link Sent Successfully", Toast.LENGTH_SHORT).show();
                       }else{
                           Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create();
        builder.show();
    }

    private void initialise() {
        emailLogin = (EditText)findViewById(R.id.emailLogin);
        passwordLogin = (EditText)findViewById(R.id.passwordLogin);
        login = (Button)findViewById(R.id.loginButton);
        forgotPass = (Button)findViewById(R.id.forgotPassword);
        createNewAccount = (Button)findViewById(R.id.createNewAccount);
        loginAuth = FirebaseAuth.getInstance();
        loginUser = FirebaseAuth.getInstance().getCurrentUser();
    }
}