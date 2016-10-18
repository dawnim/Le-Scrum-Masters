package model;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferObserver;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import services.NPGCoordinates;

import static com.google.android.gms.location.places.AutocompleteFilter.TYPE_FILTER_NONE;

/**
 * Created by Albin on 2016-09-29.
 */
public class NPGPOIDirector extends Observable{
    private GoogleApiClient googleApiClient;
    private final String LOG_TAG = "NPGPOIDirector";

    private ArrayList<NPGPointOfInterest> mPlaces = new ArrayList<>();


    public NPGPOIDirector(GoogleApiClient googleApiClient, Observer observer){
        this.googleApiClient = googleApiClient;

        addObserver(observer);
        /* The googleApiClient should look similar to this:
        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .build();

        //In the "header"
        private static final int GOOGLE_API_CLIENT_ID = 0;
         */

    }

    private void findPlaceBy(final int TYPE, String phrase, LatLngBounds bounds){
        //LatLngBounds bounds = new LatLngBounds(new LatLng(57.67524480400853, 11.946945190429688), new LatLng(57.71120876687646, 11.988036632537842));


        AutocompleteFilter filter = new AutocompleteFilter.Builder().setTypeFilter(TYPE_FILTER_NONE).build();

        PendingResult<AutocompletePredictionBuffer> result1 =
                Places.GeoDataApi.getAutocompletePredictions(googleApiClient, phrase,
                        bounds, filter);

        result1.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
            @Override
            public void onResult(AutocompletePredictionBuffer autocompletePredictions) {
                for (AutocompletePrediction acp : autocompletePredictions){
                    String placeId = acp.getPlaceId();

                    if (mPlaces.size() < 10){
                        Places.GeoDataApi.getPlaceById(googleApiClient, placeId)
                                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                                    @Override
                                    public void onResult(PlaceBuffer places) {
                                        if (places.getStatus().isSuccess() && places.getCount() > 0) {
                                            final Place myPlace = places.get(0);
                                            if (myPlace.getPlaceTypes().contains(TYPE)){
                                                Log.i(LOG_TAG, "Place found: " + myPlace.getName() + " TYPE: " + myPlace.getPlaceTypes() + " LatLng: " + myPlace.getLatLng());

                                                final NPGPointOfInterest tmp = new NPGPointOfInterest(myPlace.getName().toString(), myPlace.getAddress().toString(), myPlace.getId(), myPlace.getLatLng());
                                                tmp.setPlaceTypes(myPlace.getPlaceTypes());
                                                getPlacePhoto(tmp);
                                                addToPlaces(tmp);
                                            }
                                        } else {
                                            Log.e(LOG_TAG, "Place not found");
                                        }
                                        places.release();
                                    }
                                });
                    }
                }
                autocompletePredictions.release();
            }
        });

    }

    public void addToPlaces(NPGPointOfInterest place){
        if (mPlaces.size() < 10){
            mPlaces.add(place);
            setChanged();
            notifyObservers();
            clearChanged();
        }
    }

    public ArrayList<NPGPointOfInterest> getPlaces(){
        return this.mPlaces;
    }

    public void massiveSearch(LatLng coords){

        this.mPlaces.clear();

        LatLngBounds bounds = NPGCoordinates.toBounds(coords, 5000);

        String[] arr = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

        /*
            Existing placetypes:
                cafe - 15
                Campground - 16
                Department store - 29
                Food - 38
                Hospital - 50
                Library - 55
        */
        int[] types = new int[]{55,50,16,15,29,38,1035};

        int i = 0;
        for (int type : types){
            for (String tmp : arr){
                findPlaceBy(type, tmp, bounds);
            }
        }

    }

    private void getPlacePhoto(final NPGPointOfInterest poi){
        // Get a PlacePhotoMetadataResult containing metadata for the first 10 photos.
        PendingResult<PlacePhotoMetadataResult> result = Places.GeoDataApi.getPlacePhotos(googleApiClient, poi.getID());
        result.setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {
            @Override
            public void onResult(@NonNull PlacePhotoMetadataResult placePhotoMetadataResult) {
                PlacePhotoMetadataBuffer photoMetadataBuffer = placePhotoMetadataResult.getPhotoMetadata();

                // Get the first photo in the list.
                if (photoMetadataBuffer != null && photoMetadataBuffer.getCount() != 0){
                    PlacePhotoMetadata photo = photoMetadataBuffer.get(0);
                    // Get a full-size bitmap for the photo.


                    PendingResult<PlacePhotoResult> tmp_result = photo.getPhoto(googleApiClient);
                    tmp_result.setResultCallback(new ResultCallback<PlacePhotoResult>() {
                        @Override
                        public void onResult(@NonNull PlacePhotoResult placePhotoResult) {
                            poi.setImage(placePhotoResult.getBitmap());
                        }
                    });
                }

                photoMetadataBuffer.release();
            }
        });
    }

}
