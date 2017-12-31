package com.arhat.chattest.data;

import com.arhat.chattest.model.NewFriend;
import com.arhat.chattest.presenter.NewFriendActivityContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihanguang on 2016/10/3.
 */
public class NewFriendDataSource implements NewFriendActivityContact.DataSource {
    @Override
    public List<NewFriend> getNewFriends() {
        List<NewFriend> newFriends = new ArrayList<>();
        newFriends.add(new NewFriend("asd","爸爸",NewFriend.NOACCEPTED,"","请求添加好友","帐号查找"));
        newFriends.add(new NewFriend("asd","爸爸",NewFriend.NOACCEPTED,"","请求添加好友","帐号查找"));
        newFriends.add(new NewFriend("asd","爸爸",NewFriend.NOACCEPTED,"","请求添加好友","帐号查找"));
        return newFriends;
    }
}
