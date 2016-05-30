package com.example.whiteer.helloguitar.fragment.search;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import com.example.whiteer.helloguitar.Page;
import com.example.whiteer.helloguitar.PrefManager;
import com.example.whiteer.helloguitar.fragment.basic.BaseFragment;
import com.example.whiteer.helloguitar.fragment.basic.TabFragment;

import java.util.List;

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
            ((BaseFragment)tabPageAdapter.getItem(0)).forceHasOptionsMenu(true);
            isCreated = true;
        }

        return view;

    }

    @Override
    public void updatePage(int index) {
        super.updatePage(index);
//        MainFragment mainFragment = (MainFragment) tabPageAdapter.getItem(index);
    }
}
