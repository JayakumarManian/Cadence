package com.csscorp.jayakumarmanian.cadence;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JayakumarManian on 25/07/15 AD.
 */
public class HistoryScreen extends Fragment implements View.OnClickListener {
    private SensorManager sensorManager = null;
    ListView mListView;
    private Button btnMTClear;
    private boolean started = false;
    public static float hTesla = 150;
    public static float lTestla = 70;
    public Cursor res;
    public static Integer SessionID = 0001;
    List<HashMap<String,String>> list=new ArrayList<>();

    private boolean dRecord;
    DatabaseHandler db;

    CadenceListAdapter listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.historyscreen, container, false);
        btnMTClear = (Button) v.findViewById(R.id.Clear_btn);
        btnMTClear.setOnClickListener(this);
        mListView=(ListView)v.findViewById(R.id.cadencelist);
        db = new DatabaseHandler(getActivity());
        Sensorhistory();
        return  v;

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ignoring this for now
    }

    @Override
    public void onPause() {
        // Unregister the listene
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register magnetic sensor
    }

    public void viewDB() {
        Cursor res = db.getAllData();

                        if (res.getCount() == 0) {
                            showMessage("Error", "Nothing Found");
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {

                            buffer.append("id => " + res.getString(0) + "\n");
                            buffer.append("Hour => " + res.getString(1) + "\n");
                            buffer.append("Minute => " + res.getString(2) + "\n");
                            buffer.append("Teslavalue => " + res.getString(3) + "\n");
                        }
                    }

    public void Sensorhistory() {
                        Cursor res1 = db.getAllData_cadence();
                        if (res1.getCount() == 0) {
                            showMessage1("UserData", "Nothing Found");
                        }
                        list.clear();
                        StringBuffer buffer1 = new StringBuffer();
                        while (res1.moveToNext()) {
                            HashMap<String,String>map=new HashMap<String, String>();
                            map.put("id",Integer.toString(res1.getInt(0)));
                            map.put("hour",res1.getString(2));
                            map.put("minute",res1.getString(3));
                            map.put("cadence",res1.getString(4));
                            list.add(map);
                            buffer1.append("Session => " + res1.getString(0) + "\n");
                            buffer1.append("Hour => " + res1.getString(1) + "\n");
                            buffer1.append("Minute =>" + res1.getString(2) + "\n");
                            buffer1.append("Cadence => " + res1.getString(3) + "\n");
                        }
                        listAdapter=new CadenceListAdapter(list,getActivity());
                        mListView.setAdapter(listAdapter);
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
    private String getHour(){
        Calendar today = Calendar.getInstance();
        int value = today.get(Calendar.AM_PM);
        while (true){
        int Hour = today.get(Calendar.HOUR);
        return String.valueOf(Hour);
    }}
    private String getMinute() {

        Calendar today = Calendar.getInstance();
        int value = today.get(Calendar.AM_PM);
        while (true) {
            int minute = today.get(Calendar.MINUTE);

        return String.valueOf(minute);
    }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this) {
            if (started)
                if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    int CV = (int) (Math.abs(sensorEvent.values[0]) + Math.abs(sensorEvent.values[1]) + Math.abs(sensorEvent.values[2]));
                    /*SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
                    Date DT = new Date();
                    sdf.format(DT);
                    sdf = new SimpleDateFormat("HH");
                    sHR = sdf.format(DT);
                    sdf = new SimpleDateFormat("mm");
                    sMM = sdf.format(DT);*/
                    if (CV > hTesla && dRecord) {
                        db.Add_Sensor_Data(getHour(), getMinute(), String.valueOf(CV));
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
       }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Clear_btn:
                db.wipedata_cadence();
            default:
                break;
        }
    }

    public class CadenceListAdapter extends BaseAdapter {

        LayoutInflater inf;
        int selectedposition=-1;
        Context mcon;
        List<HashMap<String,String>> mlist=new ArrayList<>();
        LinearLayout viewhide;
        public CadenceListAdapter(List<HashMap<String,String>> list,Context con){
            this.mcon=con;
            this.mlist=list;

            inf=(LayoutInflater)mcon.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            Log.i("testcount ", "===> " + mlist.size());
            return mlist.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view=inf.inflate(R.layout.listitems,null);
            viewhide=(LinearLayout)view.findViewById(R.id.expandview);

            TextView sessionView=(TextView)view.findViewById(R.id.session);
            TextView hourView=(TextView)view.findViewById(R.id.hour);

            TextView minuteView=(TextView)view.findViewById(R.id.min);

            TextView cadenceView=(TextView)view.findViewById(R.id.cadence);

            HashMap<String,String> map=new HashMap<>();
            map= mlist.get(position);
            sessionView.setText("Session  "+map.get("id"));

            hourView.setText("Hour  "+map.get("hour"));

            minuteView.setText("Minute  "+map.get("minute"));

            cadenceView.setText("Cadence  " + map.get("cadence"));

            if(selectedposition==position){
                viewhide.setVisibility(View.VISIBLE);
            }else
            {
                viewhide.setVisibility(View.GONE);

            }

            sessionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                selectedposition=position;
                    notifyDataSetChanged();
                   }
            });
            return view;
        }
    }
}
