package com.example.whiteer.helloguitar;


import android.support.v4.app.Fragment;

/**
 * Created by whiteer on 16/05/19.
 */
public class Page {
    private PageID id;
    private Fragment fragment;

    public Page(PageID id, Fragment fragment) {
        this.id = id;
        this.fragment = fragment;
    }

    public PageID getId() {
        return id;
    }

    public void setId(PageID id) {
        this.id = id;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

}
