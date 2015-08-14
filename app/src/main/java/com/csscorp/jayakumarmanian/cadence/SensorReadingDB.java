/*
package com.csscorp.jayakumarmanian.cadence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

*/
/**
 * Created by JayakumarManian on 24/07/15 AD.
 *//*

public class SensorReadingDB extends SQLiteOpenHelper{
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SensorManager1.db";

    // Sensor table name
    private static final String TABLE_SENSOR= "sensorhistory";


    // Sensor table Table Columns names
    private static final String col_user= "userName";
    private static final String col_ID = "id";
    private static final String col_X = "x";
    private static final String col_Y = "y";
    private static final String col_Z = "z";

    public SensorReadingDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SENSOR_TABLE = "CREATE TABLE " + TABLE_SENSOR + "(" + col_ID + " INTEGER," + col_user + " TEXT," + col_X + " TEXT," + col_Y + " TEXT," + col_Z + " TEXT" + ")";
        db.execSQL(CREATE_SENSOR_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR);

        // Create tables again
        onCreate(db);
    }

    */
/**
     * All CRUD(Create, Read, Update, Delete) Operations
     * @param Value_X
     * @param Value_Y
      @param Value_Z
     * @param Session_ID
     *//*


    // Adding new Sensor Data
    public void Add_Sensor_Data(Integer Session_ID, String Value_X, String Value_Y, String Value_Z) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_ID,Session_ID);
        values.put(col_user, MainActivity.LoginUser);
        values.put(col_X, Value_X); // Sensor X Value
        values.put(col_Y, Value_Y); // Sensor Y Value
        values.put(col_Z, Value_Z); // Sensor Z Value
        // Inserting Row
        db.insert(TABLE_SENSOR, null, values);
        db.close(); // Closing database connection
    }

    // Getting data from DB_Table
    public Cursor getAllData_cadence() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+ TABLE_SENSOR +" where userName='" + MainActivity.LoginUser + "'", null);
        return res;
    }

    //Delete all records from table
    public void wipedata_cadence(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_SENSOR);
    }

    public int getSessionID(){
        SQLiteDatabase db = this.getReadableDatabase();
        final SQLiteStatement stmt = db
                .compileStatement("SELECT count(case "+col_user+" when "+ MainActivity.LoginUser+" then 1 else null end) FROM "+ TABLE_SENSOR);

        return (int) stmt.simpleQueryForLong();
    }
    //Get table row count
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SENSOR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

}
*/
