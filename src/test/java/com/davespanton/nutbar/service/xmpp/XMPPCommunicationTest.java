package com.davespanton.nutbar.service.xmpp;

import android.content.SharedPreferences;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.davespanton.nutbar.shadows.ShadowChat;
import com.davespanton.nutbar.shadows.ShadowChatManager;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.xtremelabs.robolectric.Robolectric;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.*;

@RunWith(InjectedTestRunner.class)
public class XMPPCommunicationTest {

    private static final String USERNAME = "some.user";
    private static final String PASSWORD = "password";

    private static final String TEST_MESSAGE = "message";

    @Inject
    private XMPPCommunication xmppCommunication;

    @Inject
    private Provider<XMPPConnection> provider;

    private StubXMPPConnection xmppConnection;

    @Inject
    private SharedPreferences preferences;

    @Before
    public void setup() throws XMPPException {
        xmppConnection = (StubXMPPConnection) provider.get();
        xmppConnection.disconnect();
        setupSharedPreferencesCredentials();
        xmppCommunication.connect();
    }

    @After
    public void tearDown() {
        xmppConnection.setShouldFail(false);
    }

    private void setupSharedPreferencesCredentials() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NutbarPreferenceActivity.USERNAME_KEY, USERNAME);
        editor.putString(NutbarPreferenceActivity.PASSWORD_KEY, PASSWORD);
        editor.commit();
    }
    
    @Test
    public void shouldConnectToXmppServer() {
        assertTrue(xmppConnection.isConnected());
    }

    @Test
    public void shouldLoginOnConnectionWithSharedPreferenceCredentials() {

        assertEquals(USERNAME, xmppConnection.getUsername());
        assertEquals(PASSWORD, xmppConnection.getPassword());
    }

    @Test
    public void shouldDisconnectFromXMPPConnection() {
        xmppCommunication.disconnect();
        assertFalse(xmppConnection.isConnected());
    }

    @Test
    public void shouldInitiateChatOnLogin() {
        assertTrue(getShadowChatManager().hasCreatedChatForRecipient(XMPPCommunication.XMPP_RECIPIENT));
    }

    private ShadowChatManager getShadowChatManager() {
        return Robolectric.shadowOf_(xmppConnection.getChatManager());
    }

    @Test
    public void shouldSendAMessageWhenConnected() {
        xmppCommunication.sendMessage(TEST_MESSAGE);

        assertTrue(getShadowChatForXmppRecipient().hasSentMessage(TEST_MESSAGE));
    }

    private ShadowChat getShadowChatForXmppRecipient() {
        Chat chat = getShadowChatManager().getChatForRecipient(XMPPCommunication.XMPP_RECIPIENT);
        return Robolectric.shadowOf_(chat);
    }

    @Test
    public void shouldNotSendAMessageWhenNotConnected() {
        xmppCommunication.disconnect();
        xmppCommunication.sendMessage(TEST_MESSAGE);

        assertFalse(getShadowChatForXmppRecipient().hasSentMessage(TEST_MESSAGE));
    }

    @Test
    public void shouldReturnIsConnectedWhenConnected() throws XMPPException {
        xmppConnection.connect();
        assertTrue(xmppCommunication.isConnected());
    }
}
