package services;

/**
 * Created by Albin on 2016-09-26.
 */
public class NPGCoordinates {
    public double distanceBetweenCoordinates(int[] coord1, int[] coord2){ //Returns the distance between to points in kilometres
        int radius_earth = 6371000;

        double lat1 = Math.toRadians(coord1[0]);
        double lat2 = Math.toRadians(coord2[0]);

        double latDelta = Math.toRadians(coord1[0]-coord2[0]);
        double lonDelta = Math.toRadians(coord1[1]-coord2[1]);

        double a = Math.sin(latDelta/2) * Math.sin(latDelta/2) * Math.cos(lat1) * Math.cos(lat2) * Math.sin(lonDelta/2) * Math.sin(lonDelta/2);

        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double distance = radius_earth * b;

        return distance;
    }
}