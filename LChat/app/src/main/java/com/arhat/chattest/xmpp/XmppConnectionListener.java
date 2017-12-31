package com.arhat.chattest.xmpp;

import android.util.Log;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

/**
 * Created by Arhat on 2016/9/4.
 */
public class XmppConnectionListener implements ConnectionListener {

    @Override
    public void connected(XMPPConnection xmppConnection) {

        Log.e("CONNECTION","CONNECTION SUCCESS");
    }

    @Override
    public void authenticated(XMPPConnection xmppConnection, boolean b) {

        Log.e("Connection","authenticated");
    }

    @Override
    public void connectionClosed() {

        Log.e("CONNECTION","CONNECTION FAIL");
    }

    @Override
    public void connectionClosedOnError(Exception e) {

        Log.e("CONNECTION","CONNECTION CLOSE ON ERROR");
    }

    @Override
    public void reconnectionSuccessful() {

        Log.e("CONNECTION","RECONNECTION SUCCESS");
    }

    @Override
    public void reconnectingIn(int i) {

        Log.e("CONNECTION","RECONNECTION IN");
    }

    @Override
    public void reconnectionFailed(Exception e) {

        Log.e("CONNECTION","RECONNECTION FAILED");

    }
}
