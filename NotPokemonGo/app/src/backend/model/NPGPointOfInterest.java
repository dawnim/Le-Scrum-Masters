package model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Albin on 2016-09-29.
 */
public class NPGPointOfInterest implements PointOfInterest {
    private String name;
    private String address;
    private String id;
    private LatLng coords;
    private Boolean completed = false;


    public NPGPointOfInterest(String name, String address, String id, LatLng latlng){
        this.name = name;
        this.address = address;
        this.id = id;
        this.coords = latlng;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public LatLng getCoords() {
        return this.coords;
    }

    @Override
    public PlaceType getPlaceType() {
        return null;
    }

    @Override
    public Bitmap getImage() {
        return null;
    }

    @Override
    public Boolean isCompleted() {
        return null;
    }
}
