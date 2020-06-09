package com.example.admin.sensorapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;

public class ShowDataListView1 extends AppCompatActivity
{
    DataBaseHelper1 mydb = new DataBaseHelper1(this);
    SQLiteDatabase db;
    private ArrayList<String> Timestamp = new ArrayList<String>();
    private ArrayList<Float> X = new ArrayList<Float>();
    private ArrayList<Float> Y = new ArrayList<Float>();
    private ArrayList<Float> Z = new ArrayList<Float>();
    private ArrayList<Float> NEW_X = new ArrayList<Float>();
    private ArrayList<Float> NEW_Y = new ArrayList<Float>();
    private ArrayList<Float> NEW_Z = new ArrayList<Float>();

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
        Cursor cursor = db.rawQuery("SELECT * FROM  Accelerometer_New",null);
        Timestamp.clear();
        X.clear();
        Y.clear();
        Z.clear();
        NEW_X.clear();
        NEW_Y.clear();
        NEW_Z.clear();

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
            NEW_X.add(cursor.getFloat(4));
            NEW_Y.add(cursor.getFloat(5));
            NEW_Z.add(cursor.getFloat(6));
        }
        CustomAdapter1 ca = new CustomAdapter1(ShowDataListView1.this, Timestamp, X, Y, Z, NEW_X, NEW_Y, NEW_Z);
        lv.setAdapter(ca);
        //code to set adapter to populate list
        cursor.close();
    }
}
