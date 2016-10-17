package adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.le_scrum_masters.notpokemongo.R;

import java.util.List;

/**
 * Created by Albin on 2016-10-17.
 */

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {
    private List<Bitmap> images;
    private List<String> mp3files;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public String mp3filename;
        private Bitmap image;
        private ImageButton imageButton;
        private ImageView imageView;
        public ViewHolder(View v, ImageView imageView, ImageButton imageButton) {
            super(v);
            cardView = v;
            this.imageView = imageView;
            this.imageButton = imageButton;

            this.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Play MP3 file here plis.
                    //mp3-file name is this.mp3filename
                }
            });
        }

        public void setImage(Bitmap image){
            this.image = image;

            imageView.setImageBitmap(this.image);

            Log.e("image:", "Image set:" + this.image.toString());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardListAdapter(List<Bitmap> images, List<String> mp3filenames) {
        this.images = images;
        this.mp3files = mp3filenames;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        //v.setLayoutParams(new RecyclerView.LayoutParams(150,150));

        ImageView imageView = (ImageView) v.findViewById(R.id.card_image);

        ImageButton imageButton = (ImageButton) v.findViewById(R.id.card_btn);

        ViewHolder vh = new ViewHolder(v, imageView, imageButton);

        return vh;
        // create a new view
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        //ViewHolder vh = new ViewHolder(null);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setImage(images.get(position));
        holder.mp3filename = mp3files.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return images.size();
    }
}

