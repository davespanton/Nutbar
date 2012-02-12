package com.davespanton.nutbar.service.xmpp;

import android.os.Handler;
import android.os.Message;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowHandler;
import junit.framework.Assert;
import org.jivesoftware.smack.XMPPConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class XMPPReconnectionHandlerTest {

    private XMPPReconnectionHandler xmppReconnectionHandler;

    private Handler handler;
    private ShadowHandler shadowHandler;

    @Inject
    XMPPCommunication xmppCommunication;

    @Before
    public void setup() {
        xmppReconnectionHandler = new XMPPReconnectionHandler();
        handler = new Handler();
        shadowHandler = Robolectric.shadowOf(handler);
    }

    @After
    public void tearDown() {
        xmppReconnectionHandler = null;
        handler = null;
        shadowHandler = null;
    }

    @Test
    public void shouldAttemptConnectionAfterDelay() {
        reconnectAfter(1000);

        // TODO implement hasMessages(int, Object) in shadow.
        Assert.assertTrue(handler.hasMessages(0, xmppCommunication));
    }

    private void reconnectAfter(int delay) {
        Message message = new Message();
        message.obj = xmppCommunication;
        handler.sendMessageDelayed(message, delay);
    }
}
