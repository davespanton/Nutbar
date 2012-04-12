package com.davespanton.nutbar.alarms;

import android.app.PendingIntent;
import android.content.*;
import android.telephony.SmsManager;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import roboguice.inject.InjectResource;

public class SMSSendingAlarm {

    @Inject
    private SharedPreferences sharedPreferences;

    private Context context;

	private SmsManager smsManager = SmsManager.getDefault();

    @InjectResource(R.string.sms_alarm_body)
    private String bodyText;

    @InjectResource(R.string.sms_failed)
    private String failedSMSAction;

    @AssistedInject
    public SMSSendingAlarm(@Assisted Context context) {
        this.context = context;
    }

    public void tripAlarm() {
        context.registerReceiver(receiver, new IntentFilter(failedSMSAction));

        String destinationAddress = sharedPreferences.getString(NutbarPreferenceActivity.SMS_ALARM_KEY, "");

        if(!destinationAddress.isEmpty())
            smsManager.sendTextMessage(destinationAddress, null, bodyText, getPendingIntent(), null);
	}

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(failedSMSAction);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            
        }
    };
}
