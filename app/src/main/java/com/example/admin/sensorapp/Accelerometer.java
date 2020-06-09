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
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Accelerometer extends MainActivity implements SensorEventListener
{
    private static final String TAG = "ACCELEROMETER_ACTIVITY";
    TextView x, y, z;
    Sensor mySensor;
    SensorManager sm;
    SensorEventListener eventListener;
    Button save, show, clear, calculate;
    private Button export;
    DataBaseHelper1 databaseHelper;
    private SensorManager sensorManager;
    Sensor accelerometer, magnetometer;
    Float x_value_acc, y_value_acc, z_value_acc;
    Float x_value_mag, y_value_mag, z_value_mag;
    Float azimuth, pitch, roll;
    float oldAzimuth;
    float[] ans = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor);
        x = (TextView) findViewById(R.id.textView1);
        y = (TextView) findViewById(R.id.textView2);
        z = (TextView) findViewById(R.id.textView3);
        save = (Button) findViewById(R.id.saveButton);
        show = (Button) findViewById(R.id.showButton);
        clear = (Button) findViewById(R.id.clearButton);
        calculate = (Button) findViewById(R.id.calculateButton);

        databaseHelper = new DataBaseHelper1(this);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Accelerometer.this, ShowDataListView1.class);
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
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(Accelerometer.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(Accelerometer.this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

        Log.d(TAG, "onCreate: sensor done");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AddData(x_value_acc, y_value_acc, z_value_acc, ans[0], ans[1], ans[2]);
                calculateAngles();
                AddData(x_value_acc, y_value_acc, z_value_acc, ans[0], ans[1], ans[2]);

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
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    float[] mAcc;
    float[] mGeomagnetic;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            x.setText("X: " + sensorEvent.values[0]);
            y.setText("Y: " + sensorEvent.values[1]);
            z.setText("Z: " + sensorEvent.values[2]);
            x_value_acc = sensorEvent.values[0];
            y_value_acc = sensorEvent.values[1];
            z_value_acc = sensorEvent.values[2];

        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            x_value_mag = sensorEvent.values[0];
            y_value_mag = sensorEvent.values[1];
            z_value_mag = sensorEvent.values[2];
        }

        if (mAcc != null && mGeomagnetic != null)
        {
            float R[] = new float[9];
            float I[] = new float[9];

            if (SensorManager.getRotationMatrix(R, I, mAcc, mGeomagnetic))
            {
                // orientation contains azimuth, pitch and roll
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                oldAzimuth = azimuth;
                azimuth = orientation[0];
                pitch = orientation[1];
                roll = orientation[2];
                // at this point, orientation contains the azimuth(direction), pitch and roll values.

                Log.d("onSensorChanged:", "azimuth = "+ azimuth);
                Log.d("onSensorChanged:", "oldAzimuth = "+ oldAzimuth);
            }
        }
//        AddData(x_value, y_value, z_value);
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public double getDeclination()
    {
        Magnetometer m = new Magnetometer();
        double dec = m.calculateDeclination();
        return dec;
    }

    public void calculateAngles()
    {
        Double beta = -(Math.toDegrees(Math.atan(y_value_acc/z_value_acc)));
        Double gamma = -(Math.toDegrees(Math.atan(x_value_acc/z_value_acc)));

//        toastMessage("Beta = " + beta + " and Gamma = " + gamma);

//        if(beta != 0 && gamma != 0)
        {
//            Magnetometer mm = new Magnetometer();
//            ArrayList<Float> temp = mm.getMagnetometerValues();
//            toastMessage("Bx = " + temp.get(0) + " By = " + temp.get(1) + "Bz = " + temp.get(2));

            double[][] a = new double[3][3];
            a[0][0] = Math.cos(gamma);
            a[0][1] = 0;
            a[0][2] = -Math.sin(gamma);
            a[1][0] = 0;
            a[1][1] = 1;
            a[1][2] = 0;
            a[2][0] = Math.sin(gamma);
            a[2][1] = 0;
            a[2][2] = Math.cos(gamma);

            double[][] b = new double[3][3];
            b[0][0] = 1;
            b[0][1] = 0;
            b[0][2] = 0;
            b[1][0] = 0;
            b[1][1] = Math.cos(beta);
            b[1][2] = Math.sin(beta);
            b[2][0] = 0;
            b[2][1] = -Math.sin(beta);
            b[2][2] = Math.cos(beta);

            double c[] = new double[3];
//            c[0] = temp.get(0);
//            c[1] = temp.get(1);
//            c[2] = temp.get(2);
            c[0] = x_value_mag;
            c[1] = y_value_mag;
            c[2] = z_value_mag;

            double res[] = new double[3];

            for (int i = 0; i < 3; i++)
            {
                res[i] = 0;
                for (int j = 0; j < 3; j++)
                {
                    res[i] += b[i][j] * c[j];
                }
            }

            double res_final[] = new double[3]; //changed magnetic field vector

            for (int i = 0; i < 3; i++)
            {
                res_final[i] = 0;
                for (int j = 0; j < 3; j++)
                {
                    res_final[i] += a[i][j] * res[j];
                }
            }
//            toastMessage("Bx' = " + res_final[0] + " By' = " + res_final[1] + "Bz' = " + res_final[2]);

            //calculate cosine similarity between original and changed vectors
            double num = 0;
            for (int i = 0; i < 3; i++)
            {
                num += c[i]*res_final[i];
            }
            double den1 = 0;
            for (int i = 0; i < 3; i++)
            {
                den1 += c[i]*c[i];
            }
            double den2 = 0;
            for (int i = 0; i < 3; i++)
            {
                den2 += res_final[i] * res_final[i];
            }
            double den = Math.sqrt(den1)*Math.sqrt(den2);

            Intent intent = new Intent(Accelerometer.this, Magnetometer.class);
            startActivity(intent);
            Magnetometer m = new Magnetometer();
            double declination = m.calculateDeclination();
            double alpha = Math.toDegrees(Math.acos(num/den)) - declination;
//            toastMessage("Alpha = " + alpha);

            double[][] matrix1 = new double[3][3]; //Rotation along z axis
            double[][] matrix2 = new double[3][3]; //Rotation along y axis
            double[][] matrix3 = new double[3][3]; //Rotation along x axis

            matrix1[0][0] = Math.cos(alpha);
            matrix1[0][1] = Math.sin(alpha);
            matrix1[0][2] = 0;
            matrix1[1][0] = -(Math.sin(alpha));
            matrix1[1][1] = Math.cos(alpha);
            matrix1[1][2] = 0;
            matrix1[2][0] = 0;
            matrix1[2][1] = 0;
            matrix1[2][1] = 1;

            matrix2[0][0] = Math.cos(gamma);
            matrix2[0][1] = 0;
            matrix2[0][2] = Math.sin(gamma);
            matrix2[1][0] = 0;
            matrix2[1][1] = 1;
            matrix2[1][2] = 0;
            matrix2[2][0] = -(Math.sin(gamma));
            matrix2[2][1] = 0;
            matrix2[2][2] = Math.cos(gamma);

            matrix3[0][0] = 1;
            matrix3[0][1] = 0;
            matrix3[0][2] = 0;
            matrix3[1][0] = 0;
            matrix3[1][1] = Math.cos(beta);
            matrix3[1][2] = -(Math.sin(beta));
            matrix3[2][0] = 0;
            matrix3[2][1] = Math.sin(beta);
            matrix3[2][2] = Math.cos(beta);

            double result1[][] = new double[3][3];

            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    result1[i][j] = 0;
                    for(int k = 0; k < 3; k++)
                    {
                        result1[i][j] += matrix1[i][k] * matrix2[k][j];
                    }
                }
            }

            double result_final[][] = new double[3][3];

            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    result_final[i][j] = 0;
                    for(int k = 0; k < 3; k++)
                    {
                        result_final[i][j] += result1[i][k] * matrix3[k][j];
                    }
                }
            }
