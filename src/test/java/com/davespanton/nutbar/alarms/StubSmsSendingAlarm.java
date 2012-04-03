package com.davespanton.nutbar.alarms;

import android.app.PendingIntent;
import android.content.Context;
import com.google.inject.assistedinject.Assisted;
import com.xtremelabs.robolectric.Robolectric;

public class StubSmsSendingAlarm extends SMSSendingAlarm {

	private int tripCount = 0;

    public StubSmsSendingAlarm() {
        this(Robolectric.application.getApplicationContext());
    }

    public StubSmsSendingAlarm(@Assisted Context context) {
        super(context);
    }

    @Override
	public void tripAlarm(PendingIntent pendingIntent) {
		super.tripAlarm(pendingIntent);
        tripCount++;
	}

	public int getTripCount() {
		return tripCount;
	}
}
