package com.arhat.chattest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.arhat.chattest.model.Tab;
import com.arhat.chattest.util.L;

/**
 * Created by lihanguang on 2016/9/19.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private Tab[] mTabs;

    public MyFragmentPagerAdapter(FragmentManager fm, Tab[] tab) {
        super(fm);
        mTabs = tab;
    }

    @Override
    public Fragment getItem(int position) {
        if (position < 0) {
            L.d("carl", "position小于0");
            return null;
        }
        if (position > mTabs.length) {
            L.d("carl", "position小于mTab.length");
            return null;
        }
        L.d("carl","position=" + position);
        return mTabs[position].mFragment;
    }

    
    @Override
    public int getCount() {
        return mTabs.length;
    }
}
