package com.davespanton.nutbar.service.xmpp;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class StubXMPPConnection extends XMPPConnection {

    private boolean isConnected = false;

    public StubXMPPConnection(String serviceName) {
        super(serviceName);
    }

    @Override
    public void connect() throws XMPPException {
        isConnected = true;
    }
}
