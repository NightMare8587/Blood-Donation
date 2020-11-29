package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText emailLogin,passwordLogin;
    Button login,forgotPass,createNewAccount;
    FirebaseAuth loginAuth;
    FirebaseUser loginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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