package com.le_scrum_masters.notpokemongo.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, Observer, POICallback {
    private static final int GOOGLE_API_CLIENT_ID = 0;

    private GoogleMap mMap;
    Intent POIIntent;
    Bundle b;

    ArrayList<NPGPointOfInterest> places;
    NPGPOIDirector dir;

    Bitmap currentPlacePhoto;
    MapBehaviour mapBehaviour;
    Observer observer;

    POICallback poiCallback;
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

        b = new Bundle();

        dir = new NPGPOIDirector(new GoogleApiClient.Builder(MapsActivity.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .build(), this);

        dir.massiveSearch();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapBehaviour = new MapBehaviour(this,observer, mMap);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker arg0) {

                for(NPGPointOfInterest poi : places){
                    if(arg0.getTitle().equals(poi.getName())){
                        b.putString("Name", poi.getName());
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

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(57.6884, 11.9778), 11));

    }

    public void placeAssignmentMarker(LatLng coordinates, String description, int drawableID){
        //LatLng latLng = new LatLng(coordinates[0],coordinates[1]);
        int id = drawableID;

        Marker marker = mMap.addMarker(new MarkerOptions().position(coordinates).title(description).icon(BitmapDescriptorFactory.fromResource(id)));

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

        /*for (int i = 0; i < places.size(); i++){
            Log.e("lol","place: " + places.get(i));
            //NPGPointOfInterest poi = new NPGPointOfInterest(places.get(i).getName().toString(), places.get(i).getAddress().toString(), places.get(i).getId(), places.get(i).getLatLng());

            //pois.add(poi);
            placeAssignmentMarker(places.get(i).getCoords(), places.get(i).getName());
        }*/

        for (NPGPointOfInterest place : places){
            Log.e("lol","place: " + place.getImage());
            //NPGPointOfInterest poi = new NPGPointOfInterest(places.get(i).getName().toString(), places.get(i).getAddress().toString(), places.get(i).getId(), places.get(i).getLatLng());

            //pois.add(poi);

            //System.out.println("Icon ID is: " + place.getIcon());

            placeAssignmentMarker(place.getCoords(), place.getName(), place.getIcon());
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
    }

    @Override
    public void returnPlacephoto() {
        POIActivity.setPlacephoto(currentPlacePhoto);
    }

}
