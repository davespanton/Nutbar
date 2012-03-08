package com.davespanton.nutbar.service.xmpp;

import android.os.AsyncTask;
import android.util.Log;
import org.jivesoftware.smack.XMPPException;

public class XMPPConnectionTask extends AsyncTask<XMPPCommunication, Void, Boolean> {

    private XMPPCommunication xmppCommunication;

    private XMPPConnectionFailureCallback failureCallback;

    public XMPPConnectionTask(XMPPCommunication communication) {
        super();
        xmppCommunication = communication;
    }

    @Override
    protected Boolean doInBackground(XMPPCommunication... xmppCommunications) {
        //TODO - handle errors here
        try {
            xmppCommunication.connect();
        } catch (XMPPException e) {
            Log.e("NBAR", e.getMessage());
            return false;
        }
        return xmppCommunication.isConnected();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(!result && failureCallback != null)
            failureCallback.connectionFailed(xmppCommunication);
    }

    public void setFailureCallback(XMPPConnectionFailureCallback xmppConnectionFailureCallback) {
        failureCallback = xmppConnectionFailureCallback;
    }
}
