package com.davespanton.nutbar.alarms;

import android.content.Context;

public class StubSmsSendingAlarm extends SMSSendingAlarm {

	private int tripCount = 0;

    public StubSmsSendingAlarm(Context context) {
        super(context);
    }

    @Override
	public void tripAlarm() {
		tripCount++;
	}

	public int getTripCount() {
		return tripCount;
	}
}
