package org.jivesoftware.smack;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.internal.RealObject;

@Implements(ChatManager.class)
public class ShadowChatManager {

    @RealObject
    private ChatManager chatManager;

    @Implementation
    private Chat createChat(String recipient, MessageListener listener) {
        return new Chat(chatManager, recipient, "01");
    }
}
