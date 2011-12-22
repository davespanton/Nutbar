package com.davespanton.nutbar.service.xmpp;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.*;

@RunWith(InjectedTestRunner.class)
public class XMPPCommunicationTest {

    private static final String USERNAME = "some.user";
    private static final String PASSWORD = "password";

    @Inject
    private XMPPCommunication xmppCommunication;

    @Inject
    private Provider<XMPPConnection> provider;

    private StubXMPPConnection xmppConnection;

    @Before
    public void setup() {
        xmppConnection = (StubXMPPConnection) provider.get();
        xmppConnection.clearSentPackets();
        xmppCommunication.connect(USERNAME, PASSWORD);
    }

    @Test
    public void shouldConnectToXmppServer() {
        assertTrue(xmppConnection.isConnected());
    }

    @Test
    public void shouldLoginOnConnectionWhenCredentialsAreFound() {
        assertEquals(USERNAME, xmppConnection.getUsername());
        assertEquals(PASSWORD, xmppConnection.getPassword());
    }

    @Test
    public void shouldDisconnectFromXMPPConnection() {
        xmppCommunication.disconnect();
        assertFalse(xmppConnection.isConnected());
    }

    @Test
    public void shouldSendAMessageWhenConnected() {
        String message = "something";
        xmppCommunication.sendMessage(message);
        Message sentMessage = (Message) xmppConnection.getLastSentPacket();
        assertEquals(message, sentMessage.getBody());
        assertEquals(XMPPCommunication.XMPP_RECIPIENT, sentMessage.getTo());
    }

    @Test
    public void shouldNotSendAMessageWhenNotConnected() {
        xmppCommunication.disconnect();
        xmppCommunication.sendMessage("message");
        Message sentMessage = (Message) xmppConnection.getLastSentPacket();
        assertNull(sentMessage);
    }
}
