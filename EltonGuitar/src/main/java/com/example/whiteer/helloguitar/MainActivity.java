package com.example.whiteer.helloguitar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private Toolbar tbSearch;
    private int currentPage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();


    }

    public void findViews(){


        tbSearch = (Toolbar)findViewById(R.id.tbSearch);
        setSupportActionBar(tbSearch);
        viewPager = (MainViewPager)findViewById(R.id.mainViewPager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                myPagerAdapter.getItem(currentPage).setHasOptionsMenu(false);
                myPagerAdapter.getItem(position).setHasOptionsMenu(true);
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void setPage(int index,Page page){
        myPagerAdapter.setPage(index, page);

        currentPage = 0;
        myPagerAdapter.getItem(0).setHasOptionsMenu(true);
        for (int i=1; i<myPagerAdapter.getCount();i++){
            myPagerAdapter.getItem(i).setHasOptionsMenu(false);
        }
        viewPager.setAdapter(viewPager.getAdapter());
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        List<Page> pageList;
        FragmentManager fragmentManager;
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//            pageList = new ArrayList<>();
//            pageList.add(new Page(PageID.MainPageID,new NewFragment()));
//            pageList.add(new Page(PageID.MainPageID,new TestFragment()));
//        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
            Bundle bundle = new Bundle();

            pageList = new ArrayList<>();

            List<Page> tabPageList = new ArrayList<>();
            tabPageList.add(new Page(PageID.OrderNewPageID, new NewFragment(), PrefManager.OrderNewPageTitle));
            tabPageList.add(new Page(PageID.OrderRatePageID,new RateFragment(), PrefManager.OrderRatePageTitle));
            pageList.add(new Page(PageID.MainPageID, new SearchFragment(tabPageList), PrefManager.MainPageTitle));

            pageList.add(new Page(PageID.LoginPageID, new LoginFragment(), PrefManager.LoginPageTitle));
            pageList.add(new Page(PageID.LoginPageID, new LoginFragment(), PrefManager.LoginPageTitle));

        }

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return pageList.get(position).getFragment();
        }

        public void setPage(int index,Page page){

            Fragment fragment = pageList.get(index).getFragment();
            FragmentTransaction fragmentTransaction = fragment.getFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
            pageList.set(index, page);
            notifyDataSetChanged();

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageList.get(position).getTitle();
        }

    }

}
