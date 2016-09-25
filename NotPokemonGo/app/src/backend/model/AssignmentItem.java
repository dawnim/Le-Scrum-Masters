package model;

public interface AssignmentItem {
    String getName();
    String[] getPosition();
    int getProgress();
    int getImageId();
    Boolean isInProgress();
    Boolean isCompleted();

    void setProgress();

}