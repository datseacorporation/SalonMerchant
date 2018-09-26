package com.datseacorporation.salonmerchant;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.util.Calendar.getInstance;


public class AppointFragment extends Fragment implements View.OnClickListener {

    View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<AppointData> appointData;
    private ProgressBar progressBar;
    private Spinner stylistSpinner;
    private TextView dateView;
    private DatePickerDialog.OnDateSetListener listener;

    public AppointFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_appoint, container, false);
         dateView = view.findViewById(R.id.dateView);
         dateView.setOnClickListener(this);
        final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        dateView.setText(date);
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = (day<10?("0"+day):(day)) + "-" +(month<10?("0"+month):(month)) + "-" + year;
                dateView.setText(date);

            }
        };
        stylistSpinner = view.findViewById(R.id.stylistSpinner);
         progressBar = view.findViewById(R.id.progressBar);
         callStylist();
         recyclerView = view.findViewById(R.id.recyclerView);
         recyclerView.hasFixedSize();
         recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
         appointData = new ArrayList<>();
         appointData.clear();

        callBookings();

        return view;
    }

    private void callStylist(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String username = firebaseUser.getUid();
        DatabaseReference stylistRef = FirebaseDatabase.getInstance().getReference("Stylist Details");
        stylistRef.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> stylistsList = new ArrayList<>();
                for (DataSnapshot stylistSnapshot: dataSnapshot.getChildren()) {
                    String stylistname = (String) stylistSnapshot.getValue();
                    stylistsList.add(stylistname);
                    Toast.makeText(view.getContext(), ""+stylistname, Toast.LENGTH_SHORT).show();
                }
                ArrayAdapter<String> stylistAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, stylistsList);
                stylistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                stylistSpinner.setAdapter(stylistAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if( view == dateView ){

            Calendar calendar = getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    view.getContext(),
                    android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                    listener,
                    year,month,day);
            Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();

        }
    }


    private void callBookings(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String username = firebaseUser.getUid();
        DatabaseReference appointRef = FirebaseDatabase.getInstance().getReference("Salon Data");
        appointRef.child(username).child("Bookings Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointData.clear();
                for (DataSnapshot value : dataSnapshot.getChildren()){
                    for(DataSnapshot childvalue : value.getChildren()){
                        String name = (childvalue.getValue(AppointData.class)).getDisplay_name();
                        String gender = (childvalue.getValue(AppointData.class)).getGender();
                        String time = (childvalue.getValue(AppointData.class)).getTime();
                        String bookingid = (childvalue.getValue(AppointData.class)).getBookingID();
                        String status = (childvalue.getValue(AppointData.class)).getStatus();
                        String cost = (childvalue.getValue(AppointData.class)).getCost();
                        AppointData ad  = new AppointData(name,gender,time,bookingid,status,cost);
                        appointData.add(ad);
                    }
                }

                adapter = new MyAdapter(appointData,view.getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    }






