package com.arhat.chattest.data;

import com.arhat.chattest.model.Friend;
import com.arhat.chattest.model.Group;
import com.arhat.chattest.presenter.ContactsFragmentContact;
import com.arhat.chattest.xmpp.FriendsManager;
import com.arhat.chattest.xmpp.XmppConnection;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lihanguang on 2016/9/23.
 */
public class ContactDataSource implements ContactsFragmentContact.DataSource {

    @Override
    public Observable<List<Group>> getGroup() {

        final List<Group> groups = new ArrayList<Group>();
        //增加两个固定的列表
        groups.add(new Group("特别关心", 0, 0, null));
        groups.add(new Group("常用群聊", 0, 0, null));
        //将没有分组的好友全部发在我的好友这个列表中
        FriendsManager manager = new FriendsManager();
        List<Friend> noGroupFriends = manager.getUnfiledUser();
        if (noGroupFriends.size() != 0) {
            groups.add(new Group("我的好友", noGroupFriends.size(), manager.getAvailableUserNum(), noGroupFriends));
        }
        //获取分组的好友
        List<RosterGroup> rosterGroups = manager.getGroups();
        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        List<Friend> friends;
        Friend friend;

        for (RosterGroup group : rosterGroups) {

            int onlineNum = 0;
            friends = new ArrayList<>();
            for (RosterEntry entry : group.getEntries()) {

                friend = new Friend();
                Presence presence = roster.getPresence(entry.getUser());
                if (presence.getType().toString().equals("available")) {
                    friend.setTextState("在线");
                    onlineNum++;
                } else if (presence.getType().toString().equals("unavailable")) {
                    friend.setTextState("离线");
                } else {
                    //还有自己自定义的状态，包括隐身等
                }
                friend.setUsername(XmppConnection.getUsername(entry.getUser()));
                friend.setName(entry.getName());
                friends.add(friend);
            }
            groups.add(new Group(group.getName(), group.getEntryCount(), onlineNum, friends));
        }
        return Observable.create(new Observable.OnSubscribe<List<Group>>() {
            @Override
            public void call(Subscriber<? super List<Group>> subscriber) {
                subscriber.onNext(groups);
                subscriber.onCompleted();
            }
        });
    }

}
