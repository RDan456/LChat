package com.arhat.chattest.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lihanguang on 2016/9/9.
 */
public class NoSrcollViewPager extends ViewPager {
    public NoSrcollViewPager(Context context) {
        super(context);
    }

    public NoSrcollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
