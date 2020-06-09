package com.example.admin.sensorapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Proximity extends MainActivity
{
    TextView x , y , z;
    Sensor mySensor;
    SensorManager sm;
    SensorEventListener eventListener;
    Button save , checkDistance;
    static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proximitysensor);
        x = (TextView)findViewById(R.id.textView1);
        y = (TextView)findViewById(R.id.textView2);
        z = (TextView)findViewById(R.id.textView3);
        save = (Button)findViewById(R.id.saveButton);
        checkDistance = (Button)findViewById(R.id.checkButton);

        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mySensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        eventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(final SensorEvent sensorEvent)
            {
                x.setText("X: " + sensorEvent.values[0]);
                y.setText("Y: " + sensorEvent.values[1]);
                z.setText("Z: " + sensorEvent.values[2]);
                checkDistance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(sensorEvent.values[0] == 0)
                        {
                            Toast.makeText(getApplicationContext(),"Near To Face", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(Proximity.this, CheckOrientation.class);
                            startActivity(myIntent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Far From Face", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                String datetime = dateformat.format(c.getTime());
                dbHelper.updatecolAcc(i++ , x.getText().toString() , y.getText().toString() , z.getText().toString() ,datetime);
                Toast.makeText(getApplicationContext() , "SAVED" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(eventListener, mySensor, sm.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(eventListener);
    }

}

