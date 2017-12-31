package com.arhat.chattest.data;

import com.arhat.chattest.model.GroupName;
import com.arhat.chattest.presenter.SelectGroupContact;
import com.arhat.chattest.xmpp.FriendsManager;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/29.
 */
public class SelectGroupDataSource implements SelectGroupContact.DataSource {
    @Override
    public List<GroupName> getGroupName() {
        FriendsManager friendsManager = new FriendsManager();
        return friendsManager.getGroupNames();
    }
}
