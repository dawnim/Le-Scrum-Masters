package com.le_scrum_masters.notpokemongo.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.le_scrum_masters.notpokemongo.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import maps.MapBehaviour;
import model.NPGPOIDirector;
import model.NPGPointOfInterest;
import model.POICallback;
import services.NPGCoordinates;
import services.NPGPlaceBasedListHelper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, Observer, POICallback, GoogleApiClient.ConnectionCallbacks {
    private static final int GOOGLE_API_CLIENT_ID = 0;
    boolean isStartingUp = true;

    private GoogleMap mMap;
    Intent POIIntent;
    Bundle b;

    TextView counter;
    int completedAmount,count;

    ArrayList<NPGPointOfInterest> places;
    ArrayList<Marker> markers = new ArrayList<Marker>();
    NPGPOIDirector dir;

    Bitmap currentPlacePhoto;
    MapBehaviour mapBehaviour;

    POICallback poiCallback;

    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        POIIntent = new Intent(this, POIActivity.class);

        poiCallback = this;

        counter = (TextView)findViewById(R.id.counter);

        b = new Bundle();

        buildGoogleApiClient();

        dir = new NPGPOIDirector(mGoogleApiClient, this);

        //GET FINNISHED LOCATIONS
        SharedPreferences pref = getSharedPreferences(getString(R.string.shared_pref_name),Context.MODE_PRIVATE);
        Set<String> finnishedLocations = pref.getStringSet(getString(R.string.finnishLoc_name),null);
        if(finnishedLocations != null) {
            for (String place : finnishedLocations) {
                dir.addPlaceToCompletedList(place);
                Log.e("Saved Place", place);
            }
        }

        count = pref.getInt(getString(R.string.finnish_int),0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapBehaviour = new MapBehaviour(this, this, mMap);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker arg0) {

                for (NPGPointOfInterest poi : places) {
                    if (poi.getID().equals((String) arg0.getTag())){
                        POIActivity.setPlaceID((String)arg0.getTag());

                        POIActivity.setPoiCallback(poiCallback);

                        startActivity(POIIntent);
                    }
                }
                return true;
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                //.addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .build();
        //mGoogleApiClient.connect();

        Log.e("Connection", "Trying to connect");
    }

    public void placeAssignmentMarker(NPGPointOfInterest poi){
        //LatLng latLng = new LatLng(coordinates[0],coordinates[1]);
        //int id = drawableID;
        Marker mMarker = mMap.addMarker(new MarkerOptions().position(poi.getCoords()).title(poi.getName()).icon(BitmapDescriptorFactory.fromResource(poi.getIcon())));
        mMarker.setTag(poi.getID());
        markers.add(mMarker);
        //markers.add(mMap.addMarker(new MarkerOptions().position(coordinates).title(description).icon(BitmapDescriptorFactory.fromResource(id))));
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
        if (markers != null){
            for (Marker marker : markers){
                marker.remove();
            }
            ArrayList<Marker> tmpMarkers = markers;
            markers.removeAll(tmpMarkers);
        }

        places = new ArrayList<>(dir.getPlaces());

        places.addAll(dir.getCompletedPOIList());
        /*for (NPGPointOfInterest place : dir.getCompletedPOIList()){
            places.add(place);
        }*/

        for (NPGPointOfInterest place : places){
            //Log.e("Placing Marker", place.getName());
            placeAssignmentMarker(place);
            /*if(place.isOnMap() != true){
                placeAssignmentMarker(place);
                place.setIsOnMap(true);
            }*/
        }

        Log.e("Place Markers", "places: " + places.size());

    }

    @Override
    public void update(Observable observable, Object data) {
        Log.i("update", "update()");
        if (observable.getClass() == NPGPOIDirector.class){
            Log.i("update", "update() inner");
            placeMarkersOnMap();
            updateCounterText();
        }
        if (observable.getClass() == MapBehaviour.class){
            Log.e("Update: ", "Update... MapBehaviour");

            if(isStartingUp) {
                isStartingUp = false;
                dir.massiveSearch(mapBehaviour.getCurrentLocation());
                mapBehaviour.setMapPositionToCurrentLocation(10);
            }
        }
    }

    @Override
    public void returnPlacephoto() {
        //POIActivity.setPlacephoto(currentPlacePhoto);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    public void completePOI(NPGPointOfInterest poi){
        poi.setCompleted(true);
        poi.setIsOnMap(false);

        /*Marker tmpMarker = null;
        for (Marker marker : markers){
            if (poi.getID().equals((String)marker.getTag())){
                tmpMarker = marker;
                marker.remove();
            }
        }
        this.markers.remove(tmpMarker);*/

        //markers.add(mMap.addMarker(new MarkerOptions().position(poi.getCoords()).title(poi.getName()).icon(BitmapDescriptorFactory.fromResource(poi.getIcon()))));
        dir.getPlaces().remove(poi);
        dir.addPlaceToCompletedList(poi);
        System.out.println("Adding new POI to map...");
        int size = dir.getPlaces().size();
        String[] arr = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        for(String s : arr){
            if(dir.getPlaces().size() == size){
                dir.findPlaceBy(poi.getActivePlaceType(), s, NPGCoordinates.toBounds(mapBehaviour.getCurrentLocation(), 2000));
                System.out.println("Nothing found... :(");
            } else {
                System.out.println("New POI found!");
                break;
            }
        }


        updateCounterText();
    }

    @Override
    public NPGPointOfInterest getPointOfInterest(String id) {
        NPGPointOfInterest tmp = null;
        for (NPGPointOfInterest poi : places){
            if (poi.getID().equals(id)){
                tmp = poi;
                break;
            }
        }

        return tmp;
    }

    private void updateCounterText(){
        counter.setText(Integer.toString(dir.getCompletedPOIList().size()));
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("Connection", "Suspended");
    }

    public ArrayList<NPGPointOfInterest> getPlaces() {
        return places;
    }

}
