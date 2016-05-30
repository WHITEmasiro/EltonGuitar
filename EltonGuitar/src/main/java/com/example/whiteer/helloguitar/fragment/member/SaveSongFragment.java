package com.example.whiteer.helloguitar.fragment.member;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.whiteer.helloguitar.MainActivity;
import com.example.whiteer.helloguitar.Page;
import com.example.whiteer.helloguitar.PageID;
import com.example.whiteer.helloguitar.PrefManager;
import com.example.whiteer.helloguitar.R;
import com.example.whiteer.helloguitar.fragment.LoginFragment;
import com.example.whiteer.helloguitar.fragment.basic.BaseFragment;

/**
 * Created by whiteer on 16/05/25.
 */
public class SaveSongFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            onCreate(savedInstanceState);
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public void initSetting() {
        super.initSetting();
        Context context = getActivity().getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefManager.PREF_NAME_USER_DATA, Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString(PrefManager.USER_ID_KEY,"");
        urlString = "http://petradise.website/EltonGuitar/Sheet_Search/get_list.php";
        paramsString = "ID=" + userID;
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

                //change to login page
                mainActivity.setPage(1, new Page(PageID.LoginPageID, new LoginFragment(), PrefManager.LoginPageTitle));
                mainActivity.setPage(2, new Page(PageID.LoginPageID, new LoginFragment(), PrefManager.LoginPageTitle));
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
