package com.arhat.chattest.xmpp;

import android.util.Log;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.model.Friend;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.packet.RosterPacket;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

/**
 * Created by Arhat on 2016/8/31.
 *
 */
public class XmppConnection {

    private static XMPPTCPConnection connection;
    private static XmppConnection xmppConnection;

    /**
     * 单例模式
     */
    public static XmppConnection getIntence() {

        if(xmppConnection == null) {
            xmppConnection = new XmppConnection();
        }
        return  xmppConnection;
    }

    /**
     * 初始化设置connection
     */
    public void  setNULL() {

        connection = null;
    }

    /**
     * 建立连接的方法
     */
    public boolean openConnection() {

        XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();
        config.setHost(Constants.SERVER_HOST);
        config.setServiceName(Constants.SERVER_NAME);
        config.setPort(Constants.SERVER_PORT);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        config.setSendPresence(true);   //
        config.setDebuggerEnabled(false); //是否启用调试
        connection = new XMPPTCPConnection(config.build());
        try {
            if(connection != null) {
                connection.disconnect();
            }
            connection.connect();

            //增加监听器
            XmppConnectionListener xmppConnectionListener = new XmppConnectionListener();
            connection.addConnectionListener(xmppConnectionListener);
            System.out.println("连接成功");
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("连接失败");
        }
        return false;
    }

    /**
     * 创建连接
     */
    public XMPPConnection getConnection() {

        if(connection == null) {
            openConnection();
        }
        return connection;
    }
    /**
     * 退出连接的方法
     */
    public void closeConnection() {

        if(connection != null) {

            try {
                connection.disconnect();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                connection = null;
                xmppConnection = null;
            }
        }
    }

    /**
     * 重新建立的连接
     */
    public boolean reOpenConnection() {

        return false;
    }

    /**
     * 登录
     */
    public boolean login(String account,String password) {

        try {
            connection.login(account,password);
            Constants.USER_NAME=account;
            Constants.PWD=password;
            //更改在线状态
            Presence presence = new Presence(Presence.Type.available);
            connection.sendStanza(presence);
            //添加好友监听器和消息监听器
            XmppMessageListener listener = new XmppMessageListener();
            ChatManager chatManager = ChatManager.getInstanceFor(getConnection());
            chatManager.addChatListener(listener);
            Roster.getInstanceFor(XmppConnection.getIntence().getConnection()).addRosterListener(new XmppRosterListener());
           // connection.addAsyncStanzaListener(new XmppPresenceListener(),new StanzaTypeFilter(Message.class));
            connection.addAsyncStanzaListener(new XmppAllMessageListener(),new XmppStanzaFilter());
            System.out.println("登录成功");
            return true;
        }catch (Exception e)  {
            System.out.println("登录失败");
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 注册账号
     */
    public static boolean register(String account, String password) {

        if (XmppConnection.getIntence().getConnection() == null){
            return  false;
        }
        AccountManager accountManager = AccountManager.getInstance(XmppConnection.getIntence().getConnection());
        try {
            accountManager.createAccount(account, password);
            Log.i("register","注册成功");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("register", "注册失败");
            return false;
        }
    }


    /**
     * 通过jid获得username
     */
    public static String getUsername(String fullUsername) {

        return fullUsername.split("@")[0];
    }

    /**
     * 通username获得jid
     */
    public static String getFullUsername(String username){

        return username + "@" + Constants.SERVER_NAME;
    }

    /**
     * 通过roomid获得jid
     */
    public static String getFullRoomname(String roomName){
        return roomName + "@conference."+ Constants.SERVER_NAME;
    }

    /**
     * 获取room的名称
     */
    public static String getRoomName(String fullRoomname) {
        return fullRoomname.split("@")[0];
    }

    /**
     * 通过roomjid获取发送者
     * @param fullRoomname
     * @return
     */
    public static String getRoomUserName(String fullRoomname) {
        return fullRoomname.split("/")[1];
    }

    public void changeFriend(Friend friend, RosterPacket.ItemType type){

        FriendsManager manager = new FriendsManager();
        manager.getFriends().get(manager.getFriends().indexOf(friend)).setType(type);
    }

    /**
     * 注销登录
     */
    public void disConnection() {

    }
}
