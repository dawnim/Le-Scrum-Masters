package com.le_scrum_masters.notpokemongo.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
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

import java.util.ArrayList;

import model.NPGPOIDirector;
import model.NPGPointOfInterest;
import model.old.NPGAssignmentItem;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private static final int GOOGLE_API_CLIENT_ID = 0;

    private GoogleMap mMap;
    private Place location;
    Intent POIIntent;
    Bundle b;

    ArrayList<NPGPointOfInterest> places;
    NPGPOIDirector dir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        POIIntent = new Intent(this, POIActivity.class);
        b = new Bundle();

        dir = new NPGPOIDirector(new GoogleApiClient.Builder(MapsActivity.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .build());

        dir.massiveSearch();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                if(arg0.getTitle().equals("Marker on Chalmers")) { // if marker source is clicked
                    placeMarkersOnMap();
                }
                for(NPGPointOfInterest poi : places){
                    if(arg0.getTitle().equals(poi.getName())){
                        b.putString("Name", poi.getName());
                        b.putString("Type", poi.getPlaceType());
                        b.putParcelable("Image", poi.getImage());
                        POIIntent.putExtras(b);
                        startActivity(POIIntent);
                    }
                }
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

    /*public void placeAssignmentMarker(LatLng coordinates, String description, Bitmap icon){
        //LatLng latLng = new LatLng(coordinates[0],coordinates[1]);
        Marker marker = mMap.addMarker(new MarkerOptions().position(coordinates).title(description));

        // how to get Bitmap item from a .bmp in res/drawable
        // icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.smiley);

        marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));
        marker.showInfoWindow();
    }*/

    public void placeAssignmentMarker(LatLng coordinates, String description){
        //LatLng latLng = new LatLng(coordinates[0],coordinates[1]);
        Marker marker = mMap.addMarker(new MarkerOptions().position(coordinates).title(description));

        // how to get Bitmap item from a .bmp in res/drawable
        // icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.smiley);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("NPGPOIDirector", "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    private void placeMarkersOnMap(){
        places = dir.getPlaces();

        //ArrayList<NPGPointOfInterest> pois = new ArrayList<>();

        for (int i = 0; i < places.size(); i++){
            Log.e("lol","place: " + places.get(i));
            //NPGPointOfInterest poi = new NPGPointOfInterest(places.get(i).getName().toString(), places.get(i).getAddress().toString(), places.get(i).getId(), places.get(i).getLatLng());

            //pois.add(poi);
            placeAssignmentMarker(places.get(i).getCoords(), places.get(i).getName());
        }
        /*
        for (int i = 0; i < pois.size(); i++){
            placeAssignmentMarker(pois.get(i).getCoords(), pois.get(i).getName());
        }*/

        /*for (Place place : places){
            NPGPointOfInterest poi = new NPGPointOfInterest(place.getName().toString(), place.getAddress().toString(), place.getId(), place.getLatLng());

            pois.add(poi);
        }

        for (NPGPointOfInterest poi : pois){
            placeAssignmentMarker(poi.getCoords(), poi.getName());
        }*/


        Log.e("Yeah", "places: " + places.size());
    }
}
