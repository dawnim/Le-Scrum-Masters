package model;

public interface AssignmentItem {
    String getName();
    Place getLocation();
    String getProgress();
    int getImageId();
    Boolean isInProgress();
    Boolean isCompleted();

    void incProgress();
    void decProgress();
    void setInProgress(Boolean inProgress);

}