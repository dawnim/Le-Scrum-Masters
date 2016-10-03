package com.le_scrum_masters.notpokemongo.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.le_scrum_masters.notpokemongo.R;

import model.NPGPOIDirector;
import model.NPGPointOfInterest;
import model.old.NPGAssignmentItem;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private NPGPointOfInterest[] pointsOfInterest;
    private Place location;
    Intent POIIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        POIIntent = new Intent(this, POIActivity.class);

        NPGPOIDirector dir = new NPGPOIDirector(this, this);

        //Log.e("meh", "" + dir.findPlaceWithinRadius(1000, Place.TYPE_CAFE));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                if(arg0.getTitle().equals("Marker on Chalmers")) // if marker source is clicked
                    startActivity(POIIntent);
                return true;
            }

        });

        // Add a marker in Chalmers and move the camera
        LatLng chalmers = new LatLng(57.6884, 11.9778);
        mMap.addMarker(new MarkerOptions().position(chalmers).title("Marker on Chalmers"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chalmers, 11));
        //placeAssignmentMarker(new NPGAssignmentItem("big d's crib",location,0,0)); //test

        centerMapOnUserLoc(mMap);
    }

    public void centerMapOnUserLoc(GoogleMap map){

        // map.setMyLocationEnabled(true);

    }

    public void updateAssignments(){
        //assignments =
    }

    public void placeAssignmentMarker(double[] coordinates, String description, Bitmap icon){
        LatLng latLng = new LatLng(coordinates[0],coordinates[1]);
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(description));

        // how to get Bitmap item from a .bmp in res/drawable
        // icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.smiley);

        marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));
        marker.showInfoWindow();
    }
}
