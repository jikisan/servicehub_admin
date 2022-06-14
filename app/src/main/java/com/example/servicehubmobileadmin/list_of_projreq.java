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

public class list_of_projreq extends AppCompatActivity {

    private RecyclerView rv_projReq;
    private ImageView iv_back;
    private AdapterProjRequest adapterProjRequest;
    private ArrayList<Projects> arr;
    private ArrayList<String> arrId;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_projreq);

        rv_projReq = findViewById(R.id.rv_projReq);
        iv_back = findViewById(R.id.iv_back);

        rv_projReq.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_projReq.setLayoutManager(linearLayoutManager);

        arr = new ArrayList<>();
        arrId = new ArrayList<>();
        adapterProjRequest = new AdapterProjRequest(arr);
        rv_projReq.setAdapter(adapterProjRequest);

        getViewHolderValues();
        clicklisteners();
    }

    private void clicklisteners() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(list_of_projreq.this, MainActivity.class);
                startActivity(intent);
            }
        });

        adapterProjRequest.setOnItemClickListener(new AdapterProjRequest.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                id = arrId.get(position);
                Intent intent = new Intent(list_of_projreq.this, view_projreq.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });
    }

    private void getViewHolderValues() {

        DatabaseReference projReqDb = FirebaseDatabase.getInstance().getReference("Projects Request");

        projReqDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Projects projects = dataSnapshot.getValue(Projects.class);
                    String idKey = dataSnapshot.getKey().toString();
                    arrId.add(idKey);
                    arr.add(projects);
                }

                adapterProjRequest.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}