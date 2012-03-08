package com.davespanton.nutbar.service.xmpp;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import static junit.framework.Assert.*;

import com.google.inject.Provider;
import org.jivesoftware.smack.XMPPConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class XMPPConnectionTaskTest {

    @Inject
    XMPPCommunication xmppCommunication;

    private XMPPConnectionTask xmppConnectionTask;

    @Inject
    private Provider<XMPPConnection> xmppConnectionProvider;

    private boolean connectionFailureCalled = false;

    private XMPPConnectionFailureCallback xmppConnectionFailureCallback = new XMPPConnectionFailureCallback() {
        @Override
        public void connectionFailed(XMPPCommunication communication) {
            connectionFailureCalled = true;
        }
    };

    @Before
    public void setup() {
        xmppConnectionTask = new XMPPConnectionTask(xmppCommunication);
        connectionFailureCalled = false;
    }

    @After
    public void tearDown() {
        xmppCommunication.disconnect();
        ((StubXMPPConnection) xmppConnectionProvider.get()).setShouldFail(false);
    }

    @Test
    public void shouldAttemptToConnectWhenExecuted() {
        xmppConnectionTask.execute();
        assertTrue(xmppCommunication.isConnected());
    }

    @Test
    public void shouldInformCallbackOfFailure() {
        xmppConnectionTask.setFailureCallback(xmppConnectionFailureCallback);
        ((StubXMPPConnection) xmppConnectionProvider.get()).setShouldFail(true);

        xmppConnectionTask.execute();

        assertTrue(connectionFailureCalled);
    }

    @Test
    public void shouldNotInformIfNoCallbackIsSet() {
        ((StubXMPPConnection) xmppConnectionProvider.get()).setShouldFail(true);

        xmppConnectionTask.execute();

        assertFalse(connectionFailureCalled);
    }
}
