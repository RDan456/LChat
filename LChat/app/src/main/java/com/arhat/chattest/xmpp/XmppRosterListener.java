package com.arhat.chattest.xmpp;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.arhat.chattest.constants.ActionUtil;
import com.arhat.chattest.constants.MyApplication;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterListener;

import java.util.Collection;

/**
 * Created by Arhat on 2016/9/12.
 */
public class XmppRosterListener implements RosterListener {

    /**
     * 有好友申请时触发
     * @param collection
     */
    @Override
    public void entriesAdded(Collection<String> collection) {

        System.out.println("有人申请您作为他的好友");
    }

    /**
     * 当好友同意我的请求时调用
     */
    @Override
    public void entriesUpdated(Collection<String> collection) {

        System.out.println("好友已经通过了你的请求");
    }

    /**
     * 当我被好友删除时触发
     * @param collection
     */
    @Override
    public void entriesDeleted(Collection<String> collection) {

        System.out.println("你被好友删除了");
    }

    //监听好友在线状态的变化
    @Override
    public void presenceChanged(Presence presence) {

        String state = null;
        Intent intent = new Intent();
        intent.setAction(ActionUtil.PRESENTER_ACTION);
        if(presence.getType().toString().equals("available")) {
            state = "在线";
        }else if(presence.getType().toString().equals("unavailable")) {
            state="离线";
        }else {

        }
        intent.putExtra("username",XmppConnection.getUsername(presence.getFrom().toString()));
        intent.putExtra("textstate",state);
        LocalBroadcastManager.getInstance(MyApplication.getInstance()).sendBroadcast(intent);
    }
}
