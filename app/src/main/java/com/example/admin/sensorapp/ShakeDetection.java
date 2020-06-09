package com.example.admin.sensorapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class ShakeDetection extends AppCompatActivity implements SensorEventListener
{
    float xAcc , yAcc ,zAcc;
    float xPrevAcc , yPrevAcc ,zPrevAcc;
    boolean firstUpdate = true;
    boolean shakeInitiated = false;
    float shakeThreshold = 12.5f;

    Sensor mySensor;
    SensorManager sm;

    public ShakeDetection() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mySensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this , mySensor , SensorManager.SENSOR_DELAY_NORMAL);

    }

    private boolean isAccChanged()
    {
        float deltaX = Math.abs(xPrevAcc - xAcc);
        float deltaY = Math.abs(yPrevAcc - yAcc);
        float deltaZ = Math.abs(zPrevAcc - zAcc);
        return (deltaX > shakeThreshold && deltaY > shakeThreshold) || (deltaX > shakeThreshold && deltaZ > shakeThreshold) || (deltaY > shakeThreshold && deltaZ > shakeThreshold);
    }

    private void executeShakeAction()
    {
        Intent i = new Intent(ShakeDetection.this , SecondActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void updateAccParameters(float xNewAcc, float yNewAcc, float zNewAcc)
    {
        if(firstUpdate)
        {
            xPrevAcc = xNewAcc;
            yPrevAcc = yNewAcc;
            zPrevAcc = zNewAcc;
            firstUpdate = false;
        }
        else
        {
            xPrevAcc = xAcc;
            yPrevAcc = yAcc;
            zPrevAcc = zAcc;
        }
        xAcc = xNewAcc;
        yAcc = yNewAcc;
        zAcc = zNewAcc;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        updateAccParameters(sensorEvent.values[0] , sensorEvent.values[1] , sensorEvent.values[2]);
        if (!shakeInitiated && isAccChanged())
        {
            shakeInitiated = true;
        }
        else if(shakeInitiated && isAccChanged())
        {
            executeShakeAction();
        }
        else if(shakeInitiated && !isAccChanged())
        {
            shakeInitiated = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
