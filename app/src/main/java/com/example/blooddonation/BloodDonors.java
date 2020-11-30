package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BloodDonors extends AppCompatActivity {
    FusedLocationProviderClient client;
    double longitude,latitude;
    TextView bloodType,Age,Name,Address,Sex;
    FirebaseAuth findAuth;
    DatabaseReference findRef;
    RecyclerView recyclerView;
    ArrayList<String> list = new ArrayList<>();
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donors);
        initialise();
        list.clear();
        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    longitude = location.getLongitude();
                    latitude =  location.getLatitude();
                }else{
                    Toast.makeText(BloodDonors.this, "Restart Application and Turn on GPS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot post : snapshot.getChildren()){
                    double longi = Double.parseDouble(post.child("Longitude").getValue().toString());
                    double lati = Double.parseDouble(post.child("Latitude").getValue().toString());
                    if (distance(latitude, longitude, lati, longi) < 0.5) { // if distance < 0.1 miles we take locations as equal
                       list.add(post.getKey());
                        list.add(post.getKey());
                    }
                }
              
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void initialise() {
        bloodType = (TextView)findViewById(R.id.donorBlood);
        Age = (TextView)findViewById(R.id.donorAge);
        Name = (TextView)findViewById(R.id.donorName);
        Address = (TextView)findViewById(R.id.donorAddress);
        Sex = (TextView)findViewById(R.id.donorSex);
        findAuth = FirebaseAuth.getInstance();
        findRef = FirebaseDatabase.getInstance().getReference().child("Blood Donors");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }
}