package com.davespanton.nutbar.service.xmpp;

import android.os.AsyncTask;
import org.jivesoftware.smack.XMPPException;
import static com.davespanton.nutbar.logging.LogConfiguration.mog;

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
            mog.error("Error connecting to xmpp service " + e.getMessage());
            xmppCommunication.disconnect();
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
