package com.davespanton.nutbar.alarms;

import android.telephony.SmsManager;

public class SMSSendingAlarm {

	private static final String destinationAddress = "";
	private static final String bodyText = "Your thingy is on the move";
	
	private SmsManager smsManager = SmsManager.getDefault();
	
	public void tripAlarm() {
		smsManager.sendTextMessage(destinationAddress, null, bodyText, null, null);
	}

}
