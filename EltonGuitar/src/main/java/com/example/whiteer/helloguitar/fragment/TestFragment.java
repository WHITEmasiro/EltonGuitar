package com.example.whiteer.helloguitar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whiteer.helloguitar.R;
import com.example.whiteer.helloguitar.fragment.basic.MyFragment;

/**
 * Created by whiteer on 16/05/20.
 */
public class TestFragment extends MyFragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View view = inflater.inflate(R.layout.fragment_test, container, false);

            return view;

        }
}
