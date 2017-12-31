package com.arhat.chattest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.adapter.ChatGroupAdapter;
import com.arhat.chattest.adapter.SearchItemAdapter;
import com.arhat.chattest.data.ChatGroupDataSource;
import com.arhat.chattest.model.ChatGroup;
import com.arhat.chattest.presenter.ChatGroupContact;
import com.arhat.chattest.presenter.ChatGroupPresenter;
import com.arhat.chattest.util.T;

import java.util.List;

public class ChatGroupActivity extends AppCompatActivity implements ChatGroupContact.View {
    private ChatGroupContact.Presenter mPresenter;
    private ListView mListView;
    private ChatGroupAdapter mChatGroupAdapter;
    private TextView tv_name;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_group);
        mListView = (ListView) findViewById(R.id.lv);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText("群聊");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(mBackListener);
        mListView.setOnItemClickListener(mOnItemClickListener);
        new ChatGroupPresenter(this, new ChatGroupDataSource());
    }

    /**
     * 返回点击事件
     */
    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            back();
        }
    };

    /**
     * item点击事件
     */
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mPresenter.openChatGroup(position);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void showChatGroups(List<ChatGroup> chatGroups) {
        mChatGroupAdapter = new ChatGroupAdapter(getApplicationContext(), chatGroups, R.layout.item_chatgroup);
        mListView.setAdapter(mChatGroupAdapter);
    }

    @Override
    public void showChatGroup(ChatGroup chatGroup) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("username", chatGroup.getId());
        startActivity(intent);
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void setPresenter(ChatGroupContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showError() {
        T.showShort(getApplicationContext(), "出错误!");
    }

    @Override
    public void showNoData() {
        T.showShort(getApplicationContext(), "没有数据!");
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        //空实现
    }
}
