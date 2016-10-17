package model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Albin on 2016-10-13.
 */

public interface POICallback {
    void returnPlacephoto();
    ArrayList<NPGPointOfInterest> getPlaces();
    void completePOI(NPGPointOfInterest poi);

}
