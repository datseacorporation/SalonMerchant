package com.datseacorporation.salonmerchant;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.util.Objects;

public class ServiceFragment extends Fragment implements View.OnClickListener {

    View view;
    private EditText servicenameEdit, servicedurationEdit, servicecostEdit, servicedescEdit;
    private Button addserviceBtn;
    private CardView serviceCard;
    private ProgressBar servicesprogressBar;
    private DatabaseReference addservicesRef;
    private RecyclerView servicesRecycler;
    private RecyclerView.Adapter servicesAdapter;
    private List<ServicesData> servicesData;
    private int childCount;
    public ServiceFragment() {

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view =  inflater.inflate(R.layout.fragment_, container, false);
        servicesRecycler = view.findViewById(R.id.servicesRecycler);
        servicesRecycler.hasFixedSize();
        servicesRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        servicesData = new ArrayList<>();
        servicesData.clear();
       FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
       assert firebaseUser != null;
       String username = firebaseUser.getUid();

       addservicesRef = FirebaseDatabase.getInstance().getReference("Salon Data").child(username).child("Services Provided");
        addservicesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                childCount = (int) dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       servicesprogressBar = view.findViewById(R.id.servicesprogressBar);
       serviceCard = view.findViewById(R.id.servicesCard);
       servicenameEdit = view.findViewById(R.id.servicenameEdit);
       servicedurationEdit = view.findViewById(R.id.servicedurationEdit);
       servicecostEdit = view.findViewById(R.id.servicecostEdit);
       servicedescEdit = view.findViewById(R.id.servicedescEdit);
       addserviceBtn = view.findViewById(R.id.addserviceBtn);
       addserviceBtn.setOnClickListener(this);
        callServices();
        return view;
    }

    private void callServices() {
        servicesRecycler.setVisibility(View.INVISIBLE);
        servicesprogressBar.setVisibility(View.VISIBLE);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String username = firebaseUser.getUid();
        DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference("Salon Data");
        servicesRef.child(username).child("Services Provided").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesData.clear();
                for (DataSnapshot childvalue : dataSnapshot.getChildren()){

                        String servicename = (Objects.requireNonNull(childvalue.getValue(ServicesData.class))).getServicename();
                        String serviceduration = (Objects.requireNonNull(childvalue.getValue(ServicesData.class))).getServiceduration();
                        String servicecost = (Objects.requireNonNull(childvalue.getValue(ServicesData.class))).getServicecost();
                        String servicedesc = (Objects.requireNonNull(childvalue.getValue(ServicesData.class))).getServicedesc();
                        int serviceid = Objects.requireNonNull(childvalue.getValue(ServicesData.class)).getServiceid();
                        ServicesData sd  = new ServicesData(servicename,serviceduration,servicecost,servicedesc,serviceid);
                        servicesData.add(sd);

                }


                servicesAdapter = new ServiceAdapter(servicesData,view.getContext());
                servicesRecycler.setAdapter(servicesAdapter);
                servicesAdapter.notifyDataSetChanged();
                servicesprogressBar.setVisibility(View.INVISIBLE);
                servicesRecycler.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if( view == addserviceBtn ){
            addServices();
        }
    }

    private void addServices() {
        serviceCard.setVisibility(View.INVISIBLE);
        servicesprogressBar.setVisibility(View.VISIBLE);
        String servicename, serviceduration, servicecost, servicedesc, withrupee;
        servicename = servicenameEdit.getText().toString().trim();
        serviceduration = servicedurationEdit.getText().toString().trim();
        servicecost = servicecostEdit.getText().toString().trim();
        withrupee = "₹"+servicecost;
        servicedesc = servicedescEdit.getText().toString().trim();
        childCount = childCount+1;
        ServicesData sd = new ServicesData(servicename,serviceduration,withrupee,servicedesc,childCount);
        addservicesRef.child(String.valueOf(childCount)).setValue(sd);
        Toast.makeText(view.getContext(), "Service Added...", Toast.LENGTH_SHORT).show();
        servicenameEdit.setText("");
        servicedurationEdit.setText("");
        servicecostEdit.setText("");
        servicedescEdit.setText("");
        servicesprogressBar.setVisibility(View.INVISIBLE);
        serviceCard.setVisibility(View.VISIBLE);
    }
}
