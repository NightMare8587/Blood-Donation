package com.example.blooddonation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonorList extends RecyclerView.Adapter<DonorList.DonorViewHolder> {

    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card,parent,false);
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 8;
    }

    public class DonorViewHolder extends RecyclerView.ViewHolder{

        public DonorViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
