package com.davespanton.nutbar.service;

import com.google.inject.Inject;

import roboguice.service.RoboService;
import android.content.Intent;
import android.location.LocationManager;
import android.location.GpsStatus.Listener;
import android.os.IBinder;

public class GPSListenerService extends RoboService implements Listener {

	@Inject LocationManager loc; 
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onGpsStatusChanged(int event) {
	}
	
	
}
