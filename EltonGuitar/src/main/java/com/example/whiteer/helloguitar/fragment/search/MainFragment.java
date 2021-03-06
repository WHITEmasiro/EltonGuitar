package com.example.whiteer.helloguitar.fragment.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.whiteer.helloguitar.DBConnector;
import com.example.whiteer.helloguitar.PdfViewActivity;
import com.example.whiteer.helloguitar.PrefManager;
import com.example.whiteer.helloguitar.R;
import com.example.whiteer.helloguitar.Song;
import com.example.whiteer.helloguitar.fragment.basic.BaseFragment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

enum  DrawerListViewType{
//    NoDrawerListView,
    ClassListViw,
    DetailListView
}

enum DetailListViewType{
    CharacterNumber,
    FirstCharacter;
}

public class MainFragment extends BaseFragment {

//    protected String keyword;
//    protected String songClass;
//    protected String detail;
    protected String order;

    private List<String> classList;
    private List<List<String>> detailLists;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    protected SearchView searchView;
    private ListView lvClass;
    DrawerListViewType drawerListViewType;
    DetailListViewType detailListViewType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;

    }

    @Override
    public void initSetting(){

        urlString = "";
        order = "";

    }

    public void findViews(View view){

        super.findViews(view);

        drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        lvClass = (ListView)view.findViewById(R.id.lvClass);
        createDrawerList();
        drawerListViewType = DrawerListViewType.ClassListViw;
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//let the drawer unable to be pulled
        detailListViewType = DetailListViewType.CharacterNumber;
        lvClass.setAdapter(new DrawerListAdapter(getActivity(), classList));
        lvClass.setOnItemClickListener(new DrawItemClickListener());
//        nvDrawer = (NavigationView)view.findViewById(R.id.nvDrawer);
//        //"Disallow"
//        nvDrawer.requestDisallowInterceptTouchEvent(true);
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.app_name, R.string.app_name){

            @Override
            public void onDrawerSlide(View view,float slideOffset){

            }

            @Override
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                getActivity().invalidateOptionsMenu();
                drawerListViewType = DrawerListViewType.ClassListViw;
                lvClass.setAdapter(new DrawerListAdapter(getActivity(),classList));
            }

            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerStateChanged(int newState){

                ViewPager mainViewPager = (ViewPager)getActivity().findViewById(R.id.mainViewPager);

                if(newState != DrawerLayout.STATE_SETTLING){
                    mainViewPager.requestDisallowInterceptTouchEvent(true);
                }else{
                    mainViewPager.requestDisallowInterceptTouchEvent(false);
                }

            }
        };

        drawerToggle.syncState();

        drawerLayout.addDrawerListener(drawerToggle);

    }

    private void createDrawerList(){

        //make class list
        classList = new ArrayList<>();
        classList.add("華語歌曲");
        classList.add("西洋歌曲");
        classList.add("日韓歌曲");
        classList.add("其它歌曲");
        classList.add("全部");

        //make list of character number
        String chineseNumbers[] = {"一","二","三","四","五","六","七","八","九"};

        List<String> charNumList = new ArrayList<>();

        for(int n=0; n<=8; n++){
            charNumList.add(chineseNumbers[n]+"字部");
        }
        charNumList.add("多字部");
        charNumList.add("全部");

        //make list of first character
        List<String> firstCharList = new ArrayList<>();

        for(int n=0; n<26; n++){
            firstCharList.add(Character.toString((char)(n+65)));//uppercase
//            firstCharList.add(Character.toString((char)(n+97)));//lowercase
        }
        firstCharList.add("全部");

        detailLists = new ArrayList<>();
        detailLists.add(charNumList);
        detailLists.add(firstCharList);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // inflate options from url
        inflater.inflate(R.menu.menu_main, menu);

        //get searchview and set searchable configuration
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView)getActivity().findViewById(0);

        //assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);//do not iconify the widget;expand it by default

        searchView.setQuery(PrefManager.keyword, false);
        setSongList();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                PrefManager.keyword = newText;
                setSongList();


                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_home:
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
//        System.out.println("OAO");
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void setSongList(){
        //make string of parameters
        urlString = "http://petradise.website/EltonGuitar/Sheet_Search/search_sheet.php";
        paramsString = "Keyword=" + PrefManager.keyword + "&Class=" + PrefManager.songClass + "&Detail=" + PrefManager.detail + "&Order=" + order;
        super.setSongList();
    }

    @Override
    protected void setSongItem(final Song song, View view) {
        super.setSongItem(song, view);
        Button btnSaveSong = (Button)view.findViewById(R.id.btnSaveSong);
        btnSaveSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity().getApplicationContext();
                SharedPreferences sharedPreferences = context.getSharedPreferences(PrefManager.PREF_NAME_USER_DATA, Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString(PrefManager.USER_ID_KEY, "");

                urlString = "http://petradise.website/EltonGuitar/Sheet_Search/listed_sheet.php";
                paramsString = "User_ID=" + userId + "&Sheet_ID=" + Integer.toString(song.getId());
                new SaveSongTask().execute();
            }
        });
    }

    private class SaveSongTask extends AsyncTask<URL, Integer, Long>{


        @Override
        protected Long doInBackground(URL... params) {

            long totalSize = 0;
            try {

                //save song
                new DBConnector(getActivity().getApplicationContext()).execute(urlString, paramsString);

            }catch (Exception e){

            }

            return totalSize;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);


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
                view = layoutInflater.inflate(R.layout.class_item, viewGroup, false);
            }

            String text = list.get(position);
            TextView tvClass = (TextView)view.findViewById(R.id.tvClass);
            tvClass.setText(text);
            return view;

        }

    }

    private class DrawItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (drawerListViewType){

//                case NoDrawerListView:
//                    break;
                case ClassListViw:

                    drawerListViewType = DrawerListViewType.DetailListView;
                    PrefManager.detail = "";
                    if(position == 1){
                        detailListViewType = DetailListViewType.FirstCharacter;
                        lvClass.setAdapter(new DrawerListAdapter(getActivity(), detailLists.get(1)));
                        PrefManager.songClass = classList.get(position);
                    }else if(position == 4){
                        PrefManager.songClass = "";
                        drawerLayout.closeDrawers();
                    }else{
                        detailListViewType = DetailListViewType.CharacterNumber;
                        lvClass.setAdapter(new DrawerListAdapter(getActivity(), detailLists.get(0)));
                        PrefManager.songClass = classList.get(position);
                    }
                    setSongList();
                    break;

                case DetailListView:

                    switch (detailListViewType){
                        case CharacterNumber:

                            if(position>=0 && position<10){
                                PrefManager.detail = detailLists.get(0).get(position);
                            }else if(position == 10){
                                PrefManager.detail = "";
                            }

                            setSongList();
                            drawerLayout.closeDrawers();

                            break;

                        case FirstCharacter:

                            if(position>=0 && position<26){
                                PrefManager.detail = detailLists.get(1).get(position);
                            }else if(position == 26){
                                PrefManager.detail = "";
                            }

                            setSongList();
                            drawerLayout.closeDrawers();

                            break;

                        default:
                            break;
                    }
                    break;

                default:
                    break;
            }
        }
    }

}

