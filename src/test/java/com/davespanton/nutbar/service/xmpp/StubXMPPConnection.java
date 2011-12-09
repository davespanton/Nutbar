package com.davespanton.nutbar.service.xmpp;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class StubXMPPConnection extends XMPPConnection {

    private boolean isConnected = false;

    private String username = "";
    private String password = "";

    public StubXMPPConnection(String serviceName) {
        super(serviceName);
    }

    @Override
    public void connect() throws XMPPException {
        isConnected = true;
    }

    @Override
    public void disconnect() {
        isConnected = false;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void login(String username, String password) throws XMPPException {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
