package com.datseacorporation.salonmerchant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
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

import java.util.ArrayList;
import java.util.List;

public class BusinessActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private TextView employeesView;
    private EditText mobilenumberEdit, streetaddressEdit, salonnameEdit;
    private Button registerBtn;
    private ProgressBar progressBar;
    private Spinner statesSpinner, citySpinner, salontypeSpinner;
    private DatabaseReference dbRefState, dbRefSalonType, dbRef;
    private String stateItem, cityItem, salonTypeItem, employeesseek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        dbRefState = FirebaseDatabase.getInstance().getReference("Service Area");
        dbRefSalonType = FirebaseDatabase.getInstance().getReference("Saloon Type");
        dbRef = FirebaseDatabase.getInstance().getReference("Salon Data");
        progressBar = findViewById(R.id.progressBar);
        TextView registerTitle = findViewById(R.id.registerTitle);
        mobilenumberEdit = findViewById(R.id.mobilenumberEdit);
        streetaddressEdit = findViewById(R.id.streetaddressEdit);
        salonnameEdit = findViewById(R.id.salonnameEdit);
        registerBtn = findViewById(R.id.registerBtn);
        registerTitle.requestFocus();
        employeesView = findViewById(R.id.employeesView);
        statesSpinner = findViewById(R.id.statesSpinner);
        citySpinner = findViewById(R.id.citySpinner);
        salontypeSpinner = findViewById(R.id.salontypeSpinner);
        SeekBar employeesseekBar = findViewById(R.id.employeesseekBar);
        employeesseekBar.setOnSeekBarChangeListener(this);
        employeesseekBar.setMax(15);
        registerBtn.setOnClickListener(this);
        callState();
        salonType();


    }

    private void salonType() {
        dbRefSalonType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> salontype = new ArrayList<>();
                for (DataSnapshot salonTypeSnapshot: dataSnapshot.getChildren()) {
                    String cityname = (String) salonTypeSnapshot.getValue();
                    salontype.add(cityname);
                    Toast.makeText(BusinessActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                ArrayAdapter<String> salonTypeAdapter = new ArrayAdapter<>(BusinessActivity.this, android.R.layout.simple_spinner_item, salontype);
                salonTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                salontypeSpinner.setAdapter(salonTypeAdapter);
                salontypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        salonTypeItem = adapterView.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void callCity() {

        dbRefState.child("Cities").child(stateItem).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> cities = new ArrayList<>();
                for (DataSnapshot citySnapshot: dataSnapshot.getChildren()) {
                    String cityname = (String) citySnapshot.getValue();
                    cities.add(cityname);
                }

                ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(BusinessActivity.this, android.R.layout.simple_spinner_item, cities);
                citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(citiesAdapter);
                citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        cityItem = adapterView.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void callState() {

        dbRefState.child("States").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> states = new ArrayList<>();
                for (DataSnapshot stateSnapshot: dataSnapshot.getChildren()) {
                    String statename = (String) stateSnapshot.getValue();
                    states.add(statename);
                }
                ArrayAdapter<String> statesAdapter = new ArrayAdapter<>(BusinessActivity.this, android.R.layout.simple_spinner_item, states);
                statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                statesSpinner.setAdapter(statesAdapter);
                statesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        stateItem = adapterView.getSelectedItem().toString();
                        callCity();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        employeesseek = String.valueOf(i);
        if ( i < 15 ){
            employeesView.setText("Total Employees : "+i);
        }else {
            employeesView.setText("Total Employees : "+i+"+");
        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void uploadData(){
        registerBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        String salonname = salonnameEdit.getText().toString().trim();
        String salonstate = stateItem;
        String saloncity = cityItem;
        String streetaddress = streetaddressEdit.getText().toString().trim();
        String mobilenumber = mobilenumberEdit.getText().toString().trim();
        String salontype = salonTypeItem;
        String totalemployees = employeesseek;

        if(TextUtils.isEmpty(salonname)){
            salonnameEdit.setError("Salon name is empty...");
            return;
        }
        if(TextUtils.isEmpty(streetaddress)){
            streetaddressEdit.setError("Street Address is empty...");
            return;
        }
        if(TextUtils.isEmpty(mobilenumber)){
            mobilenumberEdit.setError("Mobile Number is empty...");
            return;
        }
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String username = firebaseUser.getUid();
        RegisterData registerData = new RegisterData(salonname,salonstate,saloncity,streetaddress,mobilenumber,salontype,totalemployees);
        dbRef.child(username).child("Salon Details").setValue(registerData);
        Toast.makeText(this, "Registration Complete", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
        registerBtn.setVisibility(View.VISIBLE);
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

    }

    @Override
    public void onClick(View view) {
        if( view == registerBtn ){
            uploadData();
        }
    }
}
