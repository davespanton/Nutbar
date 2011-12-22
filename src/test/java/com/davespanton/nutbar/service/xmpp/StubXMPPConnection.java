package com.davespanton.nutbar.service.xmpp;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;

import java.util.ArrayList;

public class StubXMPPConnection extends XMPPConnection {

    private boolean isConnected = false;

    private String username = "";
    private String password = "";

    private ArrayList<Packet> sentPackets = new ArrayList<Packet>();

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

    @Override
    public void sendPacket(Packet packet) {
        sentPackets.add(packet);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public Packet getLastSentPacket() {
        if(sentPackets.size() == 0)
            return null;
        else
            return sentPackets.get(sentPackets.size() - 1);
    }

    public void clearSentPackets() {
        sentPackets.clear();
    }
}
