package com.davespanton.nutbar.service;

import roboguice.service.RoboService;
import android.content.Intent;
import android.location.LocationManager;
import android.location.GpsStatus.Listener;
import android.os.IBinder;
import android.util.Log;

public class GPSListenerService extends RoboService implements Listener, ListenerService {

	LocationManager loc; 
	
	private boolean isListening = false;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
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
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v("GPSListenerService", "onStartCommand called");
		return super.onStartCommand(intent, flags, startId);
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
