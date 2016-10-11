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

public class POIActivity extends AppCompatActivity {

    TextView t;
    ImageView icon;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();

        setContentView(R.layout.activity_poi);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        t = (TextView)findViewById(R.id.textView);
        t.setText(b.getString("Name"));
        icon= (ImageView) findViewById(R.id.imageView3);

        int type = b.getInt("Type");
        switch(type){
            case Place.TYPE_RESTAURANT: setTypeIcon(R.drawable.cutlery);
                break;
            case Place.TYPE_AIRPORT: setTypeIcon(R.drawable.airport);
                break;
            case Place.TYPE_CAFE: setTypeIcon(R.drawable.cafe);
                break;
            case Place.TYPE_BUS_STATION: setTypeIcon(R.drawable.trolleybus);
                break;
            case Place.TYPE_PARK: setTypeIcon(R.drawable.tree);
        }

        img= (ImageView) findViewById(R.id.imageView4);

        if (b.getParcelable("Image") != null){
            Bitmap image = (Bitmap)b.getParcelable("Image");
            img.setImageBitmap(image);
        }
    }

    private void setTypeIcon(int id){
        icon.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), id));
    }
}
