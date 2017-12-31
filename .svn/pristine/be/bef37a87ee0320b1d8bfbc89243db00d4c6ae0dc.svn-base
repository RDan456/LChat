package com.arhat.chattest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.data.GroupManagerDataSource;
import com.arhat.chattest.presenter.GroupManagerContact;
import com.arhat.chattest.presenter.GroupManagerPresenter;
import com.arhat.chattest.util.T;
import com.arhat.chattest.xmpp.FriendsManager;

import java.util.List;

public class GroupManagerActivity extends AppCompatActivity implements GroupManagerContact.View {
    private EditText et_remarks;
    private RelativeLayout rl_group_manager;
    private ImageView iv_back;
    private TextView tv_next;
    private TextView tv_group;
    private GroupManagerContact.Presenter mPresenter;
    private FriendsManager mFriendsManager;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manager);
        username = getIntent().getStringExtra("username");
        new GroupManagerPresenter(this, new GroupManagerDataSource());
        mFriendsManager = new FriendsManager();
        initView();
    }

    private void initView() {
        et_remarks = (EditText) findViewById(R.id.et_remarks);
        rl_group_manager = (RelativeLayout) findViewById(R.id.rl_group_manager);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_group = (TextView) findViewById(R.id.tv_group);
        iv_back.setOnClickListener(mBackListener);
        tv_next.setOnClickListener(mNextListener);
        rl_group_manager.setOnClickListener(mShowGroupSelectListener);
    }

    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.back();
        }
    };

    private View.OnClickListener mNextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.next(username, et_remarks.getText().toString());
        }
    };

    private View.OnClickListener mShowGroupSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.openGroupSelect();
        }
    };

    @Override
    public void back() {
        finish();
    }

    @Override
    public void next() {

    }

    @Override
    public void showGrooupSelect() {
        Intent intent = new Intent(this, SelectGroupActivity.class);
        startActivityForResult(intent, GroupManagerContact.REQUESTCODE);
    }

    @Override
    public void setGroupName(String groupName) {
        tv_group.setText(groupName);
    }

    @Override
    public void showError() {
        T.showShort(getApplicationContext(), "出现错误！");
    }

    @Override
    public void showNoData() {
        T.showShort(getApplicationContext(), "没有数据！");
    }

    @Override
    public void showAddSuccess() {
        T.showShort(getApplicationContext(), "添加成功！");
    }

    @Override
    public void showAddFail() {
        T.showShort(getApplicationContext(), "添加失败！");
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void setPresenter(GroupManagerContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(resultCode, requestCode, data);
    }
}
