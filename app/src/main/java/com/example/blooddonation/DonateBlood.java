package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonateBlood extends AppCompatActivity {
    FusedLocationProviderClient locationProviderClient;
    String longitude,latitude;
    FirebaseAuth uploadAuth;
    DatabaseReference uploadRef;
    FirebaseUser uploadUser;
    EditText uploadName,uploadAge,uploadAddress,uploadBloodType;
    RadioGroup uploadGroup;
    RadioButton uploadMale,uploadFemale,uploadOthers;
    RadioButton selectedButton;
    Button uploadData;
    EditText uploadNumber;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);
        initialise();
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    longitude = String.valueOf(location.getLongitude());
                    latitude = String.valueOf(location.getLatitude());
                }else{
                    Toast.makeText(DonateBlood.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        uploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadName.length() == 0){
                    uploadName.setError("Field can't be Empty");
                    uploadName.requestFocus();
                    return;
                }else if(uploadAge.length() == 0){
                    uploadAge.requestFocus();
                    uploadAge.setError("Field can't be Empty");
                    return;
                }else if(uploadAddress.length() == 0){
                    uploadAddress.requestFocus();
                    uploadAddress.setError("Field can't be Empty");
                    return;
                }else if(uploadBloodType.length() == 0){
                    uploadBloodType.requestFocus();
                    uploadBloodType.setError("Field can't be Empty");
                    return;
                }else if(uploadNumber.length()<=9){
                    uploadNumber.requestFocus();
                    uploadNumber.setError("Enter valid number");
                    return;
                }

                int id = uploadGroup.getCheckedRadioButtonId();
                selectedButton = (RadioButton)findViewById(id);
                String sex = selectedButton.getText().toString();

                startUploadingData(uploadName.getText().toString()
                ,uploadAddress.getText().toString()
                ,uploadAge.getText().toString()
                ,uploadBloodType.getText().toString()
                ,sex,uploadNumber.getText().toString());

            }
        });
    }

    private void startUploadingData(String name, String address, String age, String bloodType, String sex,String number) {
        donorUpload upload = new donorUpload(name,address,sex,bloodType,age,longitude,latitude,number);
        uploadRef.child("Blood Donors").child(uploadUser.getUid()).setValue(upload);
        Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
    }

    private void initialise() {
        uploadAuth = FirebaseAuth.getInstance();
        uploadRef = FirebaseDatabase.getInstance().getReference().getRoot();
        uploadUser = FirebaseAuth.getInstance().getCurrentUser();
        uploadName = (EditText)findViewById(R.id.uploadName);
        uploadAge = (EditText)findViewById(R.id.uploadAge);
        uploadData = (Button)findViewById(R.id.uploadButton);
        uploadGroup = (RadioGroup)findViewById(R.id.uploadGroup);
        uploadMale = (RadioButton)findViewById(R.id.uploadMale);
        uploadFemale = (RadioButton)findViewById(R.id.uploadFemale);
        uploadOthers = (RadioButton)findViewById(R.id.uploadOthers);
        uploadAddress = (EditText)findViewById(R.id.uploadAddress);
        uploadBloodType = (EditText)findViewById(R.id.uploadBloodType);
        uploadNumber = (EditText)findViewById(R.id.uploadPhone);
    }
}