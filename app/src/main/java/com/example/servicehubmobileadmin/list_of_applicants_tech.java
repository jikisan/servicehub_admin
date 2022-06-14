package com.example.servicehubmobileadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class list_of_applicants_tech extends AppCompatActivity {

    private RecyclerView rv_applicant;
    private ImageView iv_back;
    private AdapterApplicantsItemTech adapterApplicantsItemTech;
    private ArrayList<ApplicantsTech> arr;
    private ArrayList<String> arrId;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_applicants_tech);

        rv_applicant = findViewById(R.id.rv_applicant);
        iv_back = findViewById(R.id.iv_back);

        rv_applicant.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_applicant.setLayoutManager(linearLayoutManager);

        arr = new ArrayList<>();
        arrId = new ArrayList<>();
        adapterApplicantsItemTech = new AdapterApplicantsItemTech(arr);
        rv_applicant.setAdapter(adapterApplicantsItemTech);

        getViewHolderValues();
        clicklisteners();
    }

    private void clicklisteners() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(list_of_applicants_tech.this, MainActivity.class);
                startActivity(intent);
            }
        });

        adapterApplicantsItemTech.setOnItemClickListener(new AdapterApplicantsItemTech.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DatabaseReference applicantsDatabase = FirebaseDatabase.getInstance().getReference("Technician Applicants");
                arr.get(position);

                id = arrId.get(position);
                Intent intent = new Intent(list_of_applicants_tech.this, view_applicants.class);
                intent.putExtra("category", "tech");
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });

    }

    private void getViewHolderValues() {
        DatabaseReference applicantsDatabase = FirebaseDatabase.getInstance().getReference("Technician Applicants");

        Query query = applicantsDatabase
                .orderByChild("approved")
                .equalTo(false);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ApplicantsTech applicantsTech = dataSnapshot.getValue(ApplicantsTech.class);
                    String idKey = dataSnapshot.getKey().toString();
                    arrId.add(idKey);
                    arr.add(applicantsTech);
                }

                adapterApplicantsItemTech.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}