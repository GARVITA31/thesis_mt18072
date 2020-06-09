package com.example.admin.sensorapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter1 extends BaseAdapter
{
    private Context mContext;
    DataBaseHelper1 mydb;
    SQLiteDatabase db;
    private ArrayList<String> Timestamp = new ArrayList<String>();
    private ArrayList<Float> X = new ArrayList<Float>();
    private ArrayList<Float> Y = new ArrayList<Float>();
    private ArrayList<Float> Z = new ArrayList<Float>();
    private ArrayList<Float> NEW_X = new ArrayList<Float>();
    private ArrayList<Float> NEW_Y = new ArrayList<Float>();
    private ArrayList<Float> NEW_Z = new ArrayList<Float>();

    public CustomAdapter1(Context  context, ArrayList<String> Timestamp, ArrayList<Float> X, ArrayList<Float> Y, ArrayList<Float> Z, ArrayList<Float> NEW_X, ArrayList<Float> NEW_Y, ArrayList<Float> NEW_Z)
    {
        this.mContext = context;
        this.Timestamp = Timestamp;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.NEW_X = NEW_X;
        this.NEW_Y = NEW_Y;
        this.NEW_Z = NEW_Z;
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
        mydb = new DataBaseHelper1(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null)
        {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_layout_accelerometer, null);
            holder = new viewHolder();
            holder.timestamp = (TextView) convertView.findViewById(R.id.timestamp);
            holder.x = (TextView) convertView.findViewById(R.id.x_value);
            holder.y = (TextView) convertView.findViewById(R.id.y_value);
            holder.z = (TextView) convertView.findViewById(R.id.z_value);
            holder.new_x = (TextView) convertView.findViewById(R.id.new_x_value);
            holder.new_y = (TextView) convertView.findViewById(R.id.new_y_value);
            holder.new_z = (TextView) convertView.findViewById(R.id.new_z_value);
            convertView.setTag(holder);
        }
        else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.timestamp.setText(Timestamp.get(position));
        holder.x.setText(X.get(position).toString());
        holder.y.setText(Y.get(position).toString());
        holder.z.setText(Z.get(position).toString());
        holder.new_x.setText(NEW_X.get(position).toString());
        holder.new_y.setText(NEW_Y.get(position).toString());
        holder.new_z.setText(NEW_Z.get(position).toString());

        return convertView;
    }
    public class viewHolder
    {
        TextView timestamp;
        TextView x;
        TextView y;
        TextView z;
        TextView new_x;
        TextView new_y;
        TextView new_z;
    }
}