package com.example.whiteer.helloguitar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by whiteer on 16/05/21.
 */
public class MainViewPager extends ViewPager{

    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    boolean isDragging = false;


    public MainViewPager(Context context) {
        super(context);
    }

    public MainViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
//                System.out.println("down");
                x1 = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:
//                System.out.println("move");
                if(isDragging)return true;
                else{
                    x2 = ev.getX();
                    float deltaX = x2 - x1;
                    if(Math.abs(deltaX) > MIN_DISTANCE){
                        isDragging = true;
                        return true;
                    }
                }

            case MotionEvent.ACTION_UP:
//                System.out.println("up");
                isDragging = false;
                break;

            default:
                break;
        }

//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                System.out.println("down");
//                x1 = ev.getX();
//                break;
//            case MotionEvent.ACTION_UP:
//                System.out.println("up");
//                x2 = ev.getX();
//                float deltaX = x2 - x1;
//                if(Math.abs(deltaX) > MIN_DISTANCE){
//                    isDragging = true;
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                System.out.println("move");
//                if(isDragging)return true;
//
//            default:
//                break;
//        }

        return super.onInterceptTouchEvent(ev);

    }

}
