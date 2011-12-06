package com.davespanton.nutbar.service.xmpp;

import android.content.SharedPreferences;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.h2.engine.User;
import org.jivesoftware.smack.XMPPConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(InjectedTestRunner.class)
public class XMPPCommunicationTest {

    private static final String USERNAME = "some.user";
    private static final String PASSWORD = "password";

    @Inject
    private XMPPCommunication xmppCommunication;

    @Inject
    private SharedPreferences sharedPreferences;

    @Inject
    private Provider<XMPPConnection> provider;

    private StubXMPPConnection xmppConnection;

    @Before
    public void setup() {
        xmppConnection = (StubXMPPConnection) provider.get();
    }

    @Test
    public void shouldConnectToXmppServer() {
        xmppCommunication.connect();
        assertTrue(xmppConnection.isConnected());
    }

    @Test
    public void shouldLoginOnConnectionWhenCredentialsAreFound() {
        setupSharedPreferencesWithLoginInfo();
        xmppCommunication.connect();
        assertEquals(USERNAME, xmppConnection.getUsername());
        assertEquals(PASSWORD, xmppConnection.getPassword());
    }

    private void setupSharedPreferencesWithLoginInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NutbarPreferenceActivity.USERNAME_KEY, USERNAME);
        editor.putString(NutbarPreferenceActivity.PASSWORD_KEY, PASSWORD);
        editor.commit();
    }


}
