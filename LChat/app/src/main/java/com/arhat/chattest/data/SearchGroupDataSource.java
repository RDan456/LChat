package com.arhat.chattest.data;

import com.arhat.chattest.model.ChatGroup;
import com.arhat.chattest.model.Friend;
import com.arhat.chattest.presenter.SearchGroupFragmentContact;
import com.arhat.chattest.presenter.SearchPeopleFragmentContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihanguang on 2016/9/20.
 */
public class SearchGroupDataSource implements SearchGroupFragmentContact.DataSource {
    @Override
    public List<ChatGroup> getGroups() {
        //mock 好友数据
        List<ChatGroup> chatGroups = new ArrayList<>();
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        return chatGroups;
    }

    @Override
    public List<ChatGroup> getGroups(String text) {
        List<ChatGroup> chatGroups = new ArrayList<>();
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        chatGroups.add(new ChatGroup("123", "lol小分队", "123", "这是诺克萨斯聚集地"));
        return chatGroups;
    }
}
