package com.davespanton.nutbar.service.xmpp;

import com.xtremelabs.robolectric.Robolectric;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;

import java.util.ArrayList;

public class StubXMPPConnection extends XMPPConnection {

    private boolean isConnected = false;

    private String username = "";
    private String password = "";

    private String service;

    public StubXMPPConnection(String serviceName) {
        super(serviceName);
        service = serviceName;
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

    @Override
    public ChatManager getChatManager() {
        return super.getChatManager();
    }

    @Override
    public String getServiceName() {
        return service;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
