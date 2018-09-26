package com.datseacorporation.salonmerchant;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{

    private List<ServicesData> servicesData;
    private Context context;

    public ServiceAdapter(List<ServicesData> servicesData, Context context) {
        this.servicesData = servicesData;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceAdapter.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.services_items, parent, false);
        return new ServiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceAdapter.ServiceViewHolder holder, int position) {

        final ServicesData sd = servicesData.get(position);
        holder.servicenameView.setText(sd.servicename);
        holder.servicedurationView.setText(sd.serviceduration);
        holder.servicecostView.setText(sd.servicecost);
        holder.servicedescView.setText(sd.servicedesc);
        holder.deleteserviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                String username = firebaseUser.getUid();
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Salon Data").child(username).child("Services Provided").child(String.valueOf(sd.getServiceid()));
                dbRef.removeValue();
                Toast.makeText(view.getContext(), "Service Deleted...", Toast.LENGTH_SHORT).show();
            }
        });

        holder.editserviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                View mView = layoutInflaterAndroid.inflate(R.layout.servicealert, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput.setTitle("Update Service");
                final EditText servicenameAlert, servicedurationAlert, servicecostAlert, servicedescAlert;
                servicenameAlert = mView.findViewById(R.id.servicenameAlert);
                servicedurationAlert = mView.findViewById(R.id.servicedurationAlert);
                servicecostAlert = mView.findViewById(R.id.servicecostAlert);
                servicedescAlert = mView.findViewById(R.id.servicedescAlert);
              /*  */
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                String username = firebaseUser.getUid();
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Salon Data").child(username).child("Services Provided").child(String.valueOf(sd.getServiceid()));
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String servicename = (String) dataSnapshot.child("servicename").getValue();
                        String serviceduration = (String) dataSnapshot.child("serviceduration").getValue();
                        String servicecost = (String) dataSnapshot.child("servicecost").getValue();
                        String servicedesc = (String) dataSnapshot.child("servicedesc").getValue();
                        servicenameAlert.setText(servicename);
                        servicedurationAlert.setText(serviceduration);
                        servicecostAlert.setText(servicecost);
                        servicedescAlert.setText(servicedesc);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                assert firebaseUser != null;
                                String username = firebaseUser.getUid();
                                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Salon Data").child(username).child("Services Provided").child(String.valueOf(sd.getServiceid()));
                                String servicename = servicenameAlert.getText().toString().trim();
                                String serviceduration = servicedurationAlert.getText().toString().trim();
                                String servicecost = servicecostAlert.getText().toString().trim();
                                String servicedesc = servicedescAlert.getText().toString().trim();
                                int serviceid = Integer.parseInt(String.valueOf(sd.getServiceid()));
                                ServicesData sd = new ServicesData(servicename,serviceduration,servicecost,servicedesc,serviceid);
                                dbRef.setValue(sd);
                                Toast.makeText(view.getContext(), "Service Updated...", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }


        });


    }

    @Override
    public int getItemCount() {
        return servicesData.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder{

        public TextView servicenameView, servicedurationView, servicedescView, servicecostView;
        public ImageButton editserviceBtn, deleteserviceBtn;


        public ServiceViewHolder(View itemView) {
            super(itemView);
            servicenameView = itemView.findViewById(R.id.servicenameView);
            servicedurationView = itemView.findViewById(R.id.servicedurationView);
            servicecostView = itemView.findViewById(R.id.servicecostView);
            servicedescView = itemView.findViewById(R.id.servicedescView);
            editserviceBtn = itemView.findViewById(R.id.editserviceBtn);
            deleteserviceBtn = itemView.findViewById(R.id.deleteserviceBtn);
    }
    }
}