//            toastMessage("a00 = " + result_final[0][0] + " a01 = " + result_final[0][1] + " a02 = " + result_final[0][2]
//                    + " a10 = " + result_final[1][0] + " a11 = " + result_final[1][1] + " a12 = " + result_final[1][2]
//                    + " a20 = " + result_final[2][0] + " a21 = " + result_final[2][1] + " a22 = " + result_final[2][2]);

            for(int i=0; i<3; i++)
            {
                ans[i] = 0;
                for(int j=0; j<3; j++)
                {
                    ans[i] += result_final[i][j]*c[j];
                }
            }
//            toastMessage("X: " + ans[0] + " Y: " + ans[1] + " Z: " + ans[2]);
        }
    }

//    public void AddData(float x, float y, float z)
public void AddData(float x, float y, float z, float a1, float a2, float a3)

{
//        boolean insertData = databaseHelper.addDataAcc(x, y, z, a1, a2, a3);
//        boolean insertData = databaseHelper.addDataAcc(x, y, z, ans[0], ans[1], ans[2]);
        boolean insertData = databaseHelper.addDataAcc(x, y, z, a1, a2, a3);


        if (insertData)
        {
//            toastMessage("Data inserted!!");
        } else {
            toastMessage("Not inserted :(");
        }
    }
    public void getValues()
    {

    }
    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}