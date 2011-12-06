package com.davespanton.nutbar.service.xmpp;

import com.google.inject.Provider;
import org.jivesoftware.smack.XMPPConnection;

public class StubXMPPConnectionProvider implements Provider<XMPPConnection> {

    private static final XMPPConnection xmppConnection = new StubXMPPConnection(XMPPConnectionProvider.SERVER);

    public XMPPConnection get() {
        return xmppConnection;
    }
}
