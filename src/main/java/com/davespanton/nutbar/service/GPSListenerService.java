package com.davespanton.nutbar.service;

import com.davespanton.nutbar.service.binder.ListenerServiceBinder;

import roboguice.service.RoboService;
import android.content.Intent;
import android.location.LocationManager;
import android.location.GpsStatus.Listener;
import android.os.IBinder;

public class GPSListenerService extends RoboService implements Listener, ListenerService {

	private ListenerServiceBinder binder = new ListenerServiceBinder(this);
	
	private LocationManager loc; 
	
	private boolean isListening = false;
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onGpsStatusChanged(int event) {
	
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		loc = (LocationManager) getSystemService(LOCATION_SERVICE);
	}

	@Override
	public void onDestroy() {
		loc = null;
	}

	public void startListening() {
		isListening = loc.addGpsStatusListener(this);
	}
	
	public void stopListening() {
		loc.removeGpsStatusListener(this);
		isListening = false;
	}
	
	public boolean isListening() {
		return isListening;
	}
}
