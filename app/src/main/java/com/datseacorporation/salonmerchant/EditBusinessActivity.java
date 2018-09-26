package com.datseacorporation.salonmerchant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class EditBusinessActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView locationMap;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business);

        locationMap = findViewById(R.id.locationMap);
        locationMap.onCreate(savedInstanceState);
        locationMap.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap = googleMap;
        googleMap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}
