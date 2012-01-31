package org.jivesoftware.smack;

public class SmackBridge {

    public static Chat getChat(ChatManager chatManager, String participant, String threadId) {
        return new Chat(chatManager, participant, threadId);
    }
}
