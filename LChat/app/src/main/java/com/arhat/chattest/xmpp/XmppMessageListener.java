package com.arhat.chattest.xmpp;

import android.content.Intent;
import android.util.Log;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.constants.MyApplication;
import com.arhat.chattest.model.ChatItem;
import com.arhat.chattest.sqllit.MessageDBHelper;
import com.arhat.chattest.sqllit.NewMessageDBHelper;
import com.arhat.chattest.util.DateUtil;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.delay.packet.DelayInformation;

/**
 * Created by Arhat on 2016/9/4.
 */
public class XmppMessageListener implements ChatManagerListener {

    @Override
    public void chatCreated(Chat chat, boolean b) {
        chat.addMessageListener(new ChatMessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {

                if(Constants.IS_DEBUG) {
                    Log.e("XMPPCHAT COME",message.toXML().toString());
                }
                if(message.toXML().toString().contains("<invite")) {

                    String notice = "你被邀请加入群组"+XmppConnection.getRoomName(message.getFrom());
                    System.out.println(notice);
                    String userName = XmppConnection.getRoomName(message.getFrom());
                    ChatItem chatItem = new ChatItem(ChatItem.GROUP_CHAT,userName,userName,"",notice, DateUtil.now_MM_dd_HH_mm_ss(),0);
                    NewMessageDBHelper.getInstance(MyApplication.getInstance()).saveNewMessage(userName,"in");
                    MessageDBHelper.getInstance(MyApplication.getInstance()).saveMassage(chatItem);
                    MyApplication.getInstance().sendBroadcast(new Intent("CHAT NEW MESSAGE"));
                }

                Message.Type type = message.getType();
                System.out.println(message.getFrom()+"发来了消息");
                System.out.println("这条消息的内容是"+message.getBody()+"类型是"+message.getType());

                //如果返回的消息为空，就重新设置一下他的body
                if(message.getBody()==null) {
                    message.setBody("");
                }
                if((type == Message.Type.groupchat || type == Message.Type.chat)  && !message.getBody().equals("")) {

                    String chatName = "";
                    String userName= "";
                    int chatType = ChatItem.CHAT;

                        //name
                        if(type == Message.Type.groupchat) {

                        chatName = XmppConnection.getRoomName(message.getFrom());
                        userName = XmppConnection.getRoomUserName(message.getFrom());
                        chatType = ChatItem.GROUP_CHAT;
                    }else {
                        chatName = userName = XmppConnection.getUsername(message.getFrom());
                        System.out.println("对方的发送消息的人是"+chatName);
                    }
                    if(!userName.equals(Constants.USER_NAME)) {

                        String dataString;
                        DelayInformation information = (DelayInformation)message.getExtension("x","jabber:x:delay");
                        if(information == null) {
                            dataString = DateUtil.now_MM_dd_HH_mm_ss();
                        }else {
                            dataString = DateUtil.dateToStr_MM_dd_HH_mm_ss(information.getStamp());
                        }

                        ChatItem chatItem = null;
                        String messageBody = null;
                        if(message.getSubject("imgData") != null) {

                            //判断是否是图片和声音文件  如果是将他们保存
                            System.out.println("这是一个图片文件");
                        }
                        else if(message.getType() == Message.Type.groupchat && message.getBody().contains(":::")) {

                            String[] messageAndData = message.getBody().split(":::");
                            //如果为音频或者图片等文件，将他们保存到相应的路径中
                            System.out.println("这是一个音频文件");
                        }

                        else {
                            messageBody = message.getBody();
                            System.out.println("消息的内容是"+messageBody);
                        }

                        if(type == Message.Type.groupchat ) {

                            System.out.println("我已经离开了这个房间");
                        }
                        else if(message.getBody().contains("[RoomChange")) {

                            XmppConnection.getIntence().reOpenConnection();
                        }
                        else {
//                            chatItem = new ChatItem(chatType,chatName,userName,"",messageBody,dataString,0);
//                            NewMessageDBHelper.getInstance(MyApplication.getInstance()).saveNewMessage(chatName);
//                            MessageDBHelper.getInstance(MyApplication.getInstance()).saveMassage(chatItem);
//                            MyApplication.getInstance().sendBroadcast(new Intent("CHAT NEW MESSAGE"));
//                            System.out.println("消息的内容是"+messageBody);
                        }
                    }
                }
            }
        });
    }
}
