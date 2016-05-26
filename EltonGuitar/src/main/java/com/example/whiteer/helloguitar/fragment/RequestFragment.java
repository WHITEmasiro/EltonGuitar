package com.example.whiteer.helloguitar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.whiteer.helloguitar.R;

/**
 * Created by whiteer on 16/05/24.
 */
public class RequestFragment extends Fragment {

    private EditText etRequestName;
    private EditText etRequestSinger;
    private EditText etRequestSongUrl;
    private EditText etRequestLyricUrl;
    private Button btnRequestSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        findView(view);

        return view;
    }

    private void findView(View view){

        etRequestName = (EditText)view.findViewById(R.id.etRequestName);
        etRequestSinger = (EditText)view.findViewById(R.id.etRequestSinger);
        etRequestSongUrl = (EditText)view.findViewById(R.id.etRequestSongUrl);
        etRequestLyricUrl = (EditText)view.findViewById(R.id.etRequestLyricUrl);

        btnRequestSubmit = (Button)view.findViewById(R.id.btnRequestSubmit);
        btnRequestSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_request,menu);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
        if(hasMenu) getActivity().setTitle(R.string.request_title);
    }
}
