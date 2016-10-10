package com.le_scrum_masters.notpokemongo.Activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.le_scrum_masters.notpokemongo.R;

public class POIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();

        setContentView(R.layout.activity_poi);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        TextView t = (TextView)findViewById(R.id.textView);
        t.setText(b.getString("Name"));
        ImageView icon= (ImageView) findViewById(R.id.imageView3);
        //icon.setImageBitmap(bitmap depending on b.getString("Type"));
        ImageView img= (ImageView) findViewById(R.id.imageView4);
        img.setImageBitmap((Bitmap)b.getParcelable("Image"));
    }
}
