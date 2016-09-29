package model;

import android.graphics.Bitmap;

/**
 * Created by Albin on 2016-09-29.
 */
public interface PointOfInterest {
    enum PlaceType{
        CAFE, PARK, LIBRARY;
    }
    String getName();
    String getAddress();
    String getID();
    String getCoords();
    //LatLngBounds getCoords();
    PlaceType getPlaceType();

    Bitmap getImage();

    Boolean isCompleted();
}
