package model;

public interface AssignmentItem {
    String getName();
    Object getPosition();
    Object getProgress();
    Object getImage();
    Boolean isInProgress();
    Boolean isCompleted();

    void setProgress();

}