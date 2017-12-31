package com.arhat.chattest.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.adapter.MessageAdapter;
import com.arhat.chattest.constants.ActionUtil;
import com.arhat.chattest.constants.MyApplication;
import com.arhat.chattest.data.MessageDataSrouce;
import com.arhat.chattest.model.ChatMessage;
import com.arhat.chattest.presenter.MessageContact;
import com.arhat.chattest.presenter.MessagePresenter;
import com.arhat.chattest.util.L;
import com.arhat.chattest.util.T;
import com.arhat.chattest.xmpp.FriendsManager;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.List;

public class MessageActivity extends AppCompatActivity implements MessageContact.View, PullToRefreshLayout.OnPullListener {

    private ListView mListView;
    private MessageAdapter mMessageAdapter;
    private List<ChatMessage> mMessageList;
    private TextView chatFriendNameTextView;
    private EditText sendMessageEditText;
    private Button sendMessageButton;
    private Toolbar mToolbar;
    private RelativeLayout rl_root;
    private NewMessageBroadcastReceiver mReceiver;
    /**
     * 聊天对象的唯一id
     */
    private String userName;
    /**
     * 聊天对象的昵称
     */
    private String nickName;
    private PullToRefreshLayout prl;
    private MessageContact.Presenter mPresenter;
    /**
     * 软键盘是否打开
     */
    private boolean isOpenKeyBroad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        FriendsManager friendsManager = new FriendsManager();
        userName = intent.getStringExtra("username");//获取与自己聊天的唯一ID
        nickName = friendsManager.getNickName(userName);//获取与自己聊天的对象
//        init();
        prl = (PullToRefreshLayout) findViewById(R.id.prl);
        mListView = (ListView) findViewById(R.id.lv);
        mToolbar = (Toolbar) findViewById(R.id.tb);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
        chatFriendNameTextView = (TextView) findViewById(R.id.chatFriendName);
        sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
        sendMessageEditText = (EditText) findViewById(R.id.messageContent);
        //设置Toolbar

        chatFriendNameTextView.setText(nickName);

        prl.setOnPullListener(this);
        //设置handler
        sendMessageButton.setOnClickListener(new SendMessageButtonOnClickListener());
        //监听软键盘
        rl_root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        int heightDiff = rl_root.getRootView().getHeight() - rl_root.getHeight();

                        if (heightDiff > 100) { // 说明键盘是弹出状态
                            L.d("asdasd");
                            if (!isOpenKeyBroad) {
                                isOpenKeyBroad = true;
                                scrollToEnd();
                            }
                        } else {
                            isOpenKeyBroad = false;
                        }
                    }
                });
        new MessagePresenter(this, new MessageDataSrouce(this), this, userName);
//        handler = new Handler() {
//
//            @Override
//            public void handleMessage(Message message) {
//                super.handleMessage(message);
//                switch (message.what) {
//                    case 1:
//                        init();
//                        mMessageAdapter = new MessageAdapter(mMessageList, MessageActivity.this);
//                        mListView.setAdapter(mMessageAdapter);
//                        sendMessageEditText.setText("");
//                        break;
//                    case 0:
//                        sendMessageEditText.setText("");
//                        break;
//                }
//            }
//        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
        registerBroadcast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }
//
//    private void init() {
//
//        List<ChatItem> items;
//        Intent intent = getIntent();
//        String username = intent.getStringExtra("username");
//        String name = intent.getStringExtra("name");
//        items = MessageDBHelper.getInstance(this).getChatMessage(username);
//        mMessageList = new ArrayList<>();
//        for (ChatItem item : items) {
//
//            boolean flag = true;
//            if (item.inOrOut == 1) {
//                item.username = Constants.USER_NAME;
//                flag = false;
//            }
//            mMessageList.add(new ChatMessage(item.username, item.msg, item.head, flag));
//        }
//    }

    @Override
    public void showMessages(List<ChatMessage> chatMessages) {
        mMessageAdapter = new MessageAdapter(chatMessages, getApplicationContext());
        mListView.setAdapter(mMessageAdapter);
    }

    @Override
    public void notifyDataSourceChanged() {
        if (mMessageAdapter != null) {
            mMessageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void scrollToEnd() {
        //滑动到结束
        if (mListView != null && mMessageAdapter != null) {
            mListView.setSelection(mMessageAdapter.getCount() - 1);
            L.d("滑动到最后");
        }
    }

    @Override
    public void clearEditText() {
        sendMessageEditText.setText("");
    }

    @Override
    public void scrollToPostion(int position) {
        if(mListView != null){
            mListView.setSelection(position);
        }
    }

    @Override
    public void refreshSuccess() {
        if(prl != null){
            prl.refreshFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    @Override
    public void refreshFail() {
        if(prl != null){
            prl.refreshFinish(PullToRefreshLayout.FAIL);
        }
    }

    @Override
    public void setPresenter(MessageContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showError() {
        T.showShort(getApplicationContext(), "系统错误！");
    }

    @Override
    public void showNoData() {
        T.showShort(getApplicationContext(), "没有数据！");
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        //空实现
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        mPresenter.refresh();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }


    //发送消息,给发送按钮增加监听器
    class SendMessageButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String message = sendMessageEditText.getText().toString();
            mPresenter.submitNewMessage(message, userName);
//            try {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        Intent intent = getIntent();
//                        String username = intent.getStringExtra("username");
//                        String message = sendMessageEditText.getText().toString();
//                        if (!message.isEmpty()) {
//                            try {
//                                //这里是单聊
//                                ChatWithFriend.sendMsg(username, message, 0);
//                                System.out.println("发送成功");
//                                String sendDataTime = DateUtil.now_MM_dd_HH_mm_ss();
//                                ChatItem item = new ChatItem(ChatItem.CHAT, username, username, "", message, sendDataTime, 1);
//                                MessageDBHelper.getInstance(MessageActivity.this).saveMassage(item);
//                                Message msg = new Message();
//                                msg.what = 1;
//                                handler.obtainMessage();
//                                handler.sendMessage(msg);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                System.out.println("发送失败");
//                                Message m = new Message();
//                                m.what = 0;
//                                handler.obtainMessage();
//                                handler.sendMessage(m);
//                            }
//                        } else {
//                            System.out.println("输入消息不能为空");
//                        }
//                    }
//                }).start();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    /**
     * 消息广播
     */
    public void registerBroadcast() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(MyApplication.getInstance());
        mReceiver = new NewMessageBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ActionUtil.NEWMESSAGE_ACTION);
        manager.registerReceiver(mReceiver, filter);
    }

    public void unregisterBroadcast() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(MyApplication.getInstance());
        manager.unregisterReceiver(mReceiver);
    }

    class NewMessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.onReceive(context, intent);
        }
    }
}
