package com.datseacorporation.salonmerchant;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    private String bookingID, bookingcode;
    private TextView usernameView, user_genderView, userdateView, user_mobileView, costView;
    private Button acceptBtn, rejectBtn;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        usernameView = findViewById(R.id.usernameView);
        user_genderView = findViewById(R.id.user_genderView);
        userdateView = findViewById(R.id.userdateView);
        user_mobileView = findViewById(R.id.user_mobileView);
        costView = findViewById(R.id.costView);
        acceptBtn = findViewById(R.id.acceptBtn);
        rejectBtn = findViewById(R.id.rejectBtn);
        listView = findViewById(R.id.listView);
        if(getIntent().hasExtra("booking_id")) {
            bookingID = getIntent().getStringExtra("booking_id");
            bookingcode = bookingID;
            Toast.makeText(this, "" + bookingID, Toast.LENGTH_SHORT).show();
        }
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String username = firebaseUser.getUid();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Salon Data");
        dbRef.child(username).child("Bookings Details").child("SSH1").child(bookingcode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child("display_name").getValue();
                String id = (String) dataSnapshot.child("bookingID").getValue();
                String gender = (String) dataSnapshot.child("gender").getValue();
                String status = (String) dataSnapshot.child("status").getValue();
                String time = (String) dataSnapshot.child("time").getValue();
                String cost = (String) dataSnapshot.child("cost").getValue();
                final List<String> serviceList = new ArrayList<>();
                for (DataSnapshot serviceSnapshot: dataSnapshot.child("services").getChildren()) {
                    String servicetname = (String) serviceSnapshot.getValue();
                    serviceList.add(servicetname);
                }
                ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(BookingActivity.this, android.R.layout.simple_spinner_item, serviceList);
                listView.setAdapter(serviceAdapter);
                usernameView.setText(name);
                user_genderView.setText(gender);
                userdateView.setText(time);
                costView.setText(cost);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
