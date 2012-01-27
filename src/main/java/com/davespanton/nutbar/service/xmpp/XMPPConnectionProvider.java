package com.davespanton.nutbar.service.xmpp;

import com.google.inject.Provider;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

public class XMPPConnectionProvider implements Provider<XMPPConnection> {

    public static final String SERVER = "jabber.org";

    private static final ConnectionConfiguration config = new ConnectionConfiguration(SERVER, 5222);

    @Override
    public XMPPConnection get() {
        return new XMPPConnection(config);
    }
}
