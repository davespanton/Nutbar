package com.davespanton.nutbar.service;

import roboguice.service.RoboService;
import android.content.Intent;
import android.os.IBinder;

import com.davespanton.nutbar.R;
import com.davespanton.nutbar.alarms.SMSSendingAlarm;
import com.google.inject.Inject;

public class AlarmService extends RoboService {

	@Inject
	private SMSSendingAlarm smsAlarm;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent.getAction().equals(getString(R.string.alarm_service_trip)))
			smsAlarm.tripAlarm();
		
		return START_STICKY;
	}

}
