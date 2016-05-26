package com.example.whiteer.helloguitar.fragment.basic;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whiteer.helloguitar.NoSwipeViewPager;
import com.example.whiteer.helloguitar.Page;
import com.example.whiteer.helloguitar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whiteer on 16/05/21.
 */
public class TabFragment extends Fragment {

    protected List<Page> pageList = new ArrayList<>();
    TabPageAdapter tabPageAdapter;
    NoSwipeViewPager viewPager;
    int currentPageIndex = 0;

//    public static final TabFragment newInstance(List<Page> pageList){
//
//        TabFragment tabFragment = new TabFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("pageList", (Serializable)pageList);
//        tabFragment.setArguments(bundle);
//        return tabFragment;
//
//    }

    public TabFragment(){

    }

    public TabFragment(List<Page> pageList){
        super();
        this.pageList = pageList;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        pageList = (List<Page>)getArguments().getSerializable("pageList");
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        viewPager = (NoSwipeViewPager) view.findViewById(R.id.vpTab);
        tabPageAdapter = new TabPageAdapter(getChildFragmentManager());
//        viewPager.setAdapter(new TabPageAdapter(getActivity().getSupportFragmentManager()));//getChildFragmentManager is for nested fragment
        viewPager.setAdapter(tabPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                tabPageAdapter.getItem(currentPageIndex).setHasOptionsMenu(false);//at this time,currentPageIndex is index of the last page
                tabPageAdapter.getItem(position).setHasOptionsMenu(true);
                currentPageIndex = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return view;

    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {

        if(tabPageAdapter == null)return;
        tabPageAdapter.getItem(currentPageIndex).setHasOptionsMenu(hasMenu);

    }




    //    @Override
//    public void setMenuVisibility(boolean menuVisible) {
//        super.setMenuVisibility(menuVisible);
//
//        if(tabPageAdapter == null)return;
//        tabPageAdapter.getItem(currentPageIndex).setHasOptionsMenu(true);
//
//    }
//
//    @Override
//    public void onPause() {
//        System.out.println("pause");
//        super.onStop();
//
//        tabPageAdapter.getItem(currentPageIndex).setHasOptionsMenu(false);
////        for(int i=0; i<pageList.size(); i++){
////            pageList.get(i).getFragment().setHasOptionsMenu(false);
////        }
//    }

    private class TabPageAdapter extends FragmentPagerAdapter{
        List<Page> pageList;

        public TabPageAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
            pageList = TabFragment.this.pageList;
        }

        @Override
        public Fragment getItem(int position) {
            return pageList.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageList.get(position).getTitle();
        }
    }



}
