package com.davespanton.nutbar.service.xmpp;

import android.content.SharedPreferences;
import android.util.Log;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
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

    @Inject
    private SharedPreferences preferences;

    public synchronized void connect() {
        xmppConn = provider.get();

        if(xmppConn.isConnected())
            return;

        try {
            xmppConn.connect();
        } catch (XMPPException e) {
            Log.e("NBAR", "Error connecting to xmpp server. " + e.getXMPPError().getMessage());
            return;
        } catch (Exception e) {
            Log.e("NBAR", "Error connecting to xmpp server " + e.getStackTrace()[0]);
        }

        login();
    }

    private void login() {

        if(!xmppConn.isConnected())
            return;

        Log.v("NBAR", "Logging in to XMPP server");

        try {
            xmppConn.login(
                    preferences.getString(NutbarPreferenceActivity.USERNAME_KEY, ""),
                    preferences.getString(NutbarPreferenceActivity.PASSWORD_KEY, ""));
        } catch (XMPPException e) {
            Log.e("NBAR", "Error logging in to xmpp server.");
            return;
        } catch (Exception e) {
            Log.e("NBAR", "Error logging in to xmpp server: " + e.getStackTrace()[0]);
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
        try {
            xmppConn.disconnect();
        } catch (Exception e) {
            Log.e("NBAR", "Error disconnecting from xmpp server: " + e.getStackTrace()[0]);
        }
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
        } catch (Exception e) {
            Log.e("NBAR", "Couldn't send message: " + e.getStackTrace()[0]);
        }
    }


    public boolean isConnected() {
        return xmppConn != null && xmppConn.isConnected();
    }
}
