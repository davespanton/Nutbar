package com.davespanton.nutbar.service.xmpp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
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
        else if(intent.getAction().equals(getString(R.string.send_xmpp)))
            sendXmppMessage(intent);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        xmppCommunication.disconnect();
    }

    private void sendXmppMessage(Intent intent) {
        String message = intent.getStringExtra(getString(R.string.send_xmpp_extra));
        xmppCommunication.sendMessage(message);
    }

    private void connectToXmppServer() {

        XMPPConnectionTask connectionTask = new XMPPConnectionTask();
        connectionTask.execute(xmppCommunication);
    }

    private class XMPPConnectionTask extends AsyncTask<XMPPCommunication, Void, Boolean> {

        private String username;
        private String password;

        @Override
        protected Boolean doInBackground(XMPPCommunication... xmppCommunications) {
            retrieveCredentials();
            xmppCommunication.connect(username, password);
            return false;
        }

        private void retrieveCredentials() {
        SharedPreferences prefs = getSharedPreferences("com.davespanton.nutbar_preferences", Context.MODE_PRIVATE);
        username = prefs.getString(NutbarPreferenceActivity.USERNAME_KEY, "");
        password = prefs.getString(NutbarPreferenceActivity.PASSWORD_KEY, "");
    }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO update connected status
        }
    }
}
