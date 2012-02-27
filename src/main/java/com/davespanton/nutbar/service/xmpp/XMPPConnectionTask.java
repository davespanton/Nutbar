package com.davespanton.nutbar.service.xmpp;

import android.os.AsyncTask;

public class XMPPConnectionTask extends AsyncTask<XMPPCommunication, Void, Boolean> {

    private XMPPCommunication xmppCommunication;

    public XMPPConnectionTask(XMPPCommunication communication) {
        super();
        xmppCommunication = communication;
    }

    @Override
    protected Boolean doInBackground(XMPPCommunication... xmppCommunications) {
        xmppCommunication.connect();
        return xmppCommunication.isConnected();
    }

    @Override
    protected void onPostExecute(Boolean result) {

    }
}
