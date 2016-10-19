package com.le_scrum_masters.notpokemongo.Activities;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.VideoView;

import com.le_scrum_masters.notpokemongo.R;

public class MainActivity extends Activity {


    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10;

    Intent mapsIntent;
    MediaPlayer mediaPlayer;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE);

        if(!sharedPreferences.getBoolean("first_startup", false)){
            //playTutorialVideo();
            sharedPreferences.edit().putBoolean("first_startup",false).commit();
        }



        mapsIntent = new Intent(this, MapsActivity.class);


        //Places
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else{
            startActivity(mapsIntent);
        }
    }


    /*private void playTutorialVideo() {
        mediaPlayer = MediaPlayer.create(this, R.raw.sillsound);
=======
    private void playTutorialVideo() {
        mediaPlayer = MediaPlayer.create(this, R.raw.penna);
>>>>>>> origin/master
        mediaPlayer.start();
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(mapsIntent);
                    // permission was granted, yay! Do the task you need to do.

        //mediaPlayer = MediaPlayer.create(this, R.raw.sillsound);


        /*Button tstBtn = (Button)findViewById(R.id.tstvid_btn);
        tstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.start();
            }
        });*/

                } else {

                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }
}