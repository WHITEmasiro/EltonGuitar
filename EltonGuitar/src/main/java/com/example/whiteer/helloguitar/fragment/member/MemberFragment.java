package com.example.whiteer.helloguitar.fragment.member;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.example.whiteer.helloguitar.MainActivity;
import com.example.whiteer.helloguitar.Page;
import com.example.whiteer.helloguitar.PageID;
import com.example.whiteer.helloguitar.PrefManager;
import com.example.whiteer.helloguitar.R;
import com.example.whiteer.helloguitar.fragment.LoginFragment;
import com.example.whiteer.helloguitar.fragment.RequestFragment;
import com.example.whiteer.helloguitar.fragment.basic.TabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whiteer on 16/05/25.
 */
public class MemberFragment extends TabFragment {



    public MemberFragment(List<Page> pageList){
        super();
        this.pageList = pageList;
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
        if(hasMenu)getActivity().setTitle(R.string.member_title);
    }
}
