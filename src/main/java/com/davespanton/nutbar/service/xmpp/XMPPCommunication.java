package com.davespanton.nutbar.service.xmpp;

import android.content.SharedPreferences;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import static com.davespanton.nutbar.logging.LogConfiguration.mog;

public class XMPPCommunication {

    public static final String XMPP_RECIPIENT = "ds-test-xmpp@appspot.com";

    @Inject
    private Provider<XMPPConnection> provider;

    public XMPPConnection xmppConn;

    private Chat chatSession;

    @Inject
    private SharedPreferences preferences;

    public synchronized void connect() throws XMPPException {
        xmppConn = provider.get();

        if(xmppConn.isConnected())
            return;

        // null point exception: can occur in XMPPConnection.initConnection line 604
        try {
            xmppConn.connect();
            login();
        } catch(NullPointerException exception) {
            mog.error("NullPointer in XMPPConnection.");
            xmppConn.disconnect();
        }
    }

    private void login() throws XMPPException {

        if(!xmppConn.isConnected())
            return;

        mog.debug("Logging in to XMPP server");

        xmppConn.login(
            preferences.getString(NutbarPreferenceActivity.USERNAME_KEY, ""),
            preferences.getString(NutbarPreferenceActivity.PASSWORD_KEY, ""));

        createChat();
    }

    private void createChat() {

        ChatManager chatManager = xmppConn.getChatManager();
        chatSession = chatManager.createChat(XMPP_RECIPIENT, messageListener);
    }

    private MessageListener messageListener = new MessageListener() {
        @Override
        public void processMessage(Chat chat, Message message) {
            mog.debug("Received message: " + message.getBody());
        }
    };

    public void disconnect() {
        try {
            xmppConn.disconnect();
        } catch (Exception e) {
            mog.error("Error disconnecting from xmpp server: " + e.getStackTrace()[0]);
        }
    }

    public void sendMessage(String message) {

        if(xmppConn == null || !xmppConn.isConnected()) {
            mog.warn("Not connected to xmpp server");
            return;
        }

        try {
            chatSession.sendMessage(message);
        } catch (XMPPException e) {
            mog.error("Couldn't send message.");
        } catch (Exception e) {
            mog.error("Couldn't send message: " + e.getStackTrace()[0]);
        }
    }


    public boolean isConnected() {
        return xmppConn != null && xmppConn.isConnected();
    }
}
