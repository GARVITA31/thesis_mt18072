package com.example.admin.sensorapp;

public class SensorValues
{
        int id;
        int x_value;
        int y_value;
        int z_value;
        public SensorValues()
        {
            id = 0;
            x_value = 0;
            y_value = 0;
            z_value = 0;
        }
        public SensorValues(int x , int y , int z)
        {
            x_value = x;
            y_value = y;
            z_value = z;
        }

        public int getId()
        {
            return id;
        }
        public int getX_value()
        {
            return x_value;
        }
        public int getY_value()
        {
            return y_value;
        }
        public int getZ_value()
        {
            return z_value;
        }
        public void setId(int id)
        {
            this.id = id;
        }
        public void setX_value(int x_value)
        {
            this.x_value = x_value;
        }
        public void setY_value(int y_value)
        {
            this.y_value = y_value;
        }
        public void setZ_value(int y_value)
        {
            this.y_value = y_value;
        }
}


