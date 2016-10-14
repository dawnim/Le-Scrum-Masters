package model;

import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

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
    private int icon;
    private int type;

    public NPGPointOfInterest(String name, String address, String id, LatLng latlng, int type){
        this.name = name;
        this.address = address;
        this.id = id;
        this.coords = latlng;
        this.type = type;
        this.icon = 0;
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

    @Override
    public void setType(int type) {this.type = type;}

    @Override
    public int getType() {return type;}

    public void setIcon(int icon){this.icon = icon;}
    public int getIcon(){return icon;}
}
