package com.le_scrum_masters.notpokemongo.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.location.places.Place;
import com.le_scrum_masters.notpokemongo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import adapters.CardListAdapter;

import model.NPGPointOfInterest;
import model.POICallback;
import services.NPGPlaceBasedListHelper;

import static android.R.attr.path;

public class POIActivity extends AppCompatActivity{

    private static String placeID;

    private NPGPointOfInterest poi;

    TextView t;
    ImageView icon;
    ImageButton videoBtn, doneBtn;
    ImageButton completeBtn;
    MediaController controller;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    VideoView videoView;
    SharedPreferences pref;



    Bundle b;

    List<Bitmap> images = new ArrayList<>();
    List<Integer> mp3fileInt = new ArrayList<>();
    Integer videofileInt;

    static POICallback poiCallback;
    static ImageView placePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.poi = poiCallback.getPointOfInterest(this.placeID);

        //Get some vars setup some wins
        setContentView(R.layout.activity_poi_info);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        t = (TextView)findViewById(R.id.titleView);
        t.setText(this.poi.getName());

        icon = (ImageView) findViewById(R.id.categoryImageView);

        if (this.poi.getIcon() != 0){
            setTypeIcon(this.poi.getIcon());
        }

        placePhoto = (ImageView) findViewById(R.id.placePhotoView);

        placePhoto.setImageBitmap(this.poi.getImage());


        //Card list shit going on here :)
        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        NPGPlaceBasedListHelper.context = this;

        images = NPGPlaceBasedListHelper.getImagesForPlaceType(this.poi.getActivePlaceType());

        mp3fileInt = NPGPlaceBasedListHelper.getMp3FilenamesForPlaceType(this.poi.getActivePlaceType());

        CardListAdapter cardListAdapter = new CardListAdapter(images,mp3fileInt,this);

        mRecyclerView.setAdapter(cardListAdapter);


        //Video shit -------------------------------------

        videoBtn = (ImageButton)findViewById(R.id.play_video_btn);

        videofileInt = NPGPlaceBasedListHelper.getVideoFileIntegerForPlaceType(this.poi.getActivePlaceType());

        //PLAY VIDEO ON CLICK
        controller = new MediaController(this);
        videoView = (VideoView)findViewById(R.id.videoView);

        if (videoView != null && videofileInt != null) {

            videoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controller.setAnchorView(videoView);
                    controller.setMediaPlayer(videoView);

                    videoView.setMediaController(controller);

                    String path = "android.resource://" + getPackageName() + "/" + videofileInt;
                    videoView.setVideoURI(Uri.parse(path));
                    videoView.seekTo(100);

                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
                    params.width = metrics.widthPixels;
                    params.height = metrics.heightPixels;
                    params.leftMargin = 0;
                    videoView.setLayoutParams(params);

                    videoView.start();
                    videoBtn.setVisibility(View.INVISIBLE);

                }
            });
        }else {
            videoBtn.setVisibility(View.INVISIBLE);
            TextView noVideoTxt = (TextView)findViewById(R.id.no_video_txt);
            noVideoTxt.setVisibility(View.VISIBLE);
        }

        //Video shit end ---------------------------------------


        pref = this.getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE);

        //DONE BUTTON
        doneBtn = (ImageButton)findViewById(R.id.done_button);

        if (poi.isCompleted()){
            doneBtn.setBackgroundColor(Color.parseColor("#fdcc32"));
        }

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationID = poi.getID();

                //SAVE FINNISHED LOCATION
                SharedPreferences.Editor editor = pref.edit();
                Set<String> finnishedLocations = pref.getStringSet(getString(R.string.finnishLoc_name),null);
                if (finnishedLocations == null) {
                    finnishedLocations = new TreeSet<>();
                }
                if (!(finnishedLocations.contains(locationID))){
                    doneBtn.setBackgroundColor(Color.parseColor("#fdcc32"));
                    finnishedLocations.add(locationID);
                    editor.putStringSet(getString(R.string.finnishLoc_name),finnishedLocations);
                    editor.apply();
                    poiCallback.completePOI(poi);
                }
            }
        });
    }

    private void setTypeIcon(int id){
        icon.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), id));
    }

    /*public static void setPlacephoto(Bitmap photo) {
        placePhoto.setImageBitmap(photo);
    }*/

    public static void setPoiCallback(POICallback poiCallback1){
        poiCallback = poiCallback1;
    }

    public static void setPlaceID(String id){
        placeID = id;
    }
}
