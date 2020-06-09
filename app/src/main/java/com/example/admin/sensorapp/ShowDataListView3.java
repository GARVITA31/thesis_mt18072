package com.example.admin.sensorapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;

public class ShowDataListView3 extends AppCompatActivity
{
    DataBaseHelper3 mydb = new DataBaseHelper3(this);
    SQLiteDatabase db;
    private ArrayList<String> Timestamp = new ArrayList<String>();
    private ArrayList<Float> X = new ArrayList<Float>();
    private ArrayList<Float> Y = new ArrayList<Float>();
    private ArrayList<Float> Z = new ArrayList<Float>();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showdata_listview);
        lv = (ListView) findViewById(R.id.listview);
    }

    @Override
    protected void onResume()
    {
        displayData();
        super.onResume();
    }

    private void displayData()
    {
        db = mydb.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  Orientation",null);
        Timestamp.clear();
        X.clear();
        Y.clear();
        Z.clear();
//        if (cursor.moveToFirst())
//        {
//            do {
//                Timestamp.add(cursor.getString(cursor.getColumnIndex("Id")));
//                X.add(cursor.getFloat(cursor.getColumnIndex("x_axis")));
//                Y.add(cursor.getFloat(cursor.getColumnIndex("y_axis")));
//                Z.add(cursor.getFloat(cursor.getColumnIndex("z_axis")));
//            }
//            while (cursor.moveToNext());
//        }
        while(cursor.moveToNext())
        {
            Timestamp.add(cursor.getString(0));
            X.add(cursor.getFloat(1));
            Y.add(cursor.getFloat(2));
            Z.add(cursor.getFloat(3));
        }
        CustomAdapter3 ca = new CustomAdapter3(ShowDataListView3.this, Timestamp, X, Y, Z);
        lv.setAdapter(ca);
        //code to set adapter to populate list
        cursor.close();
    }
}
