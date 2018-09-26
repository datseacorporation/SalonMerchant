package com.datseacorporation.salonmerchant;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.support.v4.content.ContextCompat.startActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<AppointData> appointData;
    private List<String> bookingidList = new ArrayList<>();

    private Context mContext;
    String rootid;

    MyAdapter(List<AppointData> appointData, Context context) {
        this.appointData = appointData;
        mContext = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appoint_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        AppointData ad = appointData.get(position);
        holder.nameView.setText(ad.getDisplay_name());
        holder.genderView.setText(ad.getGender());
        holder.timeView.setText(ad.getTime());
        holder.bookingidView.setText(ad.getBookingID());
        holder.totalcostView.setText(ad.getCost());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BookingActivity.class);
                intent.putExtra("booking_id",appointData.get(position).getBookingID());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameView, genderView, timeView, dateView, bookingidView, totalcostView;
        public ImageView verificationBadge;
        public ConstraintLayout constraintLayout;

        ViewHolder(final View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.nameView);
            genderView = itemView.findViewById(R.id.genderView);
            timeView = itemView.findViewById(R.id.timeView);
            dateView = itemView.findViewById(R.id.dateView);
            bookingidView = itemView.findViewById(R.id.bookingidView);
            verificationBadge = itemView.findViewById(R.id.verificationBadge);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            totalcostView = itemView.findViewById(R.id.totalcostView);
            timeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.AM_PM);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(final TimePicker view, int hourOfDay, int minute) {
                            final boolean isPM = (hourOfDay >= 12);
                            timeView.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            assert firebaseUser != null;
                            String username = firebaseUser.getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Salon Data")
                                    .child(username).child("Bookings Details").child("SSH1").child("BKG1").child("time");
                            String time = timeView.getText().toString().trim();
                            databaseReference.setValue(time);
                            Toast.makeText(view.getContext(), "Time Updated...", Toast.LENGTH_SHORT).show();

                        }
                    }, hour, minute, false);
                    mTimePicker.show();

                }
            });

        }
        }
    }

