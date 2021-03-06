package model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albin on 2016-09-29.
 */
public interface PointOfInterest {
    String getName();
    String getAddress();
    String getID();
    LatLng getCoords();

    void setPlaceTypes(List<Integer> types);
    List<Integer> getPlaceTypes();
    int getActivePlaceType();

    void setImage(Bitmap image);
    Bitmap getImage();

    int getIcon();

    Boolean isCompleted();
    Boolean isOnMap();

    void setCompleted(Boolean bool);
    void setIsOnMap(Boolean bool);
}
