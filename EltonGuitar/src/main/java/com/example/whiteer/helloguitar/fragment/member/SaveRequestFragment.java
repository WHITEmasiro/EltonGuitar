package com.example.whiteer.helloguitar.fragment.member;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.whiteer.helloguitar.DBConnector;
import com.example.whiteer.helloguitar.MainActivity;
import com.example.whiteer.helloguitar.Page;
import com.example.whiteer.helloguitar.PageID;
import com.example.whiteer.helloguitar.PrefManager;
import com.example.whiteer.helloguitar.R;
import com.example.whiteer.helloguitar.Request;
import com.example.whiteer.helloguitar.fragment.LoginFragment;
import com.example.whiteer.helloguitar.fragment.basic.MyFragment;

import java.net.URL;
import java.util.List;

public class SaveRequestFragment extends MyFragment {

    private List<Request> requestList;
    private ListView lvRequests;


    protected static final String urlString = "http://petradise.website/EltonGuitar/Request/get_demand.php";
    protected String paramsString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initial some setting with string
        initSetting();

        //create views
        View view = inflater.inflate(R.layout.fragment_save_request, container, false);
        findViews(view);
        //must have to add menu
//        setHasOptionsMenu(true);

        return view;

    }

    public void initSetting(){

        paramsString = "";

    }

    public void findViews(View view){

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        lvRequests = (ListView)view.findViewById(R.id.lvRequests);
        setRequestList();

    }

    public void setRequestList(){

        Context context = getActivity().getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefManager.PREF_NAME_USER_DATA, Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString(PrefManager.USER_ID_KEY,"");
        paramsString = "ID=" + userID;
        new DownloadTask().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_member, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_logout:

                //logout

                MainActivity mainActivity = (MainActivity) getActivity();
                Context context = mainActivity.getApplicationContext();
                SharedPreferences sharedPreferences = context.getSharedPreferences(PrefManager.PREF_NAME_USER_DATA,Context.MODE_PRIVATE);
                sharedPreferences.edit()
                        .putString(PrefManager.USER_ID_KEY,"")
                        .apply();

                //change to login page
                mainActivity.setPage(1, new Page(PageID.LoginPageID, new LoginFragment(), PrefManager.LoginPageTitle));
                mainActivity.setPage(2, new Page(PageID.LoginPageID, new LoginFragment(), PrefManager.LoginPageTitle));
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        if(isResumed())super.setHasOptionsMenu(hasMenu);
    }

    public void forceHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void update() {
        super.update();
        setRequestList();
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

    private class RequestAdapter extends BaseAdapter{

        Context context;
        LayoutInflater layoutInflater;
        List<Request> requestList;

        public RequestAdapter(Context context, List<Request> requestList){

            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.requestList = requestList;

        }

        @Override
        public int getCount(){
            return requestList.size();
        }

        @Override
        public Object getItem(int position){
            return requestList.get(position);
        }

        @Override
        public long getItemId(int position){
            return requestList.get(position).getId();
        }

        @Override
        public View getView(int position,View view,ViewGroup viewGroup){

            Request request = requestList.get(position);
            if(view == null){
                view = layoutInflater.inflate(R.layout.request_item, viewGroup, false);
            }
            TextView tvRequest = (TextView)view.findViewById(R.id.tvRequest);
            String text = request.getSheetName() + " " + request.getSingerName() + " " + request.getRequestDate();
            tvRequest.setText(text);
            return view;

        }

    }



    private class DownloadTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls){

            long totalSize = 0;
            try{
                //get jsonString of data by url string

                requestList = new DBConnector(getActivity().getApplicationContext()).getRequestList(urlString, paramsString);

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

            lvRequests.setAdapter((new RequestAdapter(SaveRequestFragment.this.getActivity(), requestList)));
            lvRequests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //open request

                }
            });

        }

    }


}
