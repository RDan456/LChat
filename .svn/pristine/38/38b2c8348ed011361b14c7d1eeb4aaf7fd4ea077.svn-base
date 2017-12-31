package com.arhat.chattest.xmpp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.model.Friend;
import com.arhat.chattest.model.GroupName;
import com.arhat.chattest.util.FormatTools;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.ReportedData.Row;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jivesoftware.smackx.vcardtemp.provider.VCardProvider;
import org.jivesoftware.smackx.xdata.Form;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Arhat on 2016/9/4.
 * 好友管理
 */
public class FriendsManager  {

    /**
     * 搜索好友
     */
    public List<String> searchUser(String key) {

        List<String> userList = new ArrayList<String>();
        try {
            UserSearchManager searchManager = new UserSearchManager(XmppConnection.getIntence().getConnection());
            Form searchFrom = searchManager.getSearchForm("search."+ Constants.SERVER_NAME);
            Form answerFrom = searchFrom.createAnswerForm();
            answerFrom.setAnswer("Username",true);
            answerFrom.setAnswer("search",key);
            ReportedData data = searchManager.getSearchResults(answerFrom,"search."+Constants.SERVER_NAME);

            Iterator<Row> it = data.getRows().iterator();
            ReportedData.Row row = null;
            while (it.hasNext()) {
                row = it.next();
                userList.add(row.getValues("Username").iterator().next().toString());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * 添加好友，没有分组和备注
     * @return
     */
    public boolean addFriend(String name) {

        if(XmppConnection.getIntence().getConnection() == null) {
            return false;
        }
        try {
            Roster.getInstanceFor(XmppConnection.getIntence().getConnection())
                    .createEntry(XmppConnection.getFullUsername(name),XmppConnection.getFullUsername(name),null);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加好友，没有分组，但是有名字
     * @return
     */
    public boolean addFriend(String username,String name) {

        if(XmppConnection.getIntence().getConnection() == null) {
            return false;
        }
        try {
            Roster.getInstanceFor(XmppConnection.getIntence().getConnection())
                    .createEntry(XmppConnection.getFullUsername(username),name,null);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 增加好友，有昵称和分组
     * @return
     */
    public boolean addFriend(String username,String name,String group) {

        if(XmppConnection.getIntence().getConnection() == null) {
            return false;
        }
        try {
            Roster.getInstanceFor(XmppConnection.getIntence().getConnection())
                    .createEntry(XmppConnection.getFullUsername(username),name,new String[]{group});
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除好友
     * @return
     */
    public boolean removeFriend(String username) {

        if(XmppConnection.getIntence().getConnection() == null) {
            return false;
        }
        try {
            RosterEntry entry = null;
            if(username.contains("@")) {

                entry = Roster.getInstanceFor(XmppConnection.getIntence().getConnection())
                        .getEntry(username);
            }else {
                entry = Roster.getInstanceFor(XmppConnection.getIntence().getConnection())
                        .getEntry(username + "@" + XmppConnection.getIntence().getConnection().getServiceName());
            }
            if(entry == null) {
                entry = Roster.getInstanceFor(XmppConnection.getIntence().getConnection())
                        .getEntry(username);
            }
            Roster.getInstanceFor(XmppConnection.getIntence().getConnection()).removeEntry(entry);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改用户信息
     */
    public boolean updateUserInfro(VCard vCard) {

        if(XmppConnection.getIntence().getConnection() == null) {
            return false;
        }
        try {
            ProviderManager .addIQProvider("Vcard","Vcard-temp",new VCardProvider());
            vCard.save(XmppConnection.getIntence().getConnection());
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取用户信息
     *
     */
    public VCard getUserInfo(String user) {

        try {
            VCard vCard = new VCard();

            ProviderManager.addIQProvider("vCard","Vcard-temp",new VCardProvider());
            if(user == null) {
                vCard.load(XmppConnection.getIntence().getConnection());
            }else {
                vCard.load(XmppConnection.getIntence().getConnection(),user + "@" + Constants.SERVER_NAME);
            }
            if(vCard != null) {
                return vCard;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 获取用户头像信息
     *
     */
    public Bitmap getUserImage(String user){

        ByteArrayInputStream bais = null;
        try {
            VCard vCard = new VCard();
            if(user == null) {
                vCard.load(XmppConnection.getIntence().getConnection());
            }else {
                vCard.load(XmppConnection.getIntence().getConnection(),user+"@"+Constants.SERVER_NAME);
            }
            if(vCard == null || vCard.getAvatar() == null) {

                return  null;
            }
            bais = new ByteArrayInputStream(vCard.getAvatar());
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(bais == null) {
            return  null;
        }
        return FormatTools.getInstance().InputStream2Bitmap(bais);
    }

    /**
     * 修改用户头像
     */
    public Bitmap changeImage(File file) {

        Bitmap bitmap = null;
        if(XmppConnection.getIntence().getConnection() == null) {
            return bitmap;
        }
        try {
            //获取VCard
            VCard vCard = null;
            ProviderManager.addIQProvider("vCard", "vcard-temp", new VCardProvider());
            byte[] bytes;
            bytes = getFileBytes(file);
            String encodedImage = StringUtils.encodeHex(bytes);
            vCard.setField("avatar",encodedImage);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            bitmap = BitmapFactory.decodeStream(byteArrayInputStream);

            vCard.save(XmppConnection.getIntence().getConnection());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 将文件转化为字节
     */
    private byte[] getFileBytes(File file) throws IOException {

        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            int bytes = (int)file.length();
            byte[] buffer = new byte[bytes];
            int readBytes = bufferedInputStream.read(buffer);
            if(readBytes != buffer.length) {

                throw new IOException("输入文件不正确");
            }
            return  buffer;
        }finally {
            if(bufferedInputStream != null) {
                bufferedInputStream.close();
            }
        }
    }

    /**
     * 创建好友分组
     */
    public boolean createGroup(String groupName) {

        if(XmppConnection.getIntence().getConnection() == null) {

            throw new NullPointerException("服务器连接失败，请重新连接");
        }
        try {
            Roster.getInstanceFor(XmppConnection.getIntence().getConnection()).createGroup(groupName);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改组的名称
     */
    public boolean updateGroupName(String oldGroupName,String newGroupName){

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请重新连接服务器");
        }
        try {
            Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
            RosterGroup rosterGroup = roster.getGroup(oldGroupName);

            //把该用户下的所有用户拿出来，并且拷贝到新的组下面
            Collection<RosterEntry> entries = rosterGroup.getEntries();
            rosterGroup.setName(newGroupName);
            String [] groups = new String[]{newGroupName};
            if(rosterGroup != null) {
                try {
                    Iterator<RosterEntry> iterators = entries.iterator();
                    while(iterators.hasNext()) {
                        RosterEntry rosterEntry = iterators.next();
                        roster.createEntry(rosterEntry.getUser(),rosterEntry.getName(),groups);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 将好友从一个组里移动到另一个组
     */
    public boolean removeFriendToOtherGroup(String groupName,String userName,String newGroupName) {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请重新连接");
        }
        if(!userName.contains("@")) {
            userName = userName + "@" + XmppConnection.getIntence().getConnection().getServiceName();
        }
        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        RosterEntry entry = roster.getEntry(userName);
        RosterGroup oldRosterGroup = roster.getGroup(groupName);
        RosterGroup newRosterGroup = roster.getGroup(newGroupName);
        try {
            newRosterGroup.addEntry(entry);
            oldRosterGroup.removeEntry(entry);
        }catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
        return true;
    }

    /**
     * 将好友从一个组复制到另外一个组中
     *
     */
    public boolean copyFriendToOtherGroup(String userName,String groupName) {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请重新连接");
        }
        if(!userName.contains("@")) {
            userName = userName + "@" + XmppConnection.getIntence().getConnection().getServiceName();
        }
        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        RosterEntry entry = roster.getEntry(userName);
        RosterGroup rosterGroup = roster.getGroup(groupName);
        try {
            rosterGroup.addEntry(entry);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取自己的好友分组
     * @return
     */
    public List<RosterGroup> getGroups() {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请重新连接服务器");
        }
        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        List<RosterGroup> groups = new ArrayList<>();
        Collection<RosterGroup> groupEntries = roster.getGroups();
        for (RosterGroup group : groupEntries) {

            groups.add(group);
        }
        return groups;
    }

    public List<GroupName> getGroupNames(){
        List<GroupName> groupNames = new ArrayList<>();
        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请重新连接服务器");
        }
        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        Collection<RosterGroup> groupEntries = roster.getGroups();
        for (RosterGroup group : groupEntries) {
            GroupName name = new GroupName(group.getName(),group.getName());
            groupNames.add(name);
        }
        return groupNames;
    }

    /**
     * 获取自己的好友
     */
    public List<Friend> getFriends() {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请重新连接服务器");
        }
        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        roster.addRosterListener(new XmppRosterListener());
        Collection<RosterEntry> entries = roster.getEntries();
        List<Friend> friends = new ArrayList<Friend>();
        Friend friend;
        for (RosterEntry entry : entries) {

            friend = new Friend();
            Presence presence = roster.getPresence(entry.getUser());
            if(presence.getType().toString().equals("available")) {
                friend.setTextState("在线");
            }
            else if(presence.getType().toString().equals("unavailable")){
                friend.setTextState("离线");
            }else {
                //还有自己自定义的状态，包括隐身等
            };
            friend.setUsername(XmppConnection.getUsername(entry.getUser()));
            friend.setName(entry.getName());
            friends.add(friend);
        }
        return friends;
    }

    /**
     * 获取没有分组的在线的好友的人数
     */
    public int getAvailableUserNum() {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请重新连接服务器");
        }
        int availablePersonNum = 0;
        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        Collection<RosterEntry> entries = roster.getUnfiledEntries();
        for(RosterEntry entry : entries) {

            Presence presence = roster.getPresence(entry.getUser());
            if(presence.getType().toString().equals("available")) {
                availablePersonNum++;
            }
        }
        return availablePersonNum;
    }
    /**
     * 获取没有分组的好友
     */
    public List<Friend> getUnfiledUser() {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请重新连接服务器");
        }
        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        Collection<RosterEntry> entries = roster.getUnfiledEntries();
        List<Friend> friends = new ArrayList<Friend>();
        Friend friend;
        for(RosterEntry entry : entries) {

            friend = new Friend();
            Presence presence = roster.getPresence(entry.getUser());
            if(presence.getType().toString().equals("available")) {
                friend.setTextState("在线");
            }
            else if(presence.getType().toString().equals("unavailable")){
                friend.setTextState("离线");
            }else {
                //还有自己自定义的状态，包括隐身等
            }
            friend.setUsername(XmppConnection.getUsername(entry.getUser()));
            friend.setName(entry.getName());
            friends.add(friend);
        }
        return friends;
    }

    /**
     * 获取所有的好友
     * @return
     */
    public List<Friend> getAllFriends() {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请重新连接服务器");
        }
        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        Collection<RosterEntry> entries = roster.getEntries();
        List<Friend> friends = new ArrayList<>();
        Friend friend;
        for(RosterEntry entry : entries) {

            friend = new Friend();
            Presence presence = roster.getPresence(entry.getUser());
            if(presence.getType().toString().equals("available")) {
                friend.setTextState("在线");
            }
            else if(presence.getType().toString().equals("unavailable")){
                friend.setTextState("离线");
            }else {
                //还有自己自定义的状态，包括隐身等
            }
            friend.setUsername(XmppConnection.getUsername(entry.getUser()));
            friend.setName(entry.getName());
            friends.add(friend);
        }
        return friends;
    }

    /**
     * 推荐可能认识的人
    * @return
     */
    public List<Friend> recommendFriends() {

        return null;
    }

    /**
     * 通过username查找昵称
     * @return
     */
    public String getNickName(String username) {

        Roster roster = Roster.getInstanceFor(XmppConnection.getIntence().getConnection());
        if(!username.contains("@")) {
            username = XmppConnection.getFullUsername(username);
        }
        RosterEntry entry = roster.getEntry(username);
        String name = "";
        if(entry != null){
            name = entry.getName();
        }
        return  name;
    }
}
