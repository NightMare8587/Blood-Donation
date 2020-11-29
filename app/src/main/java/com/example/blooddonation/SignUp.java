package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText fullNameSignUp,emailSignUp,passwordSignUp;
    RadioGroup group;
    RadioButton male,female,others;
    Button createAccount;
    FirebaseAuth signUpAuth;
    DatabaseReference signUpRef;
    String email,pass,name,Age;
    FirebaseUser signUpUser;
    RadioButton selected;
    EditText age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initialise();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fullNameSignUp.length() == 0){
                    fullNameSignUp.requestFocus();
                    fullNameSignUp.setError("Field can't be Empty");
                    return;
                }else if(emailSignUp.length() == 0){
                    emailSignUp.requestFocus();
                    emailSignUp.setError("Field can't be Empty");
                    return;
                }else if(passwordSignUp.length() <= 5){
                    passwordSignUp.requestFocus();
                    passwordSignUp.setError("Too Short");
                    return;
                }else if(age.length() == 0){
                    age.requestFocus();
                    age.setError("Field can't be Empty");
                    return;
                }
                email = emailSignUp.getText().toString();
                pass = passwordSignUp.getText().toString();
                name = fullNameSignUp.getText().toString();
                Age = age.getText().toString();
                int id = group.getCheckedRadioButtonId();
                selected = (RadioButton)findViewById(id);
                String buttonName = selected.getText().toString();

                startCreatingAccount(email,pass,name,Age,buttonName);
            }
        });
    }

    private void startCreatingAccount(String email, String pass, String name, String age, String buttonName) {
        signUpAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Account created Successfully", Toast.LENGTH_SHORT).show();
                    Database user = new Database(name,age,buttonName,email);
                    signUpUser = FirebaseAuth.getInstance().getCurrentUser();
                    signUpRef.child("Users").child(signUpUser.getUid()).setValue(user);
                    startActivity(new Intent(getApplicationContext(),HomePage.class));
                    finish();
                }
            }
        });
    }

    private void initialise() {
        fullNameSignUp = (EditText)findViewById(R.id.fullnameSignup);
        emailSignUp = (EditText)findViewById(R.id.emailSignup);
        passwordSignUp = (EditText)findViewById(R.id.passwordSignup);
        createAccount = (Button)findViewById(R.id.createAccount);
        group = (RadioGroup)findViewById(R.id.group);
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);
        others = (RadioButton)findViewById(R.id.others);
        age = (EditText)findViewById(R.id.ageSignUp);
        signUpRef = FirebaseDatabase.getInstance().getReference().getRoot();
        signUpAuth = FirebaseAuth.getInstance();
    }
}