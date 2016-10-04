package model;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

import static com.google.android.gms.location.places.AutocompleteFilter.TYPE_FILTER_NONE;

/**
 * Created by Albin on 2016-09-29.
 */
public class NPGPOIDirector {
    private GoogleApiClient googleApiClient;
    private final String LOG_TAG = "NPGPOIDirector";

    public NPGPOIDirector(GoogleApiClient googleApiClient){
        this.googleApiClient = googleApiClient;

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

    private Place findPlaceBy(final int TYPE, String phrase){
        LatLngBounds bounds = new LatLngBounds(new LatLng(57.67524480400853, 11.946945190429688), new LatLng(57.71120876687646, 11.988036632537842));


        AutocompleteFilter filter = new AutocompleteFilter.Builder().setTypeFilter(TYPE_FILTER_NONE).build();

        PendingResult<AutocompletePredictionBuffer> result1 =
                Places.GeoDataApi.getAutocompletePredictions(googleApiClient, phrase,
                        bounds, filter);

        final temporaryPlace tmp_place = new temporaryPlace();

        result1.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
            @Override
            public void onResult(AutocompletePredictionBuffer autocompletePredictions) {
                for (AutocompletePrediction acp : autocompletePredictions){
                    String placeId = acp.getPlaceId();
                    Places.GeoDataApi.getPlaceById(googleApiClient, placeId)
                            .setResultCallback(new ResultCallback<PlaceBuffer>() {
                                @Override
                                public void onResult(PlaceBuffer places) {
                                    if (places.getStatus().isSuccess() && places.getCount() > 0) {
                                        final Place myPlace = places.get(0);
                                        if (myPlace.getPlaceTypes().contains(TYPE)){
                                            Log.i(LOG_TAG, "Place found: " + myPlace.getName() + " TYPE: " + myPlace.getPlaceTypes());

                                            tmp_place.setPlace(myPlace);
                                            tmp_place.setStr(myPlace.getName().toString());
                                            Log.e(LOG_TAG, "yeah: " + tmp_place.getPlace().getId());
                                        }
                                    } else {
                                        Log.e(LOG_TAG, "Place not found");
                                    }
                                    places.release();
                                }
                            });
                }
                autocompletePredictions.release();
            }
        });

        Log.e(LOG_TAG, "yeah: " + tmp_place.getStr());
        return tmp_place.getPlace();
    }

    public ArrayList<Place> massiveSearch(){
        String[] arr = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

        int[] types = new int[]{15,16,29,38,50,55,1013};

        ArrayList<Place> places = new ArrayList<>();
        for (int type : types){
            for (String tmp : arr){
                Place tmp_place = findPlaceBy(type, tmp);
                if (tmp_place != null){
                    places.add(tmp_place);
                }
            }
        }

        Log.e(LOG_TAG, "places: " + places.size());

        return places;

    }

    static class temporaryPlace{
        private static String str;
        private static Place place;

        public temporaryPlace(){
        }

        public void setPlace(Place place){
            this.place = place;
        }

        public Place getPlace(){
            return this.place;
        }

        public void setStr(String str){
            this.str = str;
        }

        public String getStr(){
            return this.str;
        }
    }

}
