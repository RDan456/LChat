package com.arhat.chattest.data;

import android.content.Context;
import android.content.Intent;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.model.ChatItem;
import com.arhat.chattest.model.ChatMessage;
import com.arhat.chattest.presenter.MessageContact;
import com.arhat.chattest.sqllit.MessageDBHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lihanguang on 2016/10/18.
 */
public class MessageDataSrouce implements MessageContact.DataSource {
    private Context mContext;

    public MessageDataSrouce(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<ChatMessage>> getLastChatMessage(final String username) {
        return Observable.create(new Observable.OnSubscribe<List<ChatMessage>>() {
            @Override
            public void call(Subscriber<? super List<ChatMessage>> subscriber) {
                List<ChatItem> items = MessageDBHelper.getInstance(mContext).getChatMessage(username);
                List<ChatMessage> mMessageList = new ArrayList<>();
                for (ChatItem item : items) {
                    boolean flag = true;
                    if (item.inOrOut == 1) {
                        item.username = Constants.USER_NAME;
                        flag = false;
                    }
                    mMessageList.add(new ChatMessage(item.username, item.msg, item.head, flag));
                }
                subscriber.onNext(mMessageList);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<ChatMessage>> getChatMessage(final String username, final int size) {
        return Observable.create(new Observable.OnSubscribe<List<ChatMessage>>() {
            @Override
            public void call(Subscriber<? super List<ChatMessage>> subscriber) {
                List<ChatItem> items = MessageDBHelper.getInstance(mContext).getMoreMessage(size,username);
                List<ChatMessage> mMessageList = new ArrayList<>();
                for (ChatItem item : items) {
                    boolean flag = true;
                    if (item.inOrOut == 1) {
                        item.username = Constants.USER_NAME;
                        flag = false;
                    }
                    mMessageList.add(new ChatMessage(item.username, item.msg, item.head, flag));
                }
                subscriber.onNext(mMessageList);
                subscriber.onCompleted();
            }
        });
    }
}
