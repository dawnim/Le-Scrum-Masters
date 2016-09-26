package model;

/**
 * Created by Albin on 2016-09-26.
 */
public interface MapDirector {
    Place getPlaceWithinRadius(int kilometres); // returns a Place within a given radius
    Place getPlaceWithinRadius(int kilometres, Place.PlaceType type); // returns a Place within a given radius
}
