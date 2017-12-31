package com.arhat.chattest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.adapter.SelectGroupAdapter;
import com.arhat.chattest.data.SelectGroupDataSource;
import com.arhat.chattest.model.GroupName;
import com.arhat.chattest.presenter.GroupManagerContact;
import com.arhat.chattest.presenter.SelectGroupContact;
import com.arhat.chattest.presenter.SelectGroupPresenter;
import com.arhat.chattest.util.T;

import java.util.List;

public class SelectGroupActivity extends AppCompatActivity implements SelectGroupContact.View {
    private ImageView iv_back;
    private ListView mListView;
    private RelativeLayout rl_loading;
    private SelectGroupAdapter mAdapter;
    private SelectGroupContact.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);
        new SelectGroupPresenter(this, new SelectGroupDataSource());
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        mListView = (ListView) findViewById(R.id.lv);
        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);
        iv_back.setOnClickListener(mBackListener);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.back();
        }
    };

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mPresenter.selectGroupName(position);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void showGroupName(List<GroupName> groupNames) {
        mAdapter = new SelectGroupAdapter(getApplicationContext(), groupNames, R.layout.item_select_group);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void back(GroupName groupName) {
        if (groupName == null) {
            finish();
        } else {
            Intent intent = new Intent();
            intent.putExtra("grouname", groupName);
            setResult(GroupManagerContact.RESULTCODE, intent);
            finish();
        }
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            rl_loading.setVisibility(View.VISIBLE);
        } else {
            rl_loading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showNoData() {
        T.showShort(getApplicationContext(), "没有数据！");
    }


    @Override
    public void setPresenter(SelectGroupContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showError() {
        T.showShort(getApplicationContext(), "出错误啦！");
    }
}