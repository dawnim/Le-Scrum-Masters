package com.le_scrum_masters.notpokemongo.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import java.util.Observable;
import java.util.Observer;

import maps.MapBehaviour;
import model.NPGPOIDirector;
import model.NPGPointOfInterest;
import model.POICallback;
import services.NPGPlaceBasedListHelper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, Observer, POICallback, GoogleApiClient.ConnectionCallbacks {
    private static final int GOOGLE_API_CLIENT_ID = 0;
    boolean isStartingUp = true;

    private GoogleMap mMap;
    Intent POIIntent;
    Bundle b;

    TextView counter;
    int completedAmount;

    ArrayList<NPGPointOfInterest> places;
    ArrayList<Marker> markers;
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

        markers = new ArrayList<Marker>();

        b = new Bundle();

        buildGoogleApiClient();

        dir = new NPGPOIDirector(mGoogleApiClient, this);

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
                    if (arg0.getTitle().equals(poi.getName())) {
                        b.putString("Name", poi.getName());
                        b.putInt("ActiveType", poi.getActivePlaceType());
                        b.putInt("Type", poi.getPlaceTypes().get(0));
                        b.putInt("Icon", poi.getIcon());


                        if(poi.getImage() != null){
                            Bitmap photo = Bitmap.createScaledBitmap(poi.getImage(), 200, 200, false);
                            b.putParcelable("Image", photo);
                        }

                        POIIntent.putExtras(b);
                        currentPlacePhoto = poi.getImage();
                        startActivity(POIIntent);


                        POIActivity.setPoiCallback(poiCallback);
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

    public void placeAssignmentMarker(LatLng coordinates, String description, int drawableID){
        //LatLng latLng = new LatLng(coordinates[0],coordinates[1]);
        int id = drawableID;
        markers.add(mMap.addMarker(new MarkerOptions().position(coordinates).title(description).icon(BitmapDescriptorFactory.fromResource(id))));
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

        for (NPGPointOfInterest place : places){
            Log.e("lol","place: " + place.getImage());
            if(place.isOnMap() != true){
                placeAssignmentMarker(place.getCoords(), place.getName(), place.getIcon());
                place.setIsOnMap(true);
            }
        }

        Log.e("Yeah", "places: " + places.size());
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.i("update", "update()");
        if (observable.getClass() == NPGPOIDirector.class){
            placeMarkersOnMap();
            Log.i("update", "update() inner");
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
        POIActivity.setPlacephoto(currentPlacePhoto);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    public void completePOI(NPGPointOfInterest poi){
        poi.setCompleted(true);
        Marker marker = null;
        for (Marker m : markers){
            if(m.getTitle().equals(poi.getName())){
                marker = m;
            }
        }
        marker.remove();
        markers.remove(marker);
        markers.add(mMap.addMarker(new MarkerOptions().position(poi.getCoords()).title(poi.getName()).icon(BitmapDescriptorFactory.fromResource(poi.getIcon()))));
        completedAmount += 1;
        counter.setText(Integer.toString(completedAmount));
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("Connection", "Suspended");
    }

    public ArrayList<NPGPointOfInterest> getPlaces() {
        return places;
    }

}
