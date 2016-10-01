package com.le_scrum_masters.notpokemongo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.le_scrum_masters.notpokemongo.R;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TaskDatabase db;
    FloatingActionButton goToTask;
    FloatingActionButton dbinfo;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,TaskActivity.class);
        goToTask = (FloatingActionButton)findViewById(R.id.fab);
        goToTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });



        dbinfo = (FloatingActionButton)findViewById(R.id.dbinfo);
        dbinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new TaskDatabase(MainActivity.this);
                db.openDataBase();
                ArrayList<String> tasks = db.getTasks();
                db.close();
                TextView tekst = (TextView) findViewById(R.id.textView);
                for(String s : tasks){
                    tekst.setText(s);
                }
            }
        });


    }

}
