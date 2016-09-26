package model;

public interface AssignmentItem {
    String getName();
    Place getLocation();
    int getProgress();
    int getImageId();
    Boolean isInProgress();
    Boolean isCompleted();

    void setProgress();

}