package com.davespanton.nutbar.alarms;

import android.app.PendingIntent;
import android.content.*;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.davespanton.nutbar.alarms.broadcastreceiver.ReTripReceiver;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import roboguice.inject.InjectResource;

public class SMSSendingAlarm implements Trippable{

    @Inject
    private SharedPreferences sharedPreferences;

    @Inject
    private ReTripReceiver receiver;

    private Context context;

	private SmsManager smsManager = SmsManager.getDefault();

    @InjectResource(R.string.sms_alarm_body)
    private String bodyText;

    @InjectResource(R.string.sms_failed)
    private String failedSMSAction;

    private boolean listening = false;
    private boolean registered;

    @AssistedInject
    public SMSSendingAlarm(@Assisted Context context) {
        this.context = context;
    }

    public void tripAlarm() {
        listening = true;

        receiver.setTrippable(this);
        registerReceiver();

        String destinationAddress = sharedPreferences.getString(NutbarPreferenceActivity.SMS_ALARM_KEY, "");

        if(!destinationAddress.isEmpty())
            smsManager.sendTextMessage(destinationAddress, null, bodyText, getPendingIntent(), null);
	}

    private void registerReceiver() {
        if(!registered)
        {
            context.registerReceiver(receiver, new IntentFilter(failedSMSAction));  
            registered = true;
        }
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(failedSMSAction);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public boolean isListening() {
        return listening;
    }

    public void resetAlarm() {
        unregisterReceiver();
        receiver.cancelPendingRetrip();
        
        listening = false;
    }

    private void unregisterReceiver() {
        if(registered)
        {
            context.unregisterReceiver(receiver);
            registered = false;
        }
    }
}