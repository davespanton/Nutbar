package com.davespanton.nutbar.service.xmpp;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(InjectedTestRunner.class)
public class XMPPReconnectionHandlerTest {

    private XMPPReconnectionHandler xmppReconnectionHandler;

    @Inject
    XMPPCommunication xmppCommunication;

    @Before
    public void setup() {
        xmppCommunication.disconnect();
        xmppReconnectionHandler = new XMPPReconnectionHandler();
        Robolectric.pauseMainLooper();
    }

    @After
    public void tearDown() {
        xmppReconnectionHandler = null;
        Robolectric.unPauseMainLooper();
    }

    @Test
    public void shouldAttemptConnectionAfterDelay() {
        xmppReconnectionHandler.reconnectAfter(xmppCommunication, 100);
        ShadowHandler.runMainLooperOneTask();

        assertTrue(xmppCommunication.isConnected());
    }

    @Test
    public void shouldNotAttemptConnectionBeforeDelay() {
        xmppReconnectionHandler.reconnectAfter(xmppCommunication, 1000);
        Robolectric.idleMainLooper(100);

        assertFalse(xmppCommunication.isConnected());
    }
}
