package com.le_scrum_masters.notpokemongo.Activities;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Melina on 2016-09-27.
 */

public class TaskDatabase extends SQLiteOpenHelper {

    private static String DATABASE_PATH = "com.le_scrum_masters.notpokemongo";
    public static final String DATABASE_NAME = "task.db";
    public static final String TABLE_NAME = "task_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TASK";
    private SQLiteDatabase db;
    private final Context myContext;


    public TaskDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getReadableDatabase();
        this.myContext = context;
    }

    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null;
    }

    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public synchronized void close() {

        if(db != null)
            db.close();

        super.close();

    }


/*
    public String getTasks() {

        String data = null;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor!=null && cursor.getCount()>0)
            {
                cursor.moveToFirst();
                do {
                    data = cursor.getString(0);
                } while (cursor.moveToNext());
            }

        return data;
    }
    */

    public ArrayList<String> getTasks() {
        /*
        ArrayList<ArrayList<String>> retList = new ArrayList<ArrayList<String>>();
        ArrayList<String> list = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = myDataBase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
                list = new ArrayList<String>();
                for(int i=0; i<cursor.getColumnCount(); i++)
                {
                    list.add( cursor.getString(i) );
                }
                retList.add(list);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return retList;

    }*/

        ArrayList<String> values = new ArrayList<String>();
        Cursor allrows = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        allrows.moveToFirst();
        while (allrows.moveToNext()) {
            String name = allrows.getString(allrows.getColumnIndex("NAME"));
            values.add(name);
        }
        return values;

    }
}
