package model;


import com.le_scrum_masters.notpokemongo.R;

/**
 * Created by Albin on 2016-09-26.
 */
public class NPGAssignmentGenerator implements AssignmentGenerator {
    private final int RADIUS = 20;

    private MapDirector mapDirector;
    private AssignmentToDB dbManager;

    public NPGAssignmentGenerator(MapDirector mapDirector, AssignmentToDB dbManager){
        this.mapDirector = mapDirector;
        this.dbManager = dbManager;
    }

    @Override
    public void generateDailyPersonalAssignment() {
        Place place = mapDirector.getPlaceWithinRadius(RADIUS, Place.PlaceType.PERSONAL);

        NPGAssignmentItem assignment = new NPGAssignmentItem("Go To", place, R.drawable.walking, 1);

        dbManager.saveAssignemntToDB(assignment);
    }

    @Override
    public void generateDailyCommunityAssignment() {

    }
}
