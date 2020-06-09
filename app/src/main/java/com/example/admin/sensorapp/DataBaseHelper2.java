package com.example.admin.sensorapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;

public class DataBaseHelper2 extends SQLiteOpenHelper
{
    private static String DB_PATH = "/data/data/com.example.sqltest/databases/";
    private static String DB_NAME = "test.sqlite";
    private SQLiteDatabase myDataBase;

    private static final String TAG = "DatabaseHelper2";
    private static final String TABLE_NAME = "Gyroscope";

    private static final String T1_COL1 = "TimeStamp";
    private static final String T1_COL2 = "xaxis";
    private static final String T1_COL3 = "yaxis";
    private static final String T1_COL4 = "zaxis";

    public DataBaseHelper2(Context context)
    {
        super(context, TABLE_NAME, null, 1);
//        SQLiteDatabase temp = this.getReadableDatabase();
    }

    public void createDataBase() throws IOException
    {
        boolean dbExist = checkDataBase();
        if (dbExist)
        {
            // do nothing - database already exist
        } else {
            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            this.close();
        }
    }
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;
        try
        {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e)
        {
            // database does't exist yet.
        }
        if (checkDB != null)
        {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    public void openDataBase() throws SQLException
    {
        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String createTable = "CREATE TABLE " + TABLE_NAME + "( created_at DATETIME DEFAULT CURRENT_TIMESTAMP," + T1_COL2 + " REAL," + T1_COL3 + " REAL," + T1_COL4 + " REAL)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    public boolean addDataGyr(float x, float y, float z)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1_COL2, x);
        Log.d(TAG, "addData: Adding " + x + " to " + TABLE_NAME);
        contentValues.put(T1_COL3, y);
        Log.d(TAG, "addData: Adding " + y + " to " + TABLE_NAME);
        contentValues.put(T1_COL4, z);
        Log.d(TAG, "addData: Adding " + z + " to " + TABLE_NAME);
        long result = db.insertOrThrow(TABLE_NAME,null, contentValues);
        //check whether data is inserted or not
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT TOP 10 * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}

