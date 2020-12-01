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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DonorList extends RecyclerView.Adapter<DonorList.DonorViewHolder> {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> age = new ArrayList<>();
    ArrayList<String> sex = new ArrayList<>();

    ArrayList<String> addres = new ArrayList<>();
    ArrayList<String> blood = new ArrayList<>();
    ArrayList<String>number = new ArrayList<>();
    public DonorList(ArrayList<String> names, ArrayList<String> age, ArrayList<String> sex, ArrayList<String> addres, ArrayList<String> blood, ArrayList<String> number) {
        this.addres = addres;
        this.names = names;
        this.sex = sex;
        this.blood = blood;
        this.age = age;
        this.number = number;
    }

    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card,parent,false);
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder holder, int position) {
        holder.bloodType.setText(blood.get(position));
        holder.sex.setText(sex.get(position));
        holder.age.setText(age.get(position));
        holder.address.setText(addres.get(position));
        holder.name.setText(names.get(position));
        holder.number.setText(number.get(position));
    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class DonorViewHolder extends RecyclerView.ViewHolder{

        TextView age,sex,name,address,bloodType,number;
        public DonorViewHolder(@NonNull View itemView) {
            super(itemView);
            age = (TextView)itemView.findViewById(R.id.donorAge);
            sex = (TextView)itemView.findViewById(R.id.donorSex);
            name= (TextView)itemView.findViewById(R.id.donorName);
            address = (TextView)itemView.findViewById(R.id.donorAddress);
            bloodType = (TextView)itemView.findViewById(R.id.donorBlood);
            number = (TextView)itemView.findViewById(R.id.donorNumber);
        }
    }
}
