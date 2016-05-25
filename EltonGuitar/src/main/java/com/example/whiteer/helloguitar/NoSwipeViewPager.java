package com.example.whiteer.helloguitar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by whiteer on 16/05/21.
 */
public class NoSwipeViewPager extends ViewPager {

    float last_x = 0;

    public NoSwipeViewPager(Context context) {
        super(context);
    }

    public NoSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //disallow swiping to switch between pages
        return false;

    }

}
