package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.location.places.Place;
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
    private int icon;
    private LatLng coords;
    private Boolean completed = false;
    private Boolean isOnMap = false;
    private int activePlaceType;


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


    /*
    Existing placetypes:
        cafe - 15
        Campground - 16
        Department store - 29
        Food - 38
        Hospital - 50
        Library - 55

     */
    @Override
    public void setPlaceTypes(List<Integer> types) {
        this.placeType = types;

        if (placeType != null && placeType.size() > 0){
            switch(types.get(0)){
                case Place.TYPE_CAFE:
                    activePlaceType = Place.TYPE_CAFE;
                    setIcon(R.drawable.cafe);
                    break;
                case Place.TYPE_RESTAURANT:
                    activePlaceType = Place.TYPE_RESTAURANT;
                    setIcon(R.drawable.cutlery);
                    break;
                case Place.TYPE_HOSPITAL:
                    activePlaceType = Place.TYPE_HOSPITAL;
                    setIcon(R.drawable.sjukhus);
                    break;
                case Place.TYPE_GROCERY_OR_SUPERMARKET:
                    activePlaceType = Place.TYPE_GROCERY_OR_SUPERMARKET;
                    setIcon(R.drawable.mataffar);
                    break;
                case Place.TYPE_LODGING:
                    activePlaceType = Place.TYPE_LODGING;
                    setIcon(R.drawable.hotell);
                    break;
                case Place.TYPE_SPA:
                    activePlaceType = Place.TYPE_SPA;
                    setIcon(R.drawable.hotell);
                    break;
                case Place.TYPE_LIBRARY:
                    activePlaceType = Place.TYPE_LIBRARY;
                    setIcon(R.drawable.bibliotek);
                    break;
                case Place.TYPE_BAR:
                    activePlaceType = Place.TYPE_BAR;
                    setIcon(R.drawable.bar);
                    break;
                default:
                    activePlaceType = 0;
                    setIcon(R.drawable.butik);
                    break;
            }
        } else{
            activePlaceType = 0;
            setIcon(R.drawable.butik);
        }



    }

    @Override
    public List<Integer> getPlaceTypes() {
        return placeType;
    }

    @Override
    public int getActivePlaceType(){
        return this.activePlaceType;
    }

    @Override
    public void setImage(Bitmap image) {
        this.image = image;
    }

    private void setIcon(int drawableID) {
        this.icon = drawableID;
    }

    @Override
    public int getIcon(){
        return this.icon;
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

        Log.e("SetCompleted()", "" + activePlaceType);

        switch(activePlaceType){
            case Place.TYPE_CAFE:
                setIcon(R.drawable.cafegold);
                break;
            case Place.TYPE_RESTAURANT:
                setIcon(R.drawable.cutlerygold);
                break;
            case Place.TYPE_HOSPITAL:
                setIcon(R.drawable.sjukhusgold);
                break;
            case Place.TYPE_GROCERY_OR_SUPERMARKET:
                setIcon(R.drawable.mataffargold);
                break;
            case Place.TYPE_LODGING:
                setIcon(R.drawable.hotellgold);
                break;
            case Place.TYPE_SPA:
                setIcon(R.drawable.hotellgold);
                break;
            case Place.TYPE_LIBRARY:
                setIcon(R.drawable.bibliotekgold);
                break;
            case Place.TYPE_BAR:
                setIcon(R.drawable.bargold);
                break;
            default:
                setIcon(R.drawable.checkgreen);
                break;
        }
    }

    public void setIsOnMap(Boolean bool){
        isOnMap = bool;
    }

    public Boolean isOnMap(){
        return this.isOnMap;
    }
}
