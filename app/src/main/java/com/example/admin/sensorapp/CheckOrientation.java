package com.example.admin.sensorapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CheckOrientation extends AppCompatActivity
{
    Button check;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkorientation);
        check = (Button)findViewById(R.id.checkOrientation);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Display screenOrientation = getWindowManager().getDefaultDisplay();
                int orientation = Configuration.ORIENTATION_UNDEFINED;
                if(screenOrientation.getWidth() == screenOrientation.getHeight())
                {
                    orientation = Configuration.ORIENTATION_SQUARE;
                    Toast.makeText(getApplicationContext() , "Orientation : Square" , Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    if(screenOrientation.getWidth() < screenOrientation.getHeight())
                    {
                        orientation = Configuration.ORIENTATION_PORTRAIT;
                        Toast.makeText(getApplicationContext() , "Orientation : Portrait" , Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                        orientation = Configuration.ORIENTATION_LANDSCAPE;
                        Toast.makeText(getApplicationContext() , "Orientation : Landscape" , Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
    }
}
