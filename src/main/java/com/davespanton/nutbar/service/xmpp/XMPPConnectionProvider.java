package com.davespanton.nutbar.service.xmpp;

import com.google.inject.Provider;
import org.jivesoftware.smack.XMPPConnection;

public class XMPPConnectionProvider implements Provider<XMPPConnection> {

    public static final String SERVER = "talk.google.com";

    @Override
    public XMPPConnection get() {
        return new XMPPConnection(SERVER);
    }
}
