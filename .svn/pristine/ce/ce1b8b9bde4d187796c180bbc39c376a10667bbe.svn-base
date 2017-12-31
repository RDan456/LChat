package com.arhat.chattest.data;

import com.arhat.chattest.model.ChatGroup;
import com.arhat.chattest.presenter.ChatGroupContact;
import com.arhat.chattest.util.L;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lihanguang on 2016/10/21.
 */
public class ChatGroupDataSource implements ChatGroupContact.DataSource {
    @Override
    public Observable<List<ChatGroup>> getChatGroups() {
        return Observable.create(new Observable.OnSubscribe<List<ChatGroup>>() {
            @Override
            public void call(Subscriber<? super List<ChatGroup>> subscriber) {
                List<ChatGroup> chatGroups = new ArrayList<ChatGroup>();
                chatGroups.add(new ChatGroup("asdasdadwd","诺克萨斯","akjsdhakjdajsdg","无"));
                chatGroups.add(new ChatGroup("asdasdadwd","诺克萨斯","akjsdhakjdajsdg","无"));
                chatGroups.add(new ChatGroup("asdasdadwd","诺克萨斯","akjsdhakjdajsdg","无"));
                chatGroups.add(new ChatGroup("asdasdadwd","诺克萨斯","akjsdhakjdajsdg","无"));
                chatGroups.add(new ChatGroup("asdasdadwd","诺克萨斯","akjsdhakjdajsdg","无"));
                chatGroups.add(new ChatGroup("asdasdadwd","诺克萨斯","akjsdhakjdajsdg","无"));
                //需要添加

                L.d("chatGroup:" + chatGroups.toString());
                subscriber.onNext(chatGroups);
                subscriber.onCompleted();
            }
        });
    }
}
