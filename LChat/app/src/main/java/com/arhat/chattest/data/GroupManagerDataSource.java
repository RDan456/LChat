package com.arhat.chattest.data;

import com.arhat.chattest.model.GroupName;
import com.arhat.chattest.presenter.GroupManagerContact;

/**
 * Created by lihanguang on 2016/9/29.
 */
public class GroupManagerDataSource implements GroupManagerContact.DataSource {
    @Override
    public GroupName getDefaultGroup() {
        return new GroupName("大学同学", "");
    }
}
