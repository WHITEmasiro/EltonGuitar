package com.example.whiteer.helloguitar.fragment.basic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.whiteer.helloguitar.DBConnector;
import com.example.whiteer.helloguitar.PdfViewActivity;
import com.example.whiteer.helloguitar.R;
import com.example.whiteer.helloguitar.Song;

import java.net.URL;
import java.util.List;

public class BaseFragment extends Fragment {

    private List<Song> songList;
    private ListView lvSongs;


    protected String urlString;
    protected String paramsString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initial some setting with string
        initSetting();

        //create views
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        findViews(view);
        //must have to add menu
//        setHasOptionsMenu(true);

        return view;

    }

    public void initSetting(){

        urlString = "";
        paramsString = "";

    }

    public void findViews(View view){

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        lvSongs = (ListView)view.findViewById(R.id.lvSongs);
        setSongList();

    }

    public void setSongList(){
        //make url string
        new DownloadTask().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        System.out.println("menu created!!");
        super.onCreateOptionsMenu(menu, inflater);
    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        System.out.println("menu prepare!!");
//    }

//    @Override
//    public void setMenuVisibility(boolean menuVisible) {
////        System.out.println("set menu visibility!!");
//        super.setMenuVisibility(menuVisible);
//        setHasOptionsMenu(true);
//
//    }
//
//    @Override
//    public void onPause() {
//        System.out.println("pause");
//        super.onStop();
//        setHasOptionsMenu(false);
//    }

    private class SongAdapter extends BaseAdapter{

        Context context;
        LayoutInflater layoutInflater;
        List<Song> songList;

        public SongAdapter(Context context, List<Song> songList){

            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.songList = songList;

        }

        @Override
        public int getCount(){
            return songList.size();
        }

        @Override
        public Object getItem(int position){
            return songList.get(position);
        }

        @Override
        public long getItemId(int position){
            return songList.get(position).getId();
        }

        @Override
        public View getView(int position,View view,ViewGroup viewGroup){

            Song song = songList.get(position);
            if(view == null){
                view = layoutInflater.inflate(R.layout.song_item, viewGroup, false);
            }
            TextView tvSong = (TextView)view.findViewById(R.id.tvSong);
            String text = song.getName() + " " + song.getSinger() + " " + song.getDate();
            tvSong.setText(text);
            return view;

        }

    }

    private class DownloadTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls){

            long totalSize = 0;
            try{
                //get jsonString of data by url string

                songList = new DBConnector(getActivity().getApplicationContext()).getSongList(urlString,paramsString);

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

            lvSongs.setAdapter((new SongAdapter(BaseFragment.this.getActivity(), songList)));
            lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // new a intent object and set the Activity to switch
                    Intent intent = new Intent();
                    intent.setClass(BaseFragment.this.getActivity(), PdfViewActivity.class);

                    //new a Bundle object and transport the data
                    Song song = songList.get(position);
                    //華語歌曲-三字部-黃路梓茵_Lulu-腿之歌.pdf
                    String pdfName = song.getsongClass() + "-" + song.getdetail() + "-" + song.getSinger() + "-" + song.getName() + ".pdf";
                    String pdfPath = "http://petradise.website/EltonGuitar/Sheet/" + pdfName;
                    Bundle bundle = new Bundle();
                    bundle.putString("pdfPath", pdfPath);

                    //assign the bundle to intent
                    intent.putExtras(bundle);

                    //switch activity
                    startActivity(intent);

                }
            });

        }

    }


}
