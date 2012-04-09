package com.davespanton.nutbar.alarms;

import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.google.inject.Inject;
import com.google.inject.assistedinject.AssistedInject;
import roboguice.inject.InjectResource;
import com.google.inject.assistedinject.Assisted;

public class SMSSendingAlarm {

    private SharedPreferences sharedPreferences;

    private Context context;

	private SmsManager smsManager = SmsManager.getDefault();

    @InjectResource(R.string.sms_alarm_body)
    private String bodyText;

    @AssistedInject
    public SMSSendingAlarm(@Assisted Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void tripAlarm(PendingIntent pendingIntent) {
        String destinationAddress = sharedPreferences.getString(NutbarPreferenceActivity.SMS_ALARM_KEY, "");
        
        if(!destinationAddress.isEmpty())
            smsManager.sendTextMessage(destinationAddress, null, bodyText, pendingIntent, null);
	}

    //TODO - set failure broadcast receiver? or get a context and register own receiver?
}
