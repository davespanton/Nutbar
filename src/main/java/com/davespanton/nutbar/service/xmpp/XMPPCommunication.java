package com.davespanton.nutbar.service.xmpp;

import android.content.SharedPreferences;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPCommunication {

    @Inject
    private Provider<XMPPConnection> provider;

    @Inject
    SharedPreferences sharedPreferences;

    public XMPPConnection xmppConn;

    public void connect() {
        xmppConn = provider.get();

        try {
            xmppConn.connect();
        } catch (XMPPException e) {
            e.printStackTrace(); //TODO move outwards to enable reporting to user
        }

        login();
    }

    private void login() {
        String username = sharedPreferences.getString(NutbarPreferenceActivity.USERNAME_KEY, "");
        String password = sharedPreferences.getString(NutbarPreferenceActivity.PASSWORD_KEY, "");

        try {
            xmppConn.login(username, password);
        } catch (XMPPException e) {
            e.printStackTrace();  //TODO move outwards to enable reporting to user
        }
    }
}
