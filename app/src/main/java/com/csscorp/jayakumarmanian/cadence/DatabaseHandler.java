
package com.csscorp.jayakumarmanian.cadence;


/**
 * Created by JayakumarManian on 22/07/15 AD.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SensorManager.db";

    // Table name
    private static final String TABLE_SENSOR = "sensor";
    private static final String TABLE_CADENCE = "cadence";

    // Sensor table Table Columns names
   // private static final String col_ID = "id";
    private static final String Hours = "Hours";
    private static final String Minutes = "Minutes";
    private static final String Tesla = "Tesla";
    private static final String CREATE_SENSOR_TABLE = "CREATE TABLE " + TABLE_SENSOR + "("
            + Hours + " TEXT,"
            + Minutes + " TEXT," + Tesla + " TEXT" + ")";


    // Cadence history table Table Columns names
    private static final String col_user= "userName";
    private static final String col_ID = "id";
    private static final String col_X = "x";
    private static final String col_Y = "y";
    private static final String col_Z = "z";

    private static final String CREATE_SENSOR_HISTORY_TABLE = "CREATE TABLE " + TABLE_CADENCE + "(" + col_ID + " INTEGER," + col_user + " TEXT," + col_X + " TEXT," + col_Y + " TEXT," + col_Z + " TEXT" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SENSOR_TABLE);
        db.execSQL(CREATE_SENSOR_HISTORY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CADENCE);
        // Create tables again
        onCreate(db);
    }


/**
     * All CRUD(Create, Read, Update, Delete) Operations
     * @param hours
     * @param minutes
     * @param tesla
     */


    // Adding new Sensor Data
    public void Add_Sensor_Data( String hours, String minutes, String tesla) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Hours, hours); // Sensor X Value
        values.put(Minutes, minutes); // Sensor Y Value
        values.put(Tesla, tesla); // Sensor Z Value
        // Inserting Row
        db.insert(TABLE_SENSOR, null, values);
        db.close(); // Closing database connection
    }


/**
     * All CRUD(Create, Read, Update, Delete) Operations
     * @param Value_X
     * @param Value_Y
     @param Value_Z
      * @param Session_ID
     */


    // Adding new Sensor Data
    public void Add_cadence_Data(Integer Session_ID, String Value_X, String Value_Y, String Value_Z) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_ID,Session_ID);
        values.put(col_user, MainActivity.LoginUser);
        values.put(col_X, Value_X); // Sensor X Value
        values.put(col_Y, Value_Y); // Sensor Y Value
        values.put(col_Z, Value_Z); // Sensor Z Value
        // Inserting Row
        db.insert(TABLE_CADENCE, null, values);
        db.close(); // Closing database connection
    }


    // Getting data from DB_Table
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_SENSOR;
        Cursor res=db.rawQuery(selectQuery,null);
        return res;
    }

    // Getting data from DB_Table
    public Cursor getAllData_cadence() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+ TABLE_CADENCE +" where userName='" + MainActivity.LoginUser + "'", null);
        return res;
    }

    public Cursor GetByGourp(int sessionID){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "select "+sessionID+", Hours, Minutes, Count(*) from "+TABLE_SENSOR+" group by "+sessionID+", Hours, Minutes";
        Cursor res=db.rawQuery(selectQuery,null);
        return res;
    }

    public int getSessionID(){
        SQLiteDatabase db = this.getReadableDatabase();
        final SQLiteStatement stmt = db
                .compileStatement("SELECT count(case " + col_user + " when " + MainActivity.LoginUser + " then 1 else null end) FROM " + TABLE_CADENCE);

        return (int) stmt.simpleQueryForLong();
    }


    //Delete all records from table
    public void wipedata(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_SENSOR);
    }

    //Delete all records from table
    public void wipedata_cadence(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_SENSOR);
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
