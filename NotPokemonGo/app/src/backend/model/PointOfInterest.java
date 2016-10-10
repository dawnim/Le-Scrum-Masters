package model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Albin on 2016-09-29.
 */
public interface PointOfInterest {
    String getName();
    String getAddress();
    String getID();
    LatLng getCoords();
    //LatLngBounds getCoords();
    String getPlaceType();

    Bitmap getImage();

    Boolean isCompleted();
}
