package com.davespanton.nutbar.shadows;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.internal.RealObject;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackBridge;

import java.util.HashMap;

@Implements(ChatManager.class)
public class ShadowChatManager {

    public HashMap<String, Chat> createdChatsByRecipient = new HashMap<String, Chat>();

    @RealObject
    private ChatManager chatManager;

    @Implementation
    private Chat createChat(String recipient, MessageListener listener) {
        Chat chat = SmackBridge.getChat(chatManager, recipient, "01");
           createdChatsByRecipient.put(recipient, chat);
        return chat;
    }

    public boolean hasCreatedChatForRecipient(String recipient) {
        return createdChatsByRecipient.containsKey(recipient);
    }

    public Chat getChatForRecipient(String recipient) {
        return createdChatsByRecipient.get(recipient);
    }

    public void clearAllChats() {
        createdChatsByRecipient.clear();
    }
}
