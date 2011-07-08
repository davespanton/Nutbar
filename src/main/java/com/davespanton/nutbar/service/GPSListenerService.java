package com.davespanton.nutbar.service;

import com.google.inject.Inject;

import roboguice.service.RoboService;
import android.content.Intent;
import android.location.LocationManager;
import android.location.GpsStatus.Listener;
import android.os.IBinder;

public class GPSListenerService extends RoboService implements Listener, ListenerService {

	@Inject LocationManager loc; 
	
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
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	public void startListening() {
		loc.addGpsStatusListener(this);
	}
	
	public void stopListening() {
		loc.removeGpsStatusListener(this);
	}
}
