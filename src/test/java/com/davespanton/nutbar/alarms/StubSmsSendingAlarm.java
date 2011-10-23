package com.davespanton.nutbar.alarms;

public class StubSmsSendingAlarm extends SMSSendingAlarm {

	private int tripCount = 0;
	
	@Override
	public void tripAlarm() {
		tripCount++;
	}

	public int getTripCount() {
		return tripCount;
	}
}
