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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;



public class MapBehaviour implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequestHighAccuracy;
    private LatLng currentLocation;
    private Context contextActivity;
    private GoogleMap map;
    private boolean requestingLocationUpdates;

    private int updateInterval = 5000; //ms

    public MapBehaviour(Context contextActivity, GoogleMap map){
        this.contextActivity = contextActivity;
        this.map = map;
    }

    public void enableLocationUpdates(){
        requestingLocationUpdates = true;
        connectToLocationServices();
    }

    public void disableLocationUpdates(){
        requestingLocationUpdates = false;
        googleApiClient.disconnect();
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

        locationRequestHighAccuracy = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(updateInterval)
                .setFastestInterval(updateInterval);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequestHighAccuracy);

        //can be used to check if user settings allow for specific LocationSettingsRequest
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());

            requestLocationUpdates();
    }

    protected void requestLocationUpdates() {
       requestingLocationUpdates = true;
        if (ContextCompat.checkSelfPermission(contextActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequestHighAccuracy, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(requestingLocationUpdates){
        currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20));
        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
