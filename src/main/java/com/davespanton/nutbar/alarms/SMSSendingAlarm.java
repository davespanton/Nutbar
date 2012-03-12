package com.davespanton.nutbar.alarms;

import android.content.SharedPreferences;
import android.telephony.SmsManager;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.google.inject.Inject;
import roboguice.inject.InjectResource;

public class SMSSendingAlarm {

    @Inject
    private SharedPreferences sharedPreferences;

	private SmsManager smsManager = SmsManager.getDefault();

    @InjectResource(R.string.sms_alarm_body)
    private String bodyText;

	public void tripAlarm() {
        String destinationAddress = sharedPreferences.getString(NutbarPreferenceActivity.SMS_ALARM_KEY, "");

        if(!destinationAddress.isEmpty())
            smsManager.sendTextMessage(destinationAddress, null, bodyText, null, null);
	}
}
