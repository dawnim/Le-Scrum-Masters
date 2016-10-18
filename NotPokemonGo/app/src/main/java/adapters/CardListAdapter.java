package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
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
    private List<Integer> mp3files;
    private Context ctx;
    private MediaPlayer mediaPlayer;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public int mp3fileInt;
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
    public CardListAdapter(List<Bitmap> images, List<Integer> mp3filenames, Context ctx) {
        this.images = images;
        this.mp3files = mp3filenames;
        this.ctx = ctx;
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
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (images != null){
            if (!(images.size()-1 < position)){
                holder.setImage(images.get(position));
            }
        }

        if (mp3files != null){
            if (!(mp3files.size()-1 < position)){
                holder.mp3fileInt = mp3files.get(position);
                holder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mediaPlayer = MediaPlayer.create(ctx.getApplicationContext(),mp3files.get(position));
                        mediaPlayer.start();
                    }
                });
            }
        }



        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (images == null) return 0;
        return images.size();
    }
}

