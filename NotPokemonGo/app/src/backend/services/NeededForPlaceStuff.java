package services;

import com.google.android.gms.location.places.Place;

/**
 * Created by Albin on 2016-09-29.
 */
public class NeededForPlaceStuff {
    private Place place;
    public Place getPlace(){
        return this.place;
    }

    public void setPlace(Place place){
        this.place = place;
    }
}
