package com.arhat.chattest.xmpp;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.arhat.chattest.constants.ActionUtil;
import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.constants.MyApplication;
import com.arhat.chattest.model.ChatItem;
import com.arhat.chattest.model.ChatMessage;
import com.arhat.chattest.model.Dialogue;
import com.arhat.chattest.sqllit.MessageDBHelper;
import com.arhat.chattest.sqllit.NewFriendsDBHelper;
import com.arhat.chattest.sqllit.NewMessageDBHelper;
import com.arhat.chattest.util.DateUtil;
import com.arhat.chattest.util.L;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smackx.delay.packet.DelayInformation;

/**
 * Created by Arhat on 2016/10/14.
 */

public class XmppAllMessageListener implements StanzaListener {

    @Override
    public void processPacket(Stanza stanza) throws SmackException.NotConnectedException {

        if (stanza instanceof Message) {

            Message message = (Message) stanza;
            if (message.getBody() == null) {
                message.setBody("");
            }
            if (message.toXML().toString().contains("<invite")) {

                String notice = "你被邀请进入" + XmppConnection.getRoomName(message.getFrom());
                String username = XmppConnection.getRoomName(message.getFrom());
                ChatItem item = new ChatItem(ChatItem.GROUP_CHAT, username, username, "", message.getBody(), DateUtil.now_MM_dd_HH_mm_ss(), 0);
                NewMessageDBHelper.getInstance(MyApplication.getInstance()).saveNewMessage(username, "in");
                MessageDBHelper.getInstance(MyApplication.getInstance()).saveMassage(item);
                //发送消息广播

            }
            if (message.getType() == Message.Type.chat && !message.getBody().equals("")) {

                String chatName = XmppConnection.getUsername(message.getFrom());
                String username = XmppConnection.getUsername(message.getFrom());
                if (!chatName.equals(Constants.USER_NAME)) {

                    String dataString;
                    DelayInformation information = message.getExtension("x", "jabber:x:delay");
                    if (information == null) {
                        dataString = DateUtil.now_MM_dd_HH_mm_ss();
                    } else {
                        dataString = DateUtil.dateToStr_MM_dd_HH_mm_ss(information.getStamp());
                    }
                    ChatItem item = new ChatItem(ChatItem.CHAT, chatName, username, "", message.getBody(), dataString, 0);
                    NewMessageDBHelper.getInstance(MyApplication.getInstance()).saveNewMessage(chatName, "in");
                    MessageDBHelper.getInstance(MyApplication.getInstance()).saveMassage(item);
                    //发送广播
                    Intent intentM = new Intent();

                    Intent intent = new Intent();
                    int number = NewMessageDBHelper.getInstance(MyApplication.getInstance()).getMessageCount(chatName);
                    Dialogue dialogue = new Dialogue(username, username, number, 0, message.getBody());
                    ChatMessage chatMessage = new ChatMessage(item.username, item.msg, item.head, true);
                    intent.putExtra(Constants.RECEIVER_NEWDIALOGUE, dialogue);
                    intent.putExtra(Constants.RECEIVER_NEWMESSAGE, chatMessage);
                    intent.setAction(ActionUtil.NEWMESSAGE_ACTION);
                    LocalBroadcastManager.getInstance(MyApplication.getInstance()).sendBroadcast(intent);
                }
            } else if (message.getType() == Message.Type.groupchat && !message.getBody().equals("")) {

                String chatName = XmppConnection.getRoomName(message.getFrom());
                String username = XmppConnection.getRoomUserName(message.getFrom());
                if (!chatName.equals(Constants.USER_NAME)) {

                    String dataString;
                    DelayInformation information = message.getExtension("x", "jabber:x:delay");
                    if (information == null) {
                        dataString = DateUtil.now_MM_dd_HH_mm_ss();
                    } else {
                        dataString = DateUtil.dateToStr_MM_dd_HH_mm_ss(information.getStamp());
                    }
                    ChatItem item = new ChatItem(ChatItem.GROUP_CHAT, chatName, username, "", message.getBody(), dataString, 0);
                    NewMessageDBHelper.getInstance(MyApplication.getInstance()).saveNewMessage(chatName, "in");
                    MessageDBHelper.getInstance(MyApplication.getInstance()).saveMassage(item);
                    MyApplication.getInstance().sendBroadcast(new Intent(), "new message");
                }
            } else if (message.getType() == Message.Type.error) {
                System.out.println("消息格式错误");
            } else {
            }
        } else if (stanza instanceof Presence) {

            Presence presence = (Presence) stanza;
            if (Constants.IS_DEBUG) {
                Log.e("XMPPChat come", presence.toXML().toString());
            }
            String jid = presence.getFrom();
            String to = presence.getTo();
            if (presence.getType().equals(Presence.Type.subscribe)) {  //有人申请添加好友

                String username = XmppConnection.getUsername(jid);
                NewFriendsDBHelper.getInstance(MyApplication.getInstance()).saveNewFriend(username);
                NewMessageDBHelper.getInstance(MyApplication.getInstance()).saveNewMessage(username, "in");

                System.out.println("有人添加你为好友" + jid + "添加" + to);
            } else if (presence.getType().equals(Presence.Type.subscribed)) {  //您的请求已经被通过
                System.out.println(jid + "已经通过您的请求");
            } else if (presence.getType().equals(Presence.Type.unsubscribe)) {  //你拒绝了别人的请求
                System.out.println("你拒绝了" + jid + "的好友请求");
            } else if (presence.getType().equals(Presence.Type.unsubscribed)) {  //好友把你删除了
                System.out.println("你的请求被" + jid + "拒绝了");
            } else {
                System.out.println("消息格式错误");
            }
        } else if (stanza instanceof IQ) {

            IQ iq = (IQ) stanza;
        } else {
            System.out.println("消息格式错误");
        }
    }
}
