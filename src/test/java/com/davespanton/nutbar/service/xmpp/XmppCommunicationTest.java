package com.davespanton.nutbar.service.xmpp;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.xtremelabs.robolectric.Robolectric.bindShadowClass;
import static com.xtremelabs.robolectric.Robolectric.shadowOf_;

@RunWith(InjectedTestRunner.class)
public class XmppCommunicationTest {

    @Inject
    private XmppCommunication xmppCommunication;

    @Test
    public void shouldConnectToXmppServer() {
        xmppCommunication.connect();
    }
}
