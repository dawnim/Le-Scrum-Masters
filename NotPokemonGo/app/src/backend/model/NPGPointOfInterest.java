package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.LatLng;
import com.le_scrum_masters.notpokemongo.R;

import java.util.ArrayList;
import java.util.List;

import static com.le_scrum_masters.notpokemongo.R.drawable.parkis;

/**
 * Created by Albin on 2016-09-29.
 */
public class NPGPointOfInterest implements PointOfInterest {
    private String name;
    private String address;
    private String id;
    private List<Integer> placeType;
    private Bitmap image;
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
    public void setPlaceTypes(List<Integer> types) {
        this.placeType = types;
    }

    @Override
    public List<Integer> getPlaceTypes() {
        return placeType;
    }

    @Override
    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public Boolean isCompleted() {
        return completed;
    }

    @Override
    public void setCompleted(Boolean bool){
        completed = bool;
    }
}
