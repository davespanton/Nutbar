package com.davespanton.nutbar.service.xmpp;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Stack;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(InjectedTestRunner.class)
public class XMPPReconnectionHandlerTest {

    private static final int ONE_TENTH_SECOND = 100;
    private static final int ONE_SECOND = 1000; 

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
        xmppReconnectionHandler.reconnectAfter(xmppCommunication, ONE_TENTH_SECOND);
        ShadowHandler.runMainLooperOneTask();

        assertTrue(xmppCommunication.isConnected());
    }

    @Test
    public void shouldNotAttemptConnectionBeforeDelay() {
        xmppReconnectionHandler.reconnectAfter(xmppCommunication, ONE_SECOND);
        Robolectric.idleMainLooper(ONE_TENTH_SECOND);

        assertFalse(xmppCommunication.isConnected());
    }

    @Test
    public void shouldCancelPendingDelayedConnection() {
        xmppReconnectionHandler.reconnectAfter(xmppCommunication, ONE_SECOND);
        Robolectric.idleMainLooper(ONE_TENTH_SECOND);

        xmppReconnectionHandler.cancelPendingReconnections();

        Robolectric.idleMainLooper(ONE_SECOND);

        assertFalse(xmppCommunication.isConnected());
    }

    @Test
    public void shouldIndicateIfHasPendingConnection() {
        xmppReconnectionHandler.reconnectAfter(xmppCommunication, 0);
        Robolectric.idleMainLooper(ONE_TENTH_SECOND);

        assertTrue(xmppReconnectionHandler.hasPendingConnections());
    }

    @Test
    public void shouldIndicateIfHasNoPendingConnections() {
        assertFalse(xmppReconnectionHandler.hasPendingConnections());
    }
}
