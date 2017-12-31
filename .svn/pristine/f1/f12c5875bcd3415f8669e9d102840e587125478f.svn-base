package com.arhat.chattest.data;

import com.arhat.chattest.constants.MyApplication;
import com.arhat.chattest.model.Dialogue;
import com.arhat.chattest.model.Friend;
import com.arhat.chattest.presenter.DialogueFragmentContact;
import com.arhat.chattest.sqllit.NewMessageDBHelper;
import com.arhat.chattest.xmpp.FriendsManager;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lihanguang on 2016/9/26.
 */
public class DialogueDataSource implements DialogueFragmentContact.DataSource {

    //获取数据
    @Override
    public Observable<List<Dialogue>> getDialogue() {

        FriendsManager manager = new FriendsManager();
        List<Friend> friends = manager.getAllFriends();
        final List<Dialogue> dialogues = NewMessageDBHelper.getInstance(MyApplication.getInstance()).getAllNewMessage();
//        for (Friend friend : friends) {
//            int number = NewMessageDBHelper.getInstance(MyApplication.getInstance()).getMessageCount(friend.getUsername());
//            if (number != 0) {
//                List<ChatItem> chatItems = MessageDBHelper.getInstance(MyApplication.getInstance()).
//                        getChatMessage(friend.getUsername());
//                ChatItem chatItem = chatItems.get(chatItems.size() - 1);
//                dialogues.add(new Dialogue(friend.getUserHead(), friend.getUsername(), number, 0, chatItem.msg));
//            }
//        }
        return Observable.create(new Observable.OnSubscribe<List<Dialogue>>() {
            @Override
            public void call(Subscriber<? super List<Dialogue>> subscriber) {
                subscriber.onNext(dialogues);
                subscriber.onCompleted();
            }
        });
    }

}
