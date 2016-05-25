package com.example.whiteer.helloguitar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class RateFragment extends MainFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;

    }

    @Override
    public void initSetting(){

        super.initSetting();
        order = "ByRate";//another is "ByNew"

    }

}

