package com.davespanton.nutbar.service.xmpp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.google.inject.Inject;
import roboguice.service.RoboService;

public class XMPPService extends RoboService {

    @Inject
    private XMPPCommunication xmppCommunication;

    private XMPPConnectionTask xmppConnectionTask;
    
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
        xmppConnectionTask = new XMPPConnectionTask(this, xmppCommunication);
    }

    @Override
    public void onDestroy() {
        Log.v("NBAR", "destroying xmpp");
        super.onDestroy();
        xmppConnectionTask.cancel(true);
        xmppConnectionTask = null;
        xmppCommunication.disconnect();
        xmppCommunication = null;
    }

    private void sendXmppMessage(Intent intent) {
        String message = intent.getStringExtra(getString(R.string.send_xmpp_extra));
        xmppCommunication.sendMessage(message);
    }

    private void connectToXmppServer() {
        xmppConnectionTask.execute(xmppCommunication);
    }
}
