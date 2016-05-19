package com.example.whiteer.helloguitar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private List<Song> songList;
    private Toolbar tbSearch;
    private ListView lvSongs;

    private String keyword;
    private String songClass;
    private String detail;
    private String order;

    private List<String> classList;
    private List<String> detailList;
    private DrawerLayout drawerLayout;
    private ListView lvClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        keyword = "";
        songClass = "";
        detail = "";
        order = "";
        findViews(view);
        //must have to add menu
        setHasOptionsMenu(true);

        return view;

    }

    public void findViews(View view){

        tbSearch = (Toolbar)view.findViewById(R.id.tbSearch);
        ((AppCompatActivity)getActivity()).setSupportActionBar(tbSearch);

        lvSongs = (ListView)view.findViewById(R.id.lvSongs);
        setSongList();

        drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        lvClass = (ListView)view.findViewById(R.id.lvClass);
        createDrawerList();


    }

    private void createDrawerList(){

        classList = new ArrayList<>();
        classList.add("華語歌曲");
        classList.add("西洋歌曲");
        classList.add("日韓歌曲");
        classList.add("其它歌曲");
        classList.add("全部歌曲");

        List<String> charNumList = new ArrayList<>();

        for(int n=1;n<=10;n++){

        }



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // inflate options from url
        getActivity().getMenuInflater().inflate(R.menu.menu_main,menu);

        //get searchview and set searchable configuration
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        //assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);//do not iconify the widget;expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                keyword = newText;
                setSongList();
                return false;
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        return true;
//    }

    public void setSongList(){
        new DownloadTask().execute();
    }

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

                //make url string
                String urlString = "http://petradise.website/EltonGuitar/Sheet_Search/search_sheet.php";
                String paramsString = "Keyword=" + keyword + "&Class=" + songClass + "&Detail=" + detail + "&Order=" + order;
                //get jsonString of data by url string

                songList = new DBConnector().execute(urlString,paramsString);

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

            lvSongs.setAdapter((new SongAdapter(MainFragment.this.getActivity(), songList)));
            lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // new a intent object and set the Activity to switch
                    Intent intent = new Intent();
                    intent.setClass(MainFragment.this.getActivity(), PdfViewActivity.class);

                    //new a Bundle object and transport the data
                    Song song = songList.get(position);
                    //華語歌曲-三字部-黃路梓茵_Lulu-腿之歌.pdf
                    String pdfName = song.songClass + "-" + song.detail + "-" + song.singer + "-" + song.name + ".pdf";
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

    private class DrawerListAdapter extends BaseAdapter{

        Context context;
        LayoutInflater layoutInflater;
        List<String> list;

        public DrawerListAdapter(Context context, List<String> list){

            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.list = list;

        }

        @Override
        public int getCount(){
            return list.size();
        }

        @Override
        public Object getItem(int position){
            return list.get(position);
        }

        @Override
        public long getItemId(int position){
            return 0;
        }

        @Override
        public View getView(int position,View view,ViewGroup viewGroup){

            if(view == null){
                view = layoutInflater.inflate(R.layout.song_item, viewGroup, false);
            }

            String text = list.get(position);
            TextView tvClass = (TextView)view.findViewById(R.id.tvClass);
            tvClass.setText(text);
            return view;

        }

    }

}
