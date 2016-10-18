package com.le_scrum_masters.notpokemongo.Activities;

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
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.location.places.Place;
import com.le_scrum_masters.notpokemongo.R;

import java.util.ArrayList;
import java.util.List;

import adapters.CardListAdapter;

import model.NPGPointOfInterest;
import model.POICallback;
import services.NPGPlaceBasedListHelper;

import static android.R.attr.path;

public class POIActivity extends AppCompatActivity{

    TextView t;
    ImageView icon;
    ImageButton videoBtn;
    ImageButton completeBtn;
    MediaController controller;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    VideoView videoView;

    List<Bitmap> images = new ArrayList<>();
    List<Integer> mp3fileInt = new ArrayList<>();
    Integer videofileInt;

    static POICallback poiCallback;
    static ImageView placePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle b = getIntent().getExtras();

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
            Bitmap image = b.getParcelable("Image");
            placePhoto.setImageBitmap(image);
        }

        completeBtn = (ImageButton)findViewById(R.id.imageButton);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<NPGPointOfInterest> places = poiCallback.getPlaces();
                for (NPGPointOfInterest poi : places) {
                    if (b.getString("Name").equals(poi.getName()) && !poi.isCompleted()) {
                        poiCallback.completePOI(poi);
                        finish();
                    }
                }

            }
        });

        videoBtn = (ImageButton)findViewById(R.id.play_video_btn);


        poiCallback.returnPlacephoto();


        //Card list shit going on here :)
        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        NPGPlaceBasedListHelper.context = this;

        images = NPGPlaceBasedListHelper.getImagesForPlaceType(b.getInt("ActiveType"));

        mp3fileInt = NPGPlaceBasedListHelper.getMp3FilenamesForPlaceType(b.getInt("ActiveType"));

        videofileInt = NPGPlaceBasedListHelper.getVideoFileIntegerForPlaceType(b.getInt("ActiveType"));

        //PLAY VIDEO ON CLICK
        controller = new MediaController(this);
        videoView = (VideoView)findViewById(R.id.videoView);

        if (videoView != null && videofileInt != null) {

            String path = "android.resource://" + getPackageName() + "/" + videofileInt;
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,
                    MediaStore.Images.Thumbnails.MINI_KIND);

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

        CardListAdapter cardListAdapter = new CardListAdapter(images,mp3fileInt,this);

        mRecyclerView.setAdapter(cardListAdapter);
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
