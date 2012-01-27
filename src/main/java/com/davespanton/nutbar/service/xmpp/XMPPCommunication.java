package com.davespanton.nutbar.service.xmpp;

import android.util.Log;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class XMPPCommunication {

    public static final String XMPP_RECIPIENT = "ds-test-xmpp@appspot.com";

    @Inject
    private Provider<XMPPConnection> provider;

    public XMPPConnection xmppConn;

    private String username;
    private String password;

    public void connect(String username, String password) {
        this.username = username;
        this.password = password;

        xmppConn = provider.get();

        try {
            xmppConn.connect();
        } catch (XMPPException e) {
            Log.e("NBAR", "Error connecting to xmpp server.");
            return;
        }

        login();
    }

    private void login() {

        if(!xmppConn.isConnected())
            return;

        try {
            xmppConn.login(username, password);
        } catch (XMPPException e) {
            Log.e("NBAR", "Error logging in to xmpp server.");
        }
    }



    public void disconnect() {
        xmppConn.disconnect();
    }

    public void sendMessage(String message) {

        if(xmppConn == null || !xmppConn.isConnected()) {
            Log.w("NBAR", "Not connected to xmpp server");
            return;
        }

        Message xmppMessage = new Message(XMPP_RECIPIENT);
        xmppMessage.setBody(message);
        Log.v("NBAR", message);
        xmppConn.sendPacket(xmppMessage);
    }


    public boolean isConnected() {
        return xmppConn.isConnected();
    }
}
