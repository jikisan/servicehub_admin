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

public class view_projreq extends AppCompatActivity {

    private DatabaseReference projReqDb, projDb;
    private ImageView iv_projPhoto, iv_back;
    private TextView tv_projName, tv_projCategory, tv_address, tv_timeStart,
            tv_timeEnd, tv_price, tv_desc;
    private Button btn_decline, btn_accept;
    private String category, id;
    private ArrayList<Projects> arr = new ArrayList<Projects>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_projreq);

        projReqDb = FirebaseDatabase.getInstance().getReference("Projects Request");
        projDb = FirebaseDatabase.getInstance().getReference("Projects");
        id = getIntent().getStringExtra("id");

        setRef();
        generateProjReq();
        clickListeners();
    }

    private void clickListeners() {
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             acceptProj();

            }
        });

        btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               declineProj();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void acceptProj() {

        projDb.push().setValue(arr.get(0)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                projReqDb.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            dataSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(view_projreq.this, "Project accepted", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(view_projreq.this, list_of_projreq.class);
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

    private void declineProj() {
        projReqDb.child(id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    dataSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(view_projreq.this, "Project Declined", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view_projreq.this, list_of_projreq.class);
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

    private void generateProjReq() {

        projReqDb.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Projects projects = snapshot.getValue(Projects.class);
                    arr.add(projects);

                    String sp_imageUrl = projects.imageUrl;
                    String sp_projName = projects.projName;
                    String sp_category = projects.category;
                    String sp_address = projects.projAddress;
                    String sp_timeStart = projects.startTime;
                    String sp_timeEnd = projects.endTime;
                    String sp_price = projects.price;
                    String sp_desc = projects.projInstruction;

                    Picasso.get()
                            .load(sp_imageUrl)
                            .into(iv_projPhoto);

                    tv_projName.setText(sp_projName);
                    tv_projCategory.setText(sp_category);
                    tv_address.setText(sp_address);
                    tv_timeStart.setText(sp_timeStart);
                    tv_timeEnd.setText(sp_timeEnd);
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
        iv_projPhoto = findViewById(R.id.iv_projPhoto);
        iv_back = findViewById(R.id.iv_back);

        tv_projName = findViewById(R.id.tv_projName);
        tv_projCategory = findViewById(R.id.tv_projCategory);
        tv_address = findViewById(R.id.tv_address);
        tv_timeStart = findViewById(R.id.tv_timeStart);
        tv_timeEnd = findViewById(R.id.tv_timeEnd);
        tv_price = findViewById(R.id.tv_price);
        tv_desc = findViewById(R.id.tv_desc);

        btn_decline = findViewById(R.id.btn_decline);
        btn_accept = findViewById(R.id.btn_accept);

    }
}