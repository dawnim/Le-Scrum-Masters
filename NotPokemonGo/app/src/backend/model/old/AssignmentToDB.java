package model.old;

/**
 * Created by Albin on 2016-09-26.
 */
public interface AssignmentToDB {
    void saveAssignemntToDB(AssignmentItem assignment);
    AssignmentItem[] getAllAssignments();
    AssignmentItem getAssignment(String id);

}