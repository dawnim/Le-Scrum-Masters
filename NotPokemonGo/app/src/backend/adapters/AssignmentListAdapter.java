package adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.le_scrum_masters.notpokemongo.R;

/**
 * Created by Albin on 2016-09-25.
 */
public class AssignmentListAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] titleName;
    private final Integer[] imageId;

    public AssignmentListAdapter(Activity context, String[] titleName, Integer[] imageId){
        super(context, R.layout.list_item_assignment, titleName);

        this.context = context;
        this.titleName = titleName;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item_assignment, null, true);

        TextView title = (TextView) rowView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

        title.setText(titleName[position]);
        imageView.setImageResource(imageId[position]);

        return rowView;
    }

}