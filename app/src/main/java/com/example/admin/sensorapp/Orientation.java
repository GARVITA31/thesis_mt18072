package com.example.admin.sensorapp;

import android.content.Context;
import android.content.Intent;
//import android.database.Cursor;
//import com.opencsv.CSVWriter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

public class Orientation extends MainActivity implements SensorEventListener
{

    private static final String TAG = "ORIENTATION_ACTIVITY";
    TextView x, y, z;
    Sensor mySensor;
    SensorManager sm;
    SensorEventListener eventListener;
    Button save, show, clear;
    private Button export;
    DataBaseHelper3 databaseHelper;
    private SensorManager sensorManager;
    Sensor orientation;
    Float x_value, y_value, z_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor);
        x = (TextView) findViewById(R.id.textView1);
        y = (TextView) findViewById(R.id.textView2);
        z = (TextView) findViewById(R.id.textView3);
        save = (Button) findViewById(R.id.saveButton);
        show = (Button) findViewById(R.id.showButton);
        clear = (Button) findViewById(R.id.clearButton);
        databaseHelper = new DataBaseHelper3(this);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Orientation.this, ShowDataListView3.class);
                startActivity(intent);
            }
        });
//        export = (Button)findViewById(R.id.export_acc);
//        export.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                exportDB();
//            }
//        });
        Log.d(TAG, "onCreate: Initialized sensor");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(Orientation.this, orientation, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: sensor done");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddData(x_value, y_value, z_value);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteData();
                toastMessage("Data Cleared!!");
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x.setText("X: " + sensorEvent.values[0]);
        y.setText("Y: " + sensorEvent.values[1]);
        z.setText("Z: " + sensorEvent.values[2]);
        x_value = sensorEvent.values[0];
        y_value = sensorEvent.values[1];
        z_value = sensorEvent.values[2];
//        AddData(x, y, z);
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void AddData(float x, float y, float z) {
        boolean insertData = databaseHelper.addDataOri(x, y, z);
        if (insertData) {
            toastMessage("Data inserted!!");
        } else {
            toastMessage("Not inserted :(");
        }
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}