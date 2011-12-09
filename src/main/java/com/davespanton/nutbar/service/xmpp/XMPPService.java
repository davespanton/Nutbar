package com.davespanton.nutbar.service.xmpp;

import android.content.Intent;
import android.os.IBinder;
import com.davespanton.nutbar.R;
import com.google.inject.Inject;
import roboguice.service.RoboService;

public class XMPPService extends RoboService {

    @Inject
    private XMPPCommunication xmppCommunication;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction().equals(getString(R.string.start_xmpp_service)))
            connectToXmppServer();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        xmppCommunication.disconnect();
    }

    private void connectToXmppServer() {
        xmppCommunication.connect();
    }
}
