package com.le_scrum_masters.notpokemongo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import model.AssignmentItem;

/**
 * Created by Albin on 2016-09-25.
 */
public class AssignmentActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        listView = (ListView) findViewById(R.id.listView);

        //AssignmentItem[] items = new AssignmentItem[]{};
        String[] items = new String[]{"Hola","Guten tag","Bonjour","Hello","Hej","Nihao"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_assignment, R.id.textView, items);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPos = position;

                String itemValue = (String) listView.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), itemValue, Toast.LENGTH_LONG).show();
                //kewl
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
