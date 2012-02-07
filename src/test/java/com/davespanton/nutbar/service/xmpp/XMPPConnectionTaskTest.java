package com.davespanton.nutbar.service.xmpp;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class XMPPConnectionTaskTest {

    @Inject
    XMPPCommunication xmppCommunication;

    private XMPPConnectionTask xmppConnectionTask;

    @Before
    public void setup() {
        xmppConnectionTask = new XMPPConnectionTask(Robolectric.getShadowApplication().getApplicationContext(), xmppCommunication);
    }

    @Test
    public void shouldAttemptToConnectWhenExecuted() {
        xmppConnectionTask.execute();
        assertTrue(xmppCommunication.isConnected());
    }
}
