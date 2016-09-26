package com.le_scrum_masters.notpokemongo.model;

import com.le_scrum_masters.notpokemongo.model.AssignmentItem;

public interface AssignmentDirector {
    AssignmentItem getDailyPersonalAssignment();
    AssignmentItem getDailyCommunityAssignment();
}