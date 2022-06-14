package com.example.servicehubmobileadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class view_listreq extends AppCompatActivity {

    private DatabaseReference listReqDb, listDb;
    private ImageView iv_listPhoto, iv_back;
    private TextView tv_listName, tv_address, tv_price, tv_quantity, tv_desc;
    private Button btn_decline, btn_accept;
    private String category, id;
    private ArrayList<Listings> arr = new ArrayList<Listings>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_listreq);

        listReqDb = FirebaseDatabase.getInstance().getReference("Listings Request");
        listDb = FirebaseDatabase.getInstance().getReference("Listings");
        id = getIntent().getStringExtra("id");

        setRef();
        generateListReq();
        clickListeners();
    }

    private void clickListeners() {
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acceptListing();

            }
        });

        btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                declineListing();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void acceptListing() {

        listDb.push().setValue(arr.get(0)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                listReqDb.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            dataSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(view_listreq.this, "Listing accepted", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(view_listreq.this, list_of_listreq.class);
                                    startActivity(intent);
                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    private void declineListing() {
        listReqDb.child(id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    dataSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(view_listreq.this, "Listing Declined", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view_listreq.this, list_of_listreq.class);
                            startActivity(intent);
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void generateListReq() {

        listReqDb.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Listings listings = snapshot.getValue(Listings.class);
                    arr.add(listings);

                    String sp_imageUrl = listings.imageUrl;
                    String sp_listName = listings.listName;
                    String sp_address = listings.listAddress;
                    String sp_quantity = listings.listQuantity;
                    String sp_price = listings.listPrice;
                    String sp_desc = listings.listDesc;

                    Picasso.get()
                            .load(sp_imageUrl)
                            .into(iv_listPhoto);

                    tv_listName.setText(sp_listName);
                    tv_address.setText(sp_address);
                    tv_quantity.setText(sp_quantity);
                    tv_price.setText(sp_price);
                    tv_desc.setText(sp_desc);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setRef() {
        iv_listPhoto = findViewById(R.id.iv_listPhoto);
        iv_back = findViewById(R.id.iv_back);

        tv_listName = findViewById(R.id.tv_listName);
        tv_address = findViewById(R.id.tv_address);
        tv_quantity = findViewById(R.id.tv_quantity);
        tv_price = findViewById(R.id.tv_price);
        tv_desc = findViewById(R.id.tv_desc);

        btn_decline = findViewById(R.id.btn_decline);
        btn_accept = findViewById(R.id.btn_accept);

    }
}