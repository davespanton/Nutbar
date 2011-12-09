package com.davespanton.nutbar.service.xmpp;

import android.content.Intent;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.google.inject.Provider;
import junit.framework.Assert;
import org.jivesoftware.smack.XMPPConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class XMPPServiceTest {

    @Inject
    private XMPPService xmppService;

    @Inject
    private Provider<XMPPConnection> xmppConnectionProvider;

    private XMPPConnection xmppConnection;

    @Before
    public void setup() {
        xmppService.onCreate();
        xmppConnection = xmppConnectionProvider.get();
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

        Assert.assertTrue(xmppConnection.isConnected());
    }

    private void startService() {
        Intent intent = new Intent(xmppService.getString(R.string.start_xmpp_service));
        xmppService.onStartCommand(intent, 0, 0);
    }

    @Test
    public void shouldDisconnectXMPPOnDestroy() {
        startService();
        xmppService.onDestroy();

        Assert.assertFalse(xmppConnection.isConnected());
    }
}
