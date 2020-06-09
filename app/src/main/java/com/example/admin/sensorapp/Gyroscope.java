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

//import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Gyroscope extends MainActivity implements SensorEventListener
{

    private static final String TAG = "GYROSCOPE_ACTIVITY";
    TextView x, y, z;
    Sensor mySensor;
    SensorManager sm;
    SensorEventListener eventListener;
    Button save, show, clear, calculate;
    private Button export;
    DataBaseHelper2 databaseHelper;
    private SensorManager sensorManager;
    Sensor gyroscope;
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
        calculate = (Button) findViewById(R.id.calculateButton);

        databaseHelper = new DataBaseHelper2(this);

        Log.d(TAG, "onCreate: Initialized sensor");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(Gyroscope.this, gyroscope, 100);
        Log.d(TAG, "onCreate: sensor done");

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gyroscope.this, ShowDataListView2.class);
                startActivity(intent);
            }
        });

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

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateAngles();

            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {
    }

    public void calculateAngles()
    {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x.setText("X: " + sensorEvent.values[0]);
        y.setText("Y: " + sensorEvent.values[1]);
        z.setText("Z: " + sensorEvent.values[2]);
        x_value = sensorEvent.values[0];
        y_value = sensorEvent.values[1];
        z_value = sensorEvent.values[2];
//        AddData(x_value, y_value, z_value);
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void AddData(float x, float y, float z)
    {
        boolean insertData = databaseHelper.addDataGyr(x, y, z);
        if (insertData) {
//            toastMessage("Data inserted!!");
        } else {
            toastMessage("Not inserted :(");
        }
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}