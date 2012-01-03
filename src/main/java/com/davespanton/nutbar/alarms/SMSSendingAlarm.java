package com.davespanton.nutbar.alarms;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;

public class SMSSendingAlarm {

    private SharedPreferences sharedPreferences;

	private SmsManager smsManager = SmsManager.getDefault();

    private String bodyText;

    public SMSSendingAlarm(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        bodyText = context.getString(R.string.sms_alarm_body);
    }
	
	public void tripAlarm() {
        String destinationAddress = sharedPreferences.getString(NutbarPreferenceActivity.SMS_ALARM_KEY, "");

        if(!destinationAddress.isEmpty())
            smsManager.sendTextMessage(destinationAddress, null, bodyText, null, null);
	}
}
