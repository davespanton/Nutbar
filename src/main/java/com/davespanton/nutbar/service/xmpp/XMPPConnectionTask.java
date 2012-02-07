package com.davespanton.nutbar.service.xmpp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;

public class XMPPConnectionTask extends AsyncTask<XMPPCommunication, Void, Boolean> {

    private String username;
    private String password;

    private Context context;
    private XMPPCommunication xmppCommunication;

    public XMPPConnectionTask(Context context, XMPPCommunication communication) {
        super();
        this.context = context;
        xmppCommunication = communication;
    }

    @Override
    protected Boolean doInBackground(XMPPCommunication... xmppCommunications) {
        retrieveCredentials();
        xmppCommunication.connect(username, password);
        return false;
    }

    private void retrieveCredentials() {
        SharedPreferences prefs = context.getSharedPreferences("com.davespanton.nutbar_preferences", Context.MODE_PRIVATE);
        username = prefs.getString(NutbarPreferenceActivity.USERNAME_KEY, "");
        password = prefs.getString(NutbarPreferenceActivity.PASSWORD_KEY, "");
    }

    @Override
    protected void onPostExecute(Boolean result) {

    }
}
