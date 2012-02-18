package com.davespanton.nutbar.service.xmpp;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.google.inject.Inject;
import roboguice.inject.InjectResource;

public class XMPPReconnectionHandler {

    @Inject
    private SharedPreferences preferences;

    private Handler.Callback handlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            XMPPCommunication xmppCommunication = (XMPPCommunication) message.obj;
            xmppCommunication.connect();
            return false;
        }
    };

    private Handler handler = new Handler(handlerCallback);

    public void reconnectAfter(XMPPCommunication xmppCommunication, int delay) {
        Message message = new Message();
        message.obj = xmppCommunication;
        handler.sendMessageDelayed(message, delay);
    }


}
