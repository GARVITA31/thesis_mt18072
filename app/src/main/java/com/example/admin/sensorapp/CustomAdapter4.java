package com.example.admin.sensorapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter4 extends BaseAdapter
{
    private Context mContext;
    DataBaseHelper4 mydb;
    SQLiteDatabase db;
    private ArrayList<String> Timestamp = new ArrayList<String>();
    private ArrayList<Float> X = new ArrayList<Float>();
    private ArrayList<Float> Y = new ArrayList<Float>();
    private ArrayList<Float> Z = new ArrayList<Float>();
    private ArrayList<Double> DEG = new ArrayList<Double>();
    private ArrayList<Double> RAD = new ArrayList<Double>();


    public CustomAdapter4(Context  context, ArrayList<String> Timestamp, ArrayList<Float> X, ArrayList<Float> Y, ArrayList<Float> Z, ArrayList<Double> DEG, ArrayList<Double> RAD)
    {
        this.mContext = context;
        this.Timestamp = Timestamp;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.DEG = DEG;
        this.RAD = RAD;
    }
    @Override
    public int getCount()
    {
        return Timestamp.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder holder;
        mydb = new DataBaseHelper4(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null)
        {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_layout_magnetometer, null);
            holder = new viewHolder();
            holder.timestamp = (TextView) convertView.findViewById(R.id.timestamp);
            holder.x = (TextView) convertView.findViewById(R.id.x_value);
            holder.y = (TextView) convertView.findViewById(R.id.y_value);
            holder.z = (TextView) convertView.findViewById(R.id.z_value);
            holder.deg = (TextView) convertView.findViewById(R.id.degree_value);
            holder.rad = (TextView) convertView.findViewById(R.id.radian_value);

            convertView.setTag(holder);
        }
        else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.timestamp.setText(Timestamp.get(position));
        holder.x.setText(X.get(position).toString());
        holder.y.setText(Y.get(position).toString());
        holder.z.setText(Z.get(position).toString());
        holder.deg.setText(DEG.get(position).toString());
        holder.rad.setText(RAD.get(position).toString());

        return convertView;
    }
    public class viewHolder {
        TextView timestamp;
        TextView x;
        TextView y;
        TextView z;
        TextView deg;
        TextView rad;

    }
}

