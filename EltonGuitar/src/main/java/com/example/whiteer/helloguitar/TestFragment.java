package com.example.whiteer.helloguitar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by whiteer on 16/05/20.
 */
public class TestFragment extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View view = inflater.inflate(R.layout.fragment_test, container, false);

            return view;

        }
}
