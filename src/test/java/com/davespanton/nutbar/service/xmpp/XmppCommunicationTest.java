package com.davespanton.nutbar.service.xmpp;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class XmppCommunicationTest {

    @Inject
    private XmppCommunication xmppCommunication;
    
    @Test
    public void shouldConnectToXmppServer() {
        xmppCommunication.connect();
    }
}
