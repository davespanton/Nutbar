package com.davespanton.nutbar.service;

import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
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
    public void onDestroy() {
        super.onDestroy();

        resetAlarms();
    }

    @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent.getAction().equals(getString(R.string.alarm_service_trip)))
			tripAlarms();
        else if(intent.getAction().equals(getString(R.string.alarm_service_reset)))
            resetAlarms();
		
		return START_STICKY;
	}

    private void tripAlarms() {
        smsAlarm.tripAlarm();
        locationAlarm.tripAlarm();
    }

    private void resetAlarms() {
        locationAlarm.resetAlarm();
    }

    private LocationAlarmListener locationAlarmListener = new LocationAlarmListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("NBAR", "Loc update " + location.getProvider());

        }
    };

}
