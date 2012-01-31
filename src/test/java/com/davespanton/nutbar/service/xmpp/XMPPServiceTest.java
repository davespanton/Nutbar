package com.davespanton.nutbar.service.xmpp;

import android.content.Intent;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.davespanton.nutbar.shadows.ShadowChat;
import com.davespanton.nutbar.shadows.ShadowChatManager;
import com.google.inject.Inject;
import com.google.inject.Provider;
import static junit.framework.Assert.*;

import com.xtremelabs.robolectric.Robolectric;
import org.jivesoftware.smack.XMPPConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class XMPPServiceTest {

    private static final String TEST_MESSAGE = "message";

    @Inject
    private XMPPService xmppService;

    @Inject
    private Provider<XMPPConnection> xmppConnectionProvider;

    private XMPPConnection xmppConnection;

    @Before
    public void setup() {
        xmppService.onCreate();
        xmppConnection = xmppConnectionProvider.get();
        getShadowChatManager().clearAllChats();
    }

    @After
    public void tearDown() {
        xmppService = null;
        xmppConnection = null;
        xmppConnectionProvider = null;
    }

    @Test
    public void shouldConnectXMPPOnStartWithCorrectIntent() {
        startService();
        assertTrue(xmppConnection.isConnected());
    }


    private void sendXmppMessageViaIntent(String message) {
        Intent intent = new Intent(xmppService.getString(R.string.send_xmpp));
        intent.putExtra(xmppService.getString(R.string.send_xmpp_extra), message);
        xmppService.onStartCommand(intent, 0, 0);
    }

    @Test
    public void shouldSendXMPPMessageWithCorrectIntent() {
        startService();

        sendXmppMessageViaIntent(TEST_MESSAGE);

        assertTrue(getShadowChatForXmppRecipient().hasSentMessage(TEST_MESSAGE));
    }

    private ShadowChat getShadowChatForXmppRecipient() {
        ShadowChatManager shadowChatManager = getShadowChatManager();
        return Robolectric.shadowOf_(shadowChatManager.getChatForRecipient(XMPPCommunication.XMPP_RECIPIENT));
    }

    private ShadowChatManager getShadowChatManager() {
        return Robolectric.shadowOf_(xmppConnection.getChatManager());
    }

    @Test
    public void shouldNotSendXmppMessageWithCorrectIntentIfNotConnected() {
        sendXmppMessageViaIntent("");
        assertFalse(getShadowChatManager().hasCreatedChatForRecipient(XMPPCommunication.XMPP_RECIPIENT  ));
    }

    private void startService() {
        Intent intent = new Intent(xmppService.getString(R.string.start_xmpp_service));
        xmppService.onStartCommand(intent, 0, 0);
    }

    @Test
    public void shouldDisconnectXMPPOnDestroy() {
        startService();
        xmppService.onDestroy();

        assertFalse(xmppConnection.isConnected());
    }
}
