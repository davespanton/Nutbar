package com.davespanton.nutbar.alarms;

import android.content.Context;

public class StubSmsSendingAlarm extends SMSSendingAlarm {

	private int tripCount = 0;

    @Override
	public void tripAlarm() {
		super.tripAlarm();
        tripCount++;
	}

	public int getTripCount() {
		return tripCount;
	}
}
