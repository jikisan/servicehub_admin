package com.example.servicehubmobileadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.HashMap;

public class view_applicants extends AppCompatActivity {

    private DatabaseReference sellerDatabase, techDatabase;
    private ImageView iv_viewImage, iv_back, iv_validID, iv_proofOfWork, iv_equipment;
    private TextView tv_firstName, tv_lastName, tv_contactNum, tv_category;
    private Button btn_decline, btn_accept;
    private String category, id;
    private LinearLayout layoutFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_applicants);

        sellerDatabase = FirebaseDatabase.getInstance().getReference("Seller Applicants");
        techDatabase = FirebaseDatabase.getInstance().getReference("Technician Applicants");
        category = getIntent().getStringExtra("category");
        id = getIntent().getStringExtra("id");

        setRef();

        if(category.equals("seller"))
        {
            tv_category.setText("Seller Applicants");
            generateLayoutSeller();

        }
        else if(category.equals("tech"))
        {
            tv_category.setText("Technician Applicants");
            generateLayoutTech();

        }

        clickListeners();
    }

    private void clickListeners() {

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(category.equals("seller"))
                {
                    acceptSeller();
                }
                else if(category.equals("tech"))
                {
                    acceptTech();
                }
                
             
            }
        });

        btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(category.equals("seller"))
                {
                    declineSellerApplication();
                }
                else if(category.equals("tech"))
                {
                    declineTechApplication();
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void declineTechApplication() {
        techDatabase.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    dataSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(view_applicants.this, "Applicant Declined", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view_applicants.this, list_of_applicants_tech.class);
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

    private void declineSellerApplication() {
        sellerDatabase.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    dataSnapshot.getRef().removeValue();
                    Toast.makeText(view_applicants.this, "Applicant Declined", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view_applicants.this, list_of_applicants_seller.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void acceptTech() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("approved", true);
        hashMap.put("pending", false);

        techDatabase.child(id).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(view_applicants.this, "Applicant Accepted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view_applicants.this, list_of_applicants_tech.class);
                startActivity(intent);

            }
        });
    }

    private void acceptSeller() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("approved", true);
        hashMap.put("pending", false);

        sellerDatabase.child(id).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(view_applicants.this, "Applicant Accepted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view_applicants.this, list_of_applicants_seller.class);
                startActivity(intent);

            }
        });
    }

    private void generateLayoutTech() {
        techDatabase.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ApplicantsTech applicantsTech = snapshot.getValue(ApplicantsTech.class);

                if(snapshot.exists())
                {
                    layoutFiles.setVisibility(View.VISIBLE);
                    String firstname = applicantsTech.getFirstName();
                    String lastname = applicantsTech.getLastName();
                    String contactNum = applicantsTech.getPhoneNumber();

                    String imageUrl = applicantsTech.getSelfieUrl();
                    String validIdUrl = applicantsTech.getValidIdUrl();
                    String proofWorkUrl = applicantsTech.getProofOfWorkUrl();
                    String proofEquipUrl = applicantsTech.proofOfEquipmentUrl;

                    tv_firstName.setText(firstname);
                    tv_lastName.setText(lastname);
                    tv_contactNum.setText(contactNum);

                    Picasso.get()
                            .load(imageUrl)
                            .into(iv_viewImage);

                    Picasso.get()
                            .load(validIdUrl)
                            .into(iv_validID);

                    Picasso.get()
                            .load(proofWorkUrl)
                            .into(iv_proofOfWork);

                    Picasso.get()
                            .load(proofEquipUrl)
                            .into(iv_equipment);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void generateLayoutSeller() {

        sellerDatabase.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ApplicantsSeller applicantsSeller = snapshot.getValue(ApplicantsSeller.class);

                if(snapshot.exists())
                {

                    String firstname = applicantsSeller.getFirstName();
                    String lastname = applicantsSeller.getLastName();
                    String contactNum = applicantsSeller.getPhoneNumber();
                    String imageUrl = applicantsSeller.getSelfieUrl();
                    String validIDurl = applicantsSeller.getValidIdUrl();

                    tv_firstName.setText(firstname);
                    tv_lastName.setText(lastname);
                    tv_contactNum.setText(contactNum);

                    Picasso.get()
                            .load(imageUrl)
                            .into(iv_viewImage);

                    Picasso.get()
                            .load(validIDurl)
                            .into(iv_validID);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setRef() {
        iv_viewImage = findViewById(R.id.iv_viewImage);
        iv_validID = findViewById(R.id.iv_validID);
        iv_proofOfWork = findViewById(R.id.iv_proofOfWork);
        iv_equipment = findViewById(R.id.iv_equipment);

        layoutFiles = findViewById(R.id.layoutFiles);

        iv_back = findViewById(R.id.iv_back);

        tv_firstName = findViewById(R.id.tv_firstName);
        tv_lastName = findViewById(R.id.tv_lastName);
        tv_contactNum = findViewById(R.id.tv_contactNum);
        tv_category = findViewById(R.id.tv_category);

        btn_decline = findViewById(R.id.btn_decline);
        btn_accept = findViewById(R.id.btn_accept);
    }

}