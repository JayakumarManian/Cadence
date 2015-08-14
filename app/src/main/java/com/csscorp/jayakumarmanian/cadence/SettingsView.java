package com.csscorp.jayakumarmanian.cadence;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by JayakumarManian on 25/07/15 AD.
 */
public class SettingsView extends Fragment implements SeekBar.OnSeekBarChangeListener {
    SeekBar lTesla,hTesla;
    int lTeslaValue,hTeslaValue;
    TextView lTeslaResult,hTeslaResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settingsview, container, false);
        lTesla = (SeekBar)v.findViewById(R.id.lTesla);
        lTeslaResult = (TextView)v.findViewById(R.id.lTeslaResult);

        hTesla=(SeekBar)v.findViewById(R.id.hTesla);
        hTeslaResult=(TextView)v.findViewById(R.id.hTeslaResult);

        //set change listener
        lTesla.setOnSeekBarChangeListener(this);
        hTesla.setOnSeekBarChangeListener(this);
        return v;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.lTesla:
                lTeslaValue = progress;
                lTeslaResult.setText ("Min Magnetic Strength:  "+lTeslaValue);
                break;
            case R.id.hTesla:
                hTeslaValue= progress;
                hTeslaResult.setText("Max Magnetic Strength:  "+hTeslaValue);
                break;
        }
       }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }
}