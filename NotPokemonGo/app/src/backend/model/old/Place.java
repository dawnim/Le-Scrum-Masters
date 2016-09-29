package model.old;

/**
 * Created by Albin on 2016-09-26.
 */
public interface Place {
    public enum PlaceType{
        PERSONAL, COMMUNITY;
    }

    int[] getCoordinates(); // index 0 = latitude, index 1 = longitude
    PlaceType getType();
}