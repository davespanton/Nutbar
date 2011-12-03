package com.davespanton.nutbar.service.xmpp;


import com.google.inject.Inject;
import com.google.inject.Provider;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XmppCommunication {

    @Inject
    private Provider<XMPPConnection> provider;

    public XMPPConnection xmppConn;

    public void connect() {
        xmppConn = provider.get();

        try {
            xmppConn.connect();
        } catch (XMPPException e) {
            e.printStackTrace(); //TODO move outwards to enable reporting to user
        }
    }
}
