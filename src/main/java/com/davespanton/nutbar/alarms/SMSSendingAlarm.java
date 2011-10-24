package com.davespanton.nutbar.alarms;

import android.telephony.SmsManager;

public class SMSSendingAlarm {
	
	//TODO move to prefs
	private String destinationAddress = "+447871800784";
	private String bodyText = "Your thingy is on the move";
	
	private SmsManager smsManager = SmsManager.getDefault();
	
	public void tripAlarm() {
		smsManager.sendTextMessage(destinationAddress, null, bodyText, null, null);
	}

}
