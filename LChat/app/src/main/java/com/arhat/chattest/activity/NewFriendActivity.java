package com.arhat.chattest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.adapter.NewFriendAdapter;
import com.arhat.chattest.data.NewFriendDataSource;
import com.arhat.chattest.model.NewFriend;
import com.arhat.chattest.presenter.NewFriendActivityContact;
import com.arhat.chattest.presenter.NewFriendPresenter;
import com.arhat.chattest.util.T;

import java.util.List;

public class NewFriendActivity extends AppCompatActivity implements NewFriendActivityContact.View {
    private ImageView iv_back;
    private TextView tv_right;
    private ListView lv;
    private RelativeLayout rl_loading;
    private NewFriendActivityContact.Presenter mPresenter;
    private NewFriendAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        new NewFriendPresenter(new NewFriendDataSource(),this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_right = (TextView) findViewById(R.id.tv_right);
        lv = (ListView) findViewById(R.id.lv);
        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);
        iv_back.setOnClickListener(mBackListener);
        tv_right.setOnClickListener(mAddNewFriendListener);
    }

    /**
     * 返回点击事件
     */
    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.back();
        }
    };

    /**
     * 添加好友页面点击事件
     */
    private View.OnClickListener mAddNewFriendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.openAddFriend();
        }
    };

    @Override
    public void back() {
        finish();
    }

    @Override
    public void showNewFriends(List<NewFriend> newFriends) {
        mAdapter = new NewFriendAdapter(getApplicationContext(), newFriends, R.layout.item_newfriend);
        lv.setAdapter(mAdapter);
    }

    @Override
    public void showAddNewFriend() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNewFriendInfo(NewFriend newFriend) {
        Intent intent = new Intent(this, FriendInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(NewFriendActivityContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showError() {
        T.showShort(getApplicationContext(), "出错误！");
    }

    @Override
    public void showNoData() {
        T.showShort(getApplicationContext(), "没有数据！");
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            rl_loading.setVisibility(View.VISIBLE);
        } else {
            rl_loading.setVisibility(View.INVISIBLE);
        }
    }
}
