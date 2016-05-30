package com.example.whiteer.helloguitar.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.whiteer.helloguitar.DBConnector;
import com.example.whiteer.helloguitar.PdfViewActivity;
import com.example.whiteer.helloguitar.PrefManager;
import com.example.whiteer.helloguitar.R;
import com.example.whiteer.helloguitar.Song;
import com.example.whiteer.helloguitar.fragment.basic.MyFragment;

import java.net.URL;

/**
 * Created by whiteer on 16/05/24.
 */
public class RequestFragment extends MyFragment {

    private final static String urlString = "http://petradise.website/EltonGuitar/Request/demand.php";
    private String paramsString;
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

                Context context = getActivity().getApplicationContext();
                SharedPreferences sharedPreferences = context.getSharedPreferences(PrefManager.PREF_NAME_USER_DATA, Context.MODE_PRIVATE);
                String userID = sharedPreferences.getString(PrefManager.USER_ID_KEY, "");

                String singer = etRequestSinger.getText().toString();
                String song = etRequestName.getText().toString();
                String songUrl = etRequestSongUrl.getText().toString();
                String lyricUrl = etRequestLyricUrl.getText().toString();

                if(userID.isEmpty() ||(singer.isEmpty() && song.isEmpty() && songUrl.isEmpty() && lyricUrl.isEmpty()))return;

                paramsString = "ID=" + userID +
                        "&Singer=" + singer +
                        "&Song=" + song +
                        "&SongUrl=" + songUrl +
                        "&LyricUrl=" + lyricUrl;

                new DownloadTask().execute();

                etRequestName.setText("");
                etRequestSinger.setText("");
                etRequestSongUrl.setText("");
                etRequestLyricUrl.setText("");

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

    private class DownloadTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls){

            long totalSize = 0;
            try{
                //get jsonString of data by url string
                new DBConnector(getActivity().getApplicationContext()).execute(urlString,paramsString);

            }catch (Exception e){

            }

            return totalSize;
        }

        @Override
        protected void onProgressUpdate(Integer... progress){
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(Long result){
            super.onPostExecute(result);



        }

    }

}
