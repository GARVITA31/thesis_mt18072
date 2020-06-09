package com.example.admin.sensorapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;

public class ShowDataListView4 extends AppCompatActivity
{
    DataBaseHelper4 mydb = new DataBaseHelper4(this);
    SQLiteDatabase db;
    private ArrayList<String> Timestamp = new ArrayList<String>();
    private ArrayList<Float> X = new ArrayList<Float>();
    private ArrayList<Float> Y = new ArrayList<Float>();
    private ArrayList<Float> Z = new ArrayList<Float>();
    private ArrayList<Double> DEG = new ArrayList<Double>();
    private ArrayList<Double> RAD = new ArrayList<Double>();
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
        Cursor cursor = db.rawQuery("SELECT * FROM  Magnetometer_New",null);
        Timestamp.clear();
        X.clear();
        Y.clear();
        Z.clear();
        DEG.clear();
        RAD.clear();
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
            DEG.add(cursor.getDouble(4));
            RAD.add(cursor.getDouble(5));

        }
        CustomAdapter4 ca = new CustomAdapter4(ShowDataListView4.this, Timestamp, X, Y, Z, DEG, RAD);
        lv.setAdapter(ca);
        //code to set adapter to populate list
        cursor.close();
    }
}
