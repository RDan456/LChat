package com.arhat.chattest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.jingchen.pulltorefresh.Pullable;

/**
 * Created by lihanguang on 2016/10/18.
 */
public class PullToRefreshListView extends ListView implements Pullable {
    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (getFirstVisiblePosition() == 0
                && getChildAt(0).getTop() >= 0) {
            // 滑到ListView的顶部了
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        return false;
    }
}
