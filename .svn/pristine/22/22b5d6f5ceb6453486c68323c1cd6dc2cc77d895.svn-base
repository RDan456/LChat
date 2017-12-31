package com.arhat.chattest.xmpp;

import com.arhat.chattest.model.ChatItem;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;

/**
 * Created by Arhat on 2016/9/1.
 */
public class ChatWithFriend {

    //发送文本信息
    public static void sendMsg(String chatName,String messageText,int chatType) throws  Exception {

        if(XmppConnection.getIntence().getConnection() == null) {
            System.out.print("连接已经断开");
            throw new Exception("XmppException");
        }
        if(messageText.isEmpty() || messageText == " " || messageText==null) {

            System.out.println("您输入的内容为空");
        }else {
            if (chatType == ChatItem.CHAT) {

                //发给服务器所所在的好友
                Chat chat = ChatManager.getInstanceFor(XmppConnection.getIntence().getConnection())
                        .createChat(XmppConnection.getFullUsername(chatName),null);
                chat.sendMessage(messageText);
                chat.close();
            }else if(chatType == ChatItem.GROUP_CHAT) {
                //群聊
                MultiUserChat multiUserChat = MultiUserChatManager.getInstanceFor(XmppConnection.getIntence()
                        .getConnection()).getMultiUserChat(XmppConnection.getFullRoomname(chatName));
                multiUserChat.sendMessage(messageText);
            }
        }
    }
    //发送文本消息
    public static void sendMsg(String msg,int chatType) throws Exception{

        if (XmppConnection.getIntence().getConnection() == null){
            throw new Exception("XmppException");
        }
        if (msg.isEmpty()) {
            System.out.println("发送消息不能为空");
        }
        else {
            if (chatType == ChatItem.CHAT) {
                // newchat.sendMessage(msg);
            }
            else if (chatType == ChatItem.GROUP_CHAT) {
                // mulChat.sendMessage(msg);
            }
        }
    }

    //
}
