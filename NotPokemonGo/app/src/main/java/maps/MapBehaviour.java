package maps;

/**
 * Created by David on 2016-10-04.
 */
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.internal.FusedLocationProviderResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.Observable;
import java.util.Observer;

public class MapBehaviour extends Observable
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequestHighAccuracy;
    private LatLng lastLocation;
    private LatLng currentLocation;
    private Context contextActivity;
    private GoogleMap map;
    private boolean requestingCameraUpdates;
    private boolean connectedToApi = false;

    private static final int ANIMATE_SPEED_TURN = 1000;
    private static final int BEARING_OFFSET = 20;
    private int updateInterval = 3000; //ms
    private int minUpdateInterval = 1500; //ms
    private int scale = 13;

    public MapBehaviour(Context contextActivity, Observer observer, GoogleMap map){
        this.contextActivity = contextActivity;
        this.map = map;

        addObserver(observer);
        connectToLocationServices();
    }

    public void enableCameraUpdates(){
        requestingCameraUpdates = true;
        if(connectedToApi){
            connectToLocationServices();
        }
    }

    public void disableCameraUpdates(){
        requestingCameraUpdates = false;
    }

    public void connectToLocationServices(){
        googleApiClient = new GoogleApiClient.Builder(contextActivity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        connectedToApi = true;
        locationRequestHighAccuracy = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(updateInterval)
                .setFastestInterval(minUpdateInterval);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequestHighAccuracy);

        //can be used to check if user settings allow for specific LocationSettingsRequest
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());

            requestLocationUpdates();

        setMapPositionToCurrentLocation(scale);
    }

    private void requestLocationUpdates() {
        PendingResult pendingResult = null;

        if (ContextCompat.checkSelfPermission(contextActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequestHighAccuracy, this);
        }
        pendingResult.setResultCallback(new ResultCallback() {
            @Override
            public void onResult(@NonNull Result result) {
                isConnectedToApi();
                if(currentLocation==null){
                    Log.i("CURRENTLOCATION NULL", "In resultcallback");
                }

            }
        });


    }

    @Override
    public void onLocationChanged(Location location) {

        if(currentLocation != null){lastLocation = currentLocation;}
        currentLocation = new LatLng(location.getLatitude(),location.getLongitude());

        if(lastLocation != null) {

            if (requestingCameraUpdates) {
                CameraPosition cameraPosition =
                        new CameraPosition.Builder()
                                .target(currentLocation)
                                .bearing(getBearingBetweenCoords(lastLocation, currentLocation) + BEARING_OFFSET)
                                .tilt(map.getCameraPosition().tilt)
                                .zoom(20)
                                .build();
                map.animateCamera(
                        CameraUpdateFactory.newCameraPosition(cameraPosition),
                        ANIMATE_SPEED_TURN,
                        null
                );
            }
        }

        setChanged();
        notifyObservers();
        clearChanged();
    }
    @Override
    public void onConnectionSuspended(int i) {
        connectedToApi = false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        connectedToApi = false;
    }
    public boolean isConnectedToApi(){
        return connectedToApi;
    }

    public void setMapPositionToCurrentLocation(int scale){

        if(currentLocation!=null){
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, scale));
        }
    }

    public boolean isRequestingCameraUpdates(){
        return requestingCameraUpdates;
    }

    public LatLng getCurrentLocation(){
        return new LatLng(currentLocation.latitude, currentLocation.longitude);
    }

    private float getBearingBetweenCoords(LatLng start, LatLng stop){
        double latitude1 = Math.toRadians(start.latitude);
        double latitude2 = Math.toRadians(stop.latitude);
        double longitude1 = start.longitude;
        double longitude2 = stop.longitude;

        double longDiff= Math.toRadians(longitude2-longitude1);
        double y= Math.sin(longDiff)*Math.cos(latitude2);
        double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);
        double res = (Math.toDegrees(Math.atan2(y, x))+360)%360;

        float bearing = (float) res;
        return bearing;
    }
}