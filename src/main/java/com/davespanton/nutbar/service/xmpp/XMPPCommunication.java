package com.davespanton.nutbar.service.xmpp;

import android.util.Log;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

public class XMPPCommunication {

    public static final String XMPP_RECIPIENT = "ds-test-xmpp@appspot.com";

    @Inject
    private Provider<XMPPConnection> provider;

    public XMPPConnection xmppConn;

    private Chat chatSession;

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
            return;
        }

        createChat();
    }

    private void createChat() {

        ChatManager chatManager = xmppConn.getChatManager();
        chatSession = chatManager.createChat(XMPP_RECIPIENT, messageListener);
    }

    private MessageListener messageListener = new MessageListener() {
        @Override
        public void processMessage(Chat chat, Message message) {
            Log.v("NBAR", "Received message: " + message.getBody());
        }
    };

    public void disconnect() {
        xmppConn.disconnect();
    }

    public void sendMessage(String message) {

        if(xmppConn == null || !xmppConn.isConnected()) {
            Log.w("NBAR", "Not connected to xmpp server");
            return;
        }

        try {
            chatSession.sendMessage(message);
        } catch (XMPPException e) {
            Log.e("NBAR", "Couldn't send message.");
        }
    }


    public boolean isConnected() {
        return xmppConn.isConnected();
    }
}
