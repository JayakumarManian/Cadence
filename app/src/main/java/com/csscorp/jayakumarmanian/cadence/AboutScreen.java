package com.csscorp.jayakumarmanian.cadence;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JayakumarManian on 25/07/15 AD.
 */
public class AboutScreen extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v=inflater.inflate(R.layout.about,container,false);
        return  v;
    }

}
