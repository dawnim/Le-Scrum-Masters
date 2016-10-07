package com.le_scrum_masters.notpokemongo.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.le_scrum_masters.notpokemongo.R;

import maps.MapBehaviour;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private MapBehaviour mapBehaviour;
    private FloatingActionButton trackingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        trackingButton = (FloatingActionButton)findViewById(R.id.trackingButton);
        trackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationPermissionCheck()) {
                    if (mapBehaviour.isRequestingLocationUpdates()) {
                        mapBehaviour.disableLocationUpdates();
                    } else {
                        mapBehaviour.enableLocationUpdates();
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Chalmers and move the camera
        LatLng chalmers = new LatLng(57.6884, 11.9778);
        map.addMarker(new MarkerOptions().position(chalmers).title("Marker on Chalmers"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(chalmers, 11));

        mapBehaviour = new MapBehaviour(this, map);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            mapBehaviour.enableLocationUpdates();
        }
    }

    public void placeAssignmentMarker(double[] coordinates, String description, Bitmap icon){
        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(coordinates[0],coordinates[1]))
                .title(description));
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    public boolean locationPermissionCheck(){
        return ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
}
