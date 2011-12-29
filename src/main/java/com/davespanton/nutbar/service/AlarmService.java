package com.davespanton.nutbar.service;

import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.alarms.LocationAlarm;
import com.davespanton.nutbar.alarms.LocationAlarmListener;
import com.davespanton.nutbar.alarms.SMSSendingAlarm;
import com.google.inject.Inject;
import roboguice.service.RoboService;

public class AlarmService extends RoboService {

	@Inject
	private SMSSendingAlarm smsAlarm;

    @Inject
    private LocationAlarm locationAlarm;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

    @Override
    public void onCreate() {
        super.onCreate();

        locationAlarm.setOnLocationChangeListener(locationAlarmListener);
    }

    @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent.getAction().equals(getString(R.string.alarm_service_trip)))
			smsAlarm.tripAlarm();
		
		return START_STICKY;
	}

    private LocationAlarmListener locationAlarmListener = new LocationAlarmListener() {
        @Override
        public void onLocationChanged(Location location) {

        }
    };

}
