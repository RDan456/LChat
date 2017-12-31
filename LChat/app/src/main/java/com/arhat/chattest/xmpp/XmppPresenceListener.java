package com.arhat.chattest.xmpp;

import android.content.Intent;
import android.util.Log;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.constants.MyApplication;
import com.arhat.chattest.model.ChatItem;
import com.arhat.chattest.model.Friend;
import com.arhat.chattest.sqllit.MessageDBHelper;
import com.arhat.chattest.sqllit.NewFriendsDBHelper;
import com.arhat.chattest.sqllit.NewMessageDBHelper;
import com.arhat.chattest.util.DateUtil;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.packet.RosterPacket;

/**
 * Created by Arhat on 2016/9/4.
 */
public class XmppPresenceListener implements StanzaListener {

    @Override
    public void processPacket(Stanza stanza) throws SmackException.NotConnectedException {

        Presence presence = (Presence) stanza;
        FriendsManager manager = new FriendsManager();
        if(Constants.IS_DEBUG) {
            Log.e("XMPPCAHT COME",presence.toXML().toString());
        }
        String jid = presence.getFrom(); //发送消息的一方
        String to = presence.getTo(); //接收消息的一方

        //Presence.Type一个人的状态
        if(presence.getType().equals(Presence.Type.subscribe)) {//在线

            if(manager.getFriends().contains(new Friend(XmppConnection.getUsername(jid)))){

                Friend friend = new Friend(XmppConnection.getUsername(jid));
                friend.setType(RosterPacket.ItemType.from);
                manager.getFriends().add(friend);
            }

            for(Friend friend : manager.getFriends() ) {

                System.out.println("friend"+friend.getUsername()+"chat with me"+friend.getType());
                if(friend.equals(new Friend(XmppConnection.getUsername(jid))) && friend.getType() == RosterPacket.ItemType.to) {

                    String username = XmppConnection.getUsername(jid);
                    System.out.println(username+"同意添加好友");
                    ChatItem chatItem = new ChatItem(ChatItem.CHAT,username,username,"",username+"同意添加好友", DateUtil.now_MM_dd_HH_mm_ss(),0);
                    NewMessageDBHelper.getInstance(MyApplication.getInstance()).saveNewMessage(username,"in");
                    MessageDBHelper.getInstance(MyApplication.getInstance()).saveMassage(chatItem);
                    MyApplication.getInstance().sendBroadcast(new Intent("Chat new message"));
                    //改变好友的状态
                    XmppConnection.getIntence().changeFriend(friend, RosterPacket.ItemType.both);
                    Log.e("friend",to+"我收到好友的请求");
                }
                else if(friend.getUsername().equals(XmppConnection.getUsername(jid))) {

                    //改变好友的状态
                    XmppConnection.getIntence().changeFriend(friend, RosterPacket.ItemType.from);
                    Log.e("friend",to+"我收到好友请求");
                    System.out.println(friend.getUsername()+"申请好友");
                    NewFriendsDBHelper.getInstance(MyApplication.getInstance()).saveNewFriend(XmppConnection.getUsername(jid));
                }
            }
            MyApplication.getInstance().sendBroadcast(new Intent("friend state change"));
        }
        else if (presence.getType().equals(Presence.Type.subscribed)) {

            if(Constants.IS_DEBUG) {
                Log.e("friend",jid+"同意添加");
            }
        }
        else if(presence.getType().equals(Presence.Type.unsubscribe) || presence.getType().equals(Presence.Type.unsubscribed)) {

            if(Constants.IS_DEBUG) {
                Log.e("friend","我被好友"+jid+"删除了");
            }
            for (Friend friend : manager.getFriends()) {

                if(friend.equals(new Friend(XmppConnection.getUsername(jid)))) {
                    XmppConnection.getIntence().changeFriend(friend, RosterPacket.ItemType.remove);
                }
            }
            MyApplication.getInstance().sendBroadcast(new Intent("friend state change"));
        }
    }
}
