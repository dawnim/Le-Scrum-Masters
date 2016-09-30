package model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import services.NPGCoordinates;
import services.NeededForPlaceStuff;

/**
 * Created by Albin on 2016-09-29.
 */
public class NPGPOIDirector implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private Context context;

    public NPGPOIDirector(Context context, FragmentActivity activity) {
        googleApiClient = new GoogleApiClient
                .Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(activity, this)
                .build();

        this.context = context;

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public NPGPointOfInterest findPlaceWithinRadius(int radius, final int type) { //e.g. type = Place.TYPE_LIBRARY
        final NeededForPlaceStuff nfps = new NeededForPlaceStuff();

        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("ERROR!!!!", "ACCESS DEEEENIEEED!");
        }

        PendingResult<PlaceLikelihoodBuffer> tmp_result = Places.PlaceDetectionApi
                .getCurrentPlace(googleApiClient, null);
        tmp_result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    if (placeLikelihood.getLikelihood() > 1){
                        nfps.setPlace(placeLikelihood.getPlace());
                        break;
                    }
                }
                likelyPlaces.release();
            }
        });

        Place currentPlace = nfps.getPlace();

        NPGPointOfInterest gpoi = new NPGPointOfInterest(currentPlace.getName().toString(),currentPlace.getAddress().toString(), currentPlace.getId(), currentPlace.getLatLng());
        return gpoi;
        /*
        LatLngBounds bounds = NPGCoordinates.toBounds(currentPlace.getLatLng(), radius);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                .build();

        PendingResult<AutocompletePredictionBuffer> result =
                Places.GeoDataApi.getAutocompletePredictions(googleApiClient, "",
                        bounds, typeFilter);

        final NeededForPlaceStuff placeStuff = new NeededForPlaceStuff();

        result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
            @Override
            public void onResult(AutocompletePredictionBuffer autocompletePredictions) {
                for (AutocompletePrediction acp : autocompletePredictions){
                    if (acp.getPlaceTypes().contains(type)){
                        PendingResult<PlaceBuffer> tmp = Places.GeoDataApi.getPlaceById(googleApiClient, acp.getPlaceId());

                        tmp.setResultCallback(new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(PlaceBuffer places) {
                                for (Place place : places){
                                    placeStuff.setPlace(place);
                                }
                            }
                        });
                    }
                }
            }
        });

        NPGPointOfInterest poi = new NPGPointOfInterest(placeStuff.getPlace().getName().toString(), placeStuff.getPlace().getAddress().toString(), placeStuff.getPlace().getId(), placeStuff.getPlace().getLatLng());

        return poi;*/
    }

}
