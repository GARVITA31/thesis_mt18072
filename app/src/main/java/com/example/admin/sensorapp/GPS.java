package com.example.admin.sensorapp;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GPS extends MainActivity
{
    Button gpsButton;
    TextView t1 ,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        gpsButton = (Button)findViewById(R.id.button);
        t1 = (TextView)findViewById(R.id.gps1);
        t2 = (TextView)findViewById(R.id.gps2);

        ActivityCompat.requestPermissions(GPS.this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                Location location = gpsTracker.getLocation();
                if(location != null)
                {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    //t1.setText((int)location.getLatitude());
                    //t2.setText((int)location.getLongitude());
                    Toast.makeText(getApplicationContext(),"Latitude:" + latitude + "\n Longitude: " + longitude ,Toast.LENGTH_SHORT).show();
                }
        }
        });
    }

}

