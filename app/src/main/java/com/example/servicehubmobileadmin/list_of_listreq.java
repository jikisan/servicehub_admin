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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class list_of_listreq extends AppCompatActivity {

    private RecyclerView rv_listReq;
    private ImageView iv_back;
    private AdapterListRequest adapterListRequest;
    private ArrayList<Listings> arr;
    private ArrayList<String> arrId;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_listreq);

        rv_listReq = findViewById(R.id.rv_listReq);
        iv_back = findViewById(R.id.iv_back);

        rv_listReq.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_listReq.setLayoutManager(linearLayoutManager);

        arr = new ArrayList<>();
        arrId = new ArrayList<>();
        adapterListRequest = new AdapterListRequest(arr);
        rv_listReq.setAdapter(adapterListRequest);

        getViewHolderValues();
        clicklisteners();
    }

    private void clicklisteners() {

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(list_of_listreq.this, MainActivity.class);
                startActivity(intent);
            }
        });

        adapterListRequest.setOnItemClickListener(new AdapterListRequest.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                id = arrId.get(position);
                Intent intent = new Intent(list_of_listreq.this, view_listreq.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private void getViewHolderValues() {

        DatabaseReference listReqDb = FirebaseDatabase.getInstance().getReference("Listings Request");

        listReqDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Listings listings = dataSnapshot.getValue(Listings.class);
                    String idKey = dataSnapshot.getKey().toString();
                    arrId.add(idKey);
                    arr.add(listings);
                }

                adapterListRequest.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}