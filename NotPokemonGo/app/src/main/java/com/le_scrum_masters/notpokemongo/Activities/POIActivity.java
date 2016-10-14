package com.le_scrum_masters.notpokemongo.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.le_scrum_masters.notpokemongo.R;

import model.POICallback;

public class POIActivity extends AppCompatActivity{

    TextView t;
    ImageView icon;
    static POICallback poiCallback;
    static ImageView placePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();

        setContentView(R.layout.activity_poi_info);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        t = (TextView)findViewById(R.id.titleView);
        t.setText(b.getString("Name"));
        icon= (ImageView) findViewById(R.id.categoryImageView);

        if(b.getInt("Icon") != 0){
            setTypeIcon(b.getInt("Icon"));

        }


        placePhoto = (ImageView) findViewById(R.id.placePhotoView);

        if (b.getParcelable("Image") != null){
            Bitmap image = (Bitmap)b.getParcelable("Image");
            placePhoto.setImageBitmap(image);
        }

        poiCallback.returnPlacephoto();
    }

    private void setTypeIcon(int id){
        icon.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), id));
    }

    public static void setPlacephoto(Bitmap photo) {
        placePhoto.setImageBitmap(photo);
    }

    public static void setPoiCallback(POICallback poiCallback1){
        poiCallback = poiCallback1;
    }
}
