package com.csscorp.jayakumarmanian.cadence;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JayakumarManian on 25/07/15 AD.
 */
public class MainView extends Fragment implements View.OnClickListener,SensorEventListener {
    private TextView MagneticTestValue;
    private SensorManager sensorManager = null;
    private ImageButton btnStart;
    private ImageButton btnStop;
    private boolean started = false;
    public static float hTesla = 150;
    public static float lTestla = 70;
    public String sHR;
    public String sMM;
    public Cursor res;

    private boolean dRecord;
    DatabaseHandler db;
    SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
    Date DT = new Date();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.maiview, container, false);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        dRecord = Boolean.TRUE;
        btnStart = (ImageButton) v.findViewById(R.id.start_btn);
        btnStop = (ImageButton) v.findViewById(R.id.stop_btn);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnStart.setEnabled(true);
        btnStart.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.GONE);
        btnStop.setEnabled(false);
        db = new DatabaseHandler(getActivity());
        MagneticTestValue = (TextView) v.findViewById(R.id.Test);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);

        return  v;
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ignoring this for now

    }
      @Override
    public void onPause() {
        // Unregister the listene
        super.onPause();

        if (started == true) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onStop() {
        // Unregister the listener
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register magnetic sensor
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void showMessage1(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            if (started)
                if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                 int CV = (int) (Math.abs(sensorEvent.values[0]) + Math.abs(sensorEvent.values[1]) + Math.abs(sensorEvent.values[2]));
                    MagneticTestValue.setText(CV + "uT");
                    sdf.format(DT);
                    sdf = new SimpleDateFormat("HH");
                    sHR = sdf.format(DT);
                    sdf = new SimpleDateFormat("mm");
                    sMM = sdf.format(DT);
                    if (CV > hTesla && dRecord) {
                        db.Add_Sensor_Data(sHR, sMM, String.valueOf(CV));
                        dRecord = false;
                    } else if (CV < lTestla) {
                        dRecord = Boolean.TRUE;
                    }

                }
        }
    }


    public void SensorMath() {
        Integer id = db.getSessionID() + 1;
        Cursor res = db.GetByGourp(id);
        Integer count = db.getRowCount();
        while (res.moveToNext())
            db.Add_cadence_Data(res.getInt(0), res.getString(1), res.getString(2), res.getString(3));
        db.wipedata();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                db.getSessionID();
                btnStart.setVisibility(View.GONE);
                btnStop.setVisibility(View.VISIBLE);
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                started = true;
                Sensor accel = sensorManager
                        .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                sensorManager.registerListener(this, accel,
                        SensorManager.SENSOR_DELAY_FASTEST);

                break;
            case R.id.stop_btn:
                btnStart.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.GONE);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                started = false;
                sensorManager.unregisterListener(this);
                SensorMath();
                break;
            case R.id.Clear_btn:
                db.wipedata_cadence();
            default:
                break;
        }
    }
}
