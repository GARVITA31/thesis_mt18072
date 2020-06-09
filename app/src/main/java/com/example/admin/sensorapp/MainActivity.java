package com.example.admin.sensorapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    Button accelerometerButton;
    Button gyroscopeButton;
    Button orientationButton;
    Button magnetButton;
    Button proximityButton;
    Button gpsButton;
    Button shakeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accelerometerButton = (Button)findViewById(R.id.accelerometer);
        gyroscopeButton = (Button)findViewById(R.id.gyroscope);
        orientationButton = (Button)findViewById(R.id.orientation);
        magnetButton = (Button)findViewById(R.id.magnetometer);
        proximityButton = (Button)findViewById(R.id.proximity);
        gpsButton = (Button)findViewById(R.id.gps);
        shakeButton = (Button)findViewById(R.id.shake);

        accelerometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, Accelerometer.class);
                startActivity(myIntent);
            }
        });
        gyroscopeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, Gyroscope.class);
                startActivity(myIntent);
            }
        });
        orientationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, Orientation.class);
                startActivity(myIntent);
            }
        });
        magnetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, Magnetometer.class);
                startActivity(myIntent);
            }
        });
        proximityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, Proximity.class);
                startActivity(myIntent);
            }
        });
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, GPS.class);
                startActivity(myIntent);
            }
        });
        shakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, ShakeDetection.class);
                startActivity(myIntent);
            }
        });
    }
}
