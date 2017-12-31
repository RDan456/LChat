package com.arhat.chattest.presenter;

import android.content.Context;
import android.content.Intent;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.model.ChatItem;
import com.arhat.chattest.model.ChatMessage;
import com.arhat.chattest.sqllit.MessageDBHelper;
import com.arhat.chattest.sqllit.NewMessageDBHelper;
import com.arhat.chattest.util.DateUtil;
import com.arhat.chattest.util.L;
import com.arhat.chattest.xmpp.ChatWithFriend;
import com.arhat.chattest.xmpp.XmppConnection;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lihanguang on 2016/10/18.
 */
public class MessagePresenter implements MessageContact.Presenter {
    private MessageContact.View mView;
    private MessageContact.DataSource mDataSource;
    private Context mContext;
    private List<ChatMessage> mChatMessages;
    private String userName;
    private int page;
    private Subscription mLastMessageSubscription;
    private Subscription mMoreMessageSubscription;

    public MessagePresenter(MessageContact.View view, MessageContact.DataSource dataSource,
                            Context context, String userName) {
        mView = view;
        mDataSource = dataSource;
        mContext = context;
        this.userName = userName;
        page = 1;
        mView.setPresenter(this);
    }

    @Override
    public void loadMessages() {
        mView.setLoadingIndicator(true);
        mLastMessageSubscription = mDataSource.getLastChatMessage(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ChatMessage>>() {
                    @Override
                    public void onCompleted() {
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onNext(List<ChatMessage> chatMessages) {
                        mChatMessages = chatMessages;
                        if (mChatMessages == null || mChatMessages.size() == 0) {
                            mView.showNoData();
                            mChatMessages = new ArrayList<ChatMessage>();
                        }
                        mView.showMessages(mChatMessages);
                        mView.scrollToEnd();
                    }
                });
    }

    @Override
    public void submitNewMessage(final String content, final String username) {
        Observable
                .create(new Observable.OnSubscribe<Void>() {
                    @Override
                    public void call(Subscriber<? super Void> subscriber) {
                        try {
                            //这里是单聊
                            ChatWithFriend.sendMsg(username, content, 0);
                            System.out.println("发送成功");
                            String sendDataTime = DateUtil.now_MM_dd_HH_mm_ss();
                            ChatItem item = new ChatItem(ChatItem.CHAT, username, username, "", content, sendDataTime, 1);
                            NewMessageDBHelper.getInstance(mContext).saveNewMessage(XmppConnection.getUsername(username), "out");
                            MessageDBHelper.getInstance(mContext).saveMassage(item);
                            if (mChatMessages == null) {
                                mChatMessages = new ArrayList<>();
                            }
                            boolean flag = true;
                            if (item.inOrOut == 1) {
                                item.username = Constants.USER_NAME;
                                flag = false;
                            }
                            mChatMessages.add(new ChatMessage(item.username, item.msg, item.head, flag));
                        } catch (Exception e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                        subscriber.onNext(null);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mView.notifyDataSourceChanged();
                        mView.clearEditText();
                        mView.scrollToEnd();
                    }
                });

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || context == null) {
            L.d("Message-onReceive:intent或context为空");
            return;
        }
        ChatMessage chatMessage = intent.getParcelableExtra(Constants.RECEIVER_NEWMESSAGE);
        if (chatMessage != null) {
            if (mChatMessages == null) {
                L.d("Message-onReceive:mChatMessages为空");
                return;
            }
            mChatMessages.add(chatMessage);
            mView.notifyDataSourceChanged();
            mView.scrollToEnd();
        } else {
            L.d("Message-onReceive:chatMessage");
        }
    }

    @Override
    public void refresh() {
        int size = mChatMessages == null ? 0 : mChatMessages.size();
        mMoreMessageSubscription = mDataSource.getChatMessage(userName, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ChatMessage>>() {
                    @Override
                    public void onCompleted() {
                        mView.refreshSuccess();

                    }

                    @Override
                    public void onError(Throwable e) {
                        L.d("refresh-onError:" + e.toString());
                        mView.refreshFail();
                    }

                    @Override
                    public void onNext(List<ChatMessage> chatMessages) {
                        if(chatMessages == null || chatMessages.size() == 0){
                            L.d("refresh-onNext:chatMessage为空");
                            return;
                        }
                        if (mChatMessages == null) {
                            mChatMessages = new ArrayList<ChatMessage>();
                        }
                        mChatMessages.addAll(0, chatMessages);
                        mView.scrollToPostion(chatMessages.size());
                    }
                });
    }

    @Override
    public void start() {
        loadMessages();
    }
}
