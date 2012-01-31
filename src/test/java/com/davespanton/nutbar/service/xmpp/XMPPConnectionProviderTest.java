package com.davespanton.nutbar.service.xmpp;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.google.inject.Provider;
import junit.framework.Assert;
import org.jivesoftware.smack.XMPPConnection;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class XMPPConnectionProviderTest {

    @Inject
    private Provider<XMPPConnection> provider;

    @Test
    public void shouldReturnXmppConnection() {
        Assert.assertNotNull(provider.get());
    }

    @Test
    public void shouldReturnXmppConnectionWithExpectedServer() {
        XMPPConnection conn = provider.get();
        Assert.assertEquals(XMPPConnectionProvider.SERVER, conn.getServiceName());
    }
}
