package com.davespanton.nutbar.service.xmpp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;
import com.davespanton.nutbar.R;
import com.google.inject.Inject;
import roboguice.service.RoboService;

public class XMPPService extends RoboService {

    @Inject
    private XMPPCommunication xmppCommunication;

    @Inject
    private XMPPReconnectionHandler xmppReconnectionHandler;

    private BroadcastReceiver networkStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("NBAR", "Connection to network changed");
            connectToXmppServer();
        }
    };
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction().equals(getString(R.string.start_xmpp_service)))
            connectToXmppServer();
        else if(intent.getAction().equals(getString(R.string.send_xmpp)))
            sendXmppMessage(intent);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        registerReceiver(networkStatusReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    @Override
    public void onDestroy() {
        Log.v("NBAR", "destroying xmpp");

        unregisterReceiver(networkStatusReceiver);
        destroyXmppReconnectionHandler();
        destroyXmppCommunication();
        super.onDestroy();
    }

    private void destroyXmppReconnectionHandler() {
        xmppReconnectionHandler.cancelPendingReconnections();
        xmppReconnectionHandler = null;
    }

    private void destroyXmppCommunication() {
        xmppCommunication.disconnect();
        xmppCommunication = null;
    }

    private void sendXmppMessage(Intent intent) {
        String message = intent.getStringExtra(getString(R.string.send_xmpp_extra));
        xmppCommunication.sendMessage(message);
    }

    private void connectToXmppServer() {

        if(xmppCommunication.isConnected())
            return;

        if(xmppReconnectionHandler.hasPendingConnections())
                return;

        xmppReconnectionHandler.reconnectAfter(xmppCommunication, 0);
    }
}
