package com.example.servicehubmobileadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private CardView cardView1, cardView2, cardView3, cardView;
    private TextView tv_signOut, tv_projReqCount, tv_listReqCount, tv_techReqCount, tv_sellerReqCount,
            tv_totalUsers, tv_totalBooking, tv_totalListings, tv_totalProject, tv_totalOrders;
    private DatabaseReference techDB, sellerDB, listReqDB, projReqDB, userDb, bookingDb,
            orderDb, projDb, listdb;
    private int projReqCount = 0;
    private int listReqCount = 0;
    private int techReqCount = 0;
    private int sellerReqCount = 0;

    private int userCount = 0;
    private int bookingCount = 0;
    private int orderCount = 0;
    private int projCount = 0;
    private int listCount = 0;

    private ArrayList<Projects> arrProj = new ArrayList<Projects>();
    private ArrayList<Listings> arrList = new ArrayList<Listings>();
    private ArrayList<ApplicantsTech> arrTech = new ArrayList<ApplicantsTech>();
    private ArrayList<ApplicantsSeller> arrSeller = new ArrayList<ApplicantsSeller>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projReqDB = FirebaseDatabase.getInstance().getReference("Projects Request");
        listReqDB = FirebaseDatabase.getInstance().getReference("Listings Request");
        sellerDB = FirebaseDatabase.getInstance().getReference("Seller Applicants");
        techDB = FirebaseDatabase.getInstance().getReference("Technician Applicants");

        userDb = FirebaseDatabase.getInstance().getReference("Users");
        bookingDb = FirebaseDatabase.getInstance().getReference("Bookings");
        orderDb = FirebaseDatabase.getInstance().getReference("Orders");
        projDb = FirebaseDatabase.getInstance().getReference("Projects");
        listdb = FirebaseDatabase.getInstance().getReference("Listings");

        setRef();
        generateData();
        clickListeners();

    }

    private void generateData() {

        projReqDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Projects projects = snapshot.getValue(Projects.class);
                arrProj.add(projects);
                projReqCount = arrProj.size();
                tv_projReqCount.setText(String.valueOf(projReqCount));

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listReqDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Listings listings = snapshot.getValue(Listings.class);
                arrList.add(listings);
                listReqCount = arrList.size();
                tv_listReqCount.setText(String.valueOf(listReqCount));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        techDB.orderByChild("approved")
                .equalTo(false)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    ApplicantsTech applicantsTech = snapshot.getValue(ApplicantsTech.class);
                    arrTech.add(applicantsTech);


                techReqCount = arrTech.size();
                tv_techReqCount.setText(String.valueOf(techReqCount));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sellerDB.orderByChild("approved")
                .equalTo(false)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ApplicantsSeller applicantsSeller = snapshot.getValue(ApplicantsSeller.class);
                arrSeller.add(applicantsSeller);
                sellerReqCount = arrSeller.size();
                tv_sellerReqCount.setText(String.valueOf(sellerReqCount));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        userDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                userCount++;
                tv_totalUsers.setText(String.valueOf(userCount));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bookingDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                bookingCount++;
                tv_totalBooking.setText(String.valueOf(bookingCount));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        orderDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                orderCount++;
                tv_totalOrders.setText(String.valueOf(orderCount));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        projDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                projCount++;
                tv_totalProject.setText(String.valueOf(projCount));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listdb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                listCount++;
                tv_totalListings.setText(String.valueOf(listCount));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void clickListeners() {

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, list_of_projreq.class);
                startActivity(intent);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, list_of_listreq.class);
                startActivity(intent);
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, list_of_applicants_seller.class);
                startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, list_of_applicants_tech.class);
                startActivity(intent);
            }
        });

        tv_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Warning!.")
                        .setMessage("Are you sure you want to sign out?")
                        .setCancelable(false)
                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(), login_page.class));
                                finish();
                            }
                        }).show();


            }
        });
    }

    private void setRef() {
        cardView1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        cardView = findViewById(R.id.cardView);

        tv_signOut = findViewById(R.id.tv_signOut);
        tv_projReqCount = findViewById(R.id.tv_projReqCount);
        tv_listReqCount = findViewById(R.id.tv_listReqCount);
        tv_techReqCount = findViewById(R.id.tv_techReqCount);
        tv_sellerReqCount = findViewById(R.id.tv_sellerReqCount);
        tv_totalUsers = findViewById(R.id.tv_totalUsers);
        tv_totalBooking = findViewById(R.id.tv_totalBooking);
        tv_totalListings = findViewById(R.id.tv_totalListings);
        tv_totalProject = findViewById(R.id.tv_totalProject);
        tv_totalOrders = findViewById(R.id.tv_totalOrders);
    }
}