package model;

/**
 * Created by Albin on 2016-09-26.
 */
public class NPGAssignmentItem implements AssignmentItem {
    private String name;
    private Place location;
    private int progress = 0;
    private int goal = 0;
    private int imageId;
    private Boolean inProgress = false;
    private Boolean completed = false;

    public NPGAssignmentItem(String name, Place location, int imageId, int goal){
        this.name = name;
        this.location = location;
        this.imageId = imageId;
        this.goal = goal;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Place getLocation() {
        return this.location;
    }

    @Override
    public String getProgress() {
        return "" + this.progress + " / " + this.goal;
    }

    @Override
    public int getImageId() {
        return this.imageId;
    }

    @Override
    public Boolean isInProgress() {
        return this.inProgress;
    }

    @Override
    public Boolean isCompleted() {
        return this.isCompleted();
    }

    @Override
    public void incProgress() {
        if (inProgress){
            if (progress+1 == goal){
                this.completed = true;
            } else {
                progress += 1;
            }
        }
    }

    @Override
    public void decProgress() {
        if (inProgress) {
            if (progress != 0) {
                progress -= 1;
            }
        }
    }

    @Override
    public void setInProgress(Boolean inProgress){
        this.inProgress = inProgress;
    }
}
