package com.davespanton.nutbar.service;

import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.alarms.LocationAlarm;
import com.davespanton.nutbar.alarms.SMSSendingAlarm;
import com.davespanton.nutbar.alarms.factory.SMSSendingAlarmFactory;
import com.davespanton.nutbar.alarms.listeners.LocationAlarmListener;
import com.google.inject.Inject;
import roboguice.service.RoboService;

public class AlarmService extends RoboService {

    private SMSSendingAlarm smsAlarm;

    @Inject
    private SMSSendingAlarmFactory smsAlarmFactory;

    @Inject
    private LocationAlarm locationAlarm;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

    @Override
    public void onCreate() {
        super.onCreate();
        
        smsAlarm = smsAlarmFactory.create(this);
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
        smsAlarm.resetAlarm();
    }

    private LocationAlarmListener locationAlarmListener = new LocationAlarmListener() {
        @Override
        public void onLocationChanged(Location location) {
            Intent i = new Intent();

            String message = getString(R.string.send_xmpp_command) + " " +
                    Double.toString(location.getLatitude()) + "," +
                    Double.toString(location.getLongitude()) + "," +
                    location.getProvider();

            Log.v("NBAR", message);

            i.putExtra(getString(R.string.send_xmpp_extra), message);

            i.setAction(getString(R.string.send_xmpp));

            startService(i);
        }
    };
}
