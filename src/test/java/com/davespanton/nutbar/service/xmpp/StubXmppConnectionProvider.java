package com.davespanton.nutbar.service.xmpp;

import com.google.inject.Provider;
import org.jivesoftware.smack.XMPPConnection;

public class StubXmppConnectionProvider implements Provider<XMPPConnection> {

    public XMPPConnection get() {
        return new StubXMPPConnection(XmppConnectionProvider.SERVER);
    }
}
