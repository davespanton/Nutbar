package com.davespanton.nutbar.service.xmpp;

import android.util.Log;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPCommunication {

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
            Log.e("NBAR", "Error connecting :S");
        }

        login();
    }

    private void login() {

        if(!xmppConn.isConnected()) {
            return;
        }

        try {
            xmppConn.login(username, password);
        } catch (XMPPException e) {
            Log.e("NBAR", "Error logging in :S");
        }
    }

    public void disconnect() {
        xmppConn.disconnect();
    }
}
