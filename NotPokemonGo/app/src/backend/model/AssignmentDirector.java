package model;

import model.AssignmentItem;

public interface AssignmentDirector {
    AssignmentItem getDailyPersonalAssignment();
    AssignmentItem getDailyCommunityAssignment();
}