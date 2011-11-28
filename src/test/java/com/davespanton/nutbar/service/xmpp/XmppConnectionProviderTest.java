package com.davespanton.nutbar.service.xmpp;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import junit.framework.Assert;
import org.jivesoftware.smack.XMPPConnection;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class XmppConnectionProviderTest {

    @Inject
    private XmppConnectionProvider provider;

    @Test
    public void shouldReturnXmppConnection() {
        Assert.assertNotNull(provider.get());
    }

    @Test
    public void shouldReturnXmppConnectionWithExpectedServer() {
        XMPPConnection conn = provider.get();
        Assert.assertEquals(XmppConnectionProvider.SERVER, conn.getServiceName());
    }
}
