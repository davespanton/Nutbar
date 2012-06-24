package com.davespanton.nutbar.service.xmpp;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import com.google.inject.Inject;
import static com.davespanton.nutbar.logging.LogConfiguration.mog;

import java.util.Stack;

public class XMPPReconnectionHandler {

    public static final int RECONNECTION_MILLISECONDS = 10000;
    @Inject
    private SharedPreferences preferences;

    private Integer messageWhat = 0;
    private Stack<Integer> messageWhatStack = new Stack<Integer>();

    private Handler.Callback handlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            XMPPCommunication xmppCommunication = (XMPPCommunication) message.obj;
            XMPPConnectionTask task = new XMPPConnectionTask(xmppCommunication);
            task.setFailureCallback(failureCallback);
            task.execute();
            getLastMessageWhat(); //consume last message what
            return false;
        }
    };

    private XMPPConnectionFailureCallback failureCallback = new XMPPConnectionFailureCallback() {
        @Override
        public void connectionFailed(XMPPCommunication communication) {
            mog.debug("Queuing a reconnection attempt.");
            reconnectAfter(communication, RECONNECTION_MILLISECONDS);
        }
    };

    private Handler handler = new Handler(handlerCallback);

    public void reconnectAfter(XMPPCommunication xmppCommunication, int delay) {
        Message message = handler.obtainMessage(getNextMessageWhat(), xmppCommunication);
        handler.sendMessageDelayed(message, delay);
    }

    private int getNextMessageWhat() {
        return messageWhatStack.push(messageWhat++);
    }

    private int getLastMessageWhat() {
        messageWhat--;
        return messageWhatStack.pop();
    }

    public void cancelPendingReconnections() {
        if(messageWhatStack.size() > 0) {
            handler.removeMessages(getLastMessageWhat());
        }
    }

    public boolean hasPendingConnections() {
        return !messageWhatStack.empty();
    }
}
