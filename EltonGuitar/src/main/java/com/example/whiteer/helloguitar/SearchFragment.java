package com.example.whiteer.helloguitar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by whiteer on 16/05/25.
 */
public class SearchFragment extends TabFragment {

    boolean isCreated = false;

    public SearchFragment(List<Page> pageList){
        super();
        this.pageList = pageList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(!isCreated){
         setHasOptionsMenu(true);
            isCreated = true;
        }

        return view;

    }
}
