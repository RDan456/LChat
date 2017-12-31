package com.arhat.chattest.data;

import com.arhat.chattest.model.SearchItem;
import com.arhat.chattest.presenter.SearchActivityContact;
import com.arhat.chattest.xmpp.FriendsManager;
import com.arhat.chattest.xmpp.XmppConnection;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihanguang on 2016/9/27.
 */
public class SearchDataSource implements SearchActivityContact.DataSource {
    @Override
    public List<SearchItem> getPeopleByContent(String content) {
        List<SearchItem> iems = new ArrayList<>();
        FriendsManager friendsManager = new FriendsManager();
        List<String> entries = friendsManager.searchUser(content);

        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        SearchItem item;
        for(int i = 0 ; i < entries.size();i++) {
            RosterEntry entry = roster.getEntry(XmppConnection.getFullUsername(entries.get(i)));
            item = new SearchItem(XmppConnection.getUsername(entry.getUser()),XmppConnection.getUsername(entry.getUser()),"");
          //  System.out.println(entry.getName()+"wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
            System.out.println(item.getName()+"dddddddddddddddddddddddddddddddddddddd"+item.getIconUrl());
            iems.add(item);
        }
        return iems;
    }

    @Override
    public List<SearchItem> getGroupByContent(String content) {
        List<SearchItem> items = new ArrayList<>();
        items.add(new SearchItem("asdasd","诺克萨斯集中营","开黑的小伙伴快来吧"));
        items.add(new SearchItem("asdasd","诺克萨斯集中营","开黑的小伙伴快来吧"));
        items.add(new SearchItem("asdasd","诺克萨斯集中营","开黑的小伙伴快来吧"));
        items.add(new SearchItem("asdasd","诺克萨斯集中营","开黑的小伙伴快来吧"));
        items.add(new SearchItem("asdasd","诺克萨斯集中营","开黑的小伙伴快来吧"));
        return items;
    }

    @Override
    public List<SearchItem> getSubscriptionByContent(String content) {
        return null;
    }
}
