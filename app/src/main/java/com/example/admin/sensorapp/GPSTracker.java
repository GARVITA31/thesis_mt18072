package com.example.admin.sensorapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class GPSTracker implements LocationListener
{
    Context con;
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s)
    {

    }

    @Override
    public void onProviderDisabled(String s)
    {

    }
    public  GPSTracker(Context context)
    {
        con = context;
    }

    public Location getLocation()
    {
        if(ContextCompat.checkSelfPermission(con, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(con,"Persmission not Granted",Toast.LENGTH_SHORT).show();
        }

        LocationManager lm = (LocationManager) con.getSystemService(con.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled)
        {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return location;
        }
        else
        {
            Toast.makeText(con,"Please enable GPS" , Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
