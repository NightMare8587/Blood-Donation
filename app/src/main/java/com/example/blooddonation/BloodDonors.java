package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class BloodDonors extends AppCompatActivity {
    FusedLocationProviderClient client;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donors);
        client = LocationServices.getFusedLocationProviderClient(this);

        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    Toast.makeText(BloodDonors.this, String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}