package com.arhat.chattest.xmpp;

import android.util.Log;

import com.arhat.chattest.constants.Constants;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.xdata.Form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Arhat on 2016/9/5.
 */
public class GroupChatManager {

    /**
     * 创建一个聊天室
     */
    public boolean createGroup(String roomName,String nickName,String password) {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败");
        }
        MultiUserChat multiUserChat = null;
        try {
            multiUserChat = MultiUserChatManager.getInstanceFor(XmppConnection.getIntence().getConnection())
                    .getMultiUserChat(roomName+"@conference."+ Constants.SERVER_NAME);
            multiUserChat.create(roomName);
            boolean isCreated = multiUserChat.createOrJoin(nickName);
            if(isCreated) {
                Form form = multiUserChat.getConfigurationForm();
                Form submitForm = form.createAnswerForm();

                List owers = new ArrayList();
                //把创建的人加入到这个会议室汇中
                owers.add(XmppConnection.getIntence().getConnection().getUser());
                submitForm.setAnswer("multiUserChat#roomconfig_roomowners",owers);
                //设置聊天室是持久聊天室，将要保存下来
                submitForm.setAnswer("multiUserChat#roomconfig_persistentroom",true);
                //这个聊天室只对群里边的成员开放
                submitForm.setAnswer("multiUserChat#roomconfig_membersonly",false);
                //设置群里的成员可以邀请其他新的成员
                submitForm.setAnswer("multiUserChat#roomconfig_allowinvites",true);
                //设置进入讨论组是否需要密码
                submitForm.setAnswer("multiUserChat#roomconfig_passwordprotectedroom",false);
                //设置进入讨论组的密码
                submitForm.setAnswer("multiUserChat#roomconfig_roomsecret",password);
                //设置能够发现占有者真实jid的角色
                submitForm.setAnswer("multiUserChat#roomconfig_whois","anyone");
                //设置描述
                submitForm.setAnswer("multiUserChat#roomconfig_roomdesc","mulchat");
                //登录房间对话
                submitForm.setAnswer("multiUserChat#roomconfig_enablelogging",true);
                //仅允许注册的昵称登录
                submitForm.setAnswer("x-multiUserChat#roomconfig_reservednick",false);
                //允许使用者修改昵称
                submitForm.setAnswer("x-multiUserChat#roomconfig_canchangenick",true);
                //允许用户注册房间
                submitForm.setAnswer("x-multiUserChat#roomconfig_registration",true);
                //发送已完成的调单到服务器来开启一个聊天室
                multiUserChat.sendConfigurationForm(submitForm);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 加入一个聊天室
     *
     * @param roomName
     * @param nickName
     * @param password
     * @return
     */
    public boolean joinChatRoom(String roomName,String nickName,String password) {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请先连接诶服务器");
        }
        try {
            MultiUserChat multiUserChat = MultiUserChatManager.getInstanceFor(XmppConnection.getIntence().getConnection())
                    .getMultiUserChat(roomName+"@conference."+Constants.SERVER_NAME);
            //聊天室服务将会决定要接受的历史记录数量
            DiscussionHistory history = new DiscussionHistory();
            history.setMaxChars(0);
            history.setSince(new Date());
            //用户加入聊天室
            multiUserChat.join(nickName,null,history, SmackConfiguration.getDefaultPacketReplyTimeout());
            if(Constants.IS_DEBUG) {
                Log.e("multiUserChat","成功加入会议室");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 退出群聊
     */
    public boolean leaveGroupChat(String rooName) {

        if(XmppConnection.getIntence().getConnection() == null) {
            throw new NullPointerException("服务器连接失败，请重新连接服务器");
        }
        MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(XmppConnection.getIntence().getConnection());
        MultiUserChat multiUserChat = multiUserChatManager.getMultiUserChat(rooName);
        try {
            multiUserChat.leave();
            return  true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
