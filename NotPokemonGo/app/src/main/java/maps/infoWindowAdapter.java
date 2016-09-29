package maps;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import model.old.NPGAssignmentItem;

/**
 * För att modifiera rutan över markers med t.ex. assignmentbilden och kanske några ords beskrivning
 * Created by David on 2016-09-27.
 */

public class infoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private NPGAssignmentItem assignment;

    public void setActiveAssignmentMarker(NPGAssignmentItem assignment){
        this.assignment = assignment;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = new View(null);

        return view;
    }
}
