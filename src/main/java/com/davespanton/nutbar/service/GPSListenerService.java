package com.davespanton.nutbar.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.davespanton.nutbar.R;
import com.davespanton.nutbar.service.binder.ListenerServiceBinder;

public class GPSListenerService extends Service implements LocationListener, ListenerService {

	private ListenerServiceBinder binder;
	
	private LocationManager loc; 

	private boolean isListening = false;
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		loc = (LocationManager) getSystemService(LOCATION_SERVICE);
		binder = new ListenerServiceBinder(this);
	}

	@Override
	public void onDestroy() {
		loc = null;
		binder = null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent.getAction().equals(getString(R.string.gps_service_start_listening)))
			startListening();
		else if(intent.getAction().equals(getString(R.string.gps_service_stop_listening)))
			stopListening();
		
		return START_STICKY;
	}

	public void startListening() {
		if(isListening)
			return;
		
		loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		isListening = true;
	}
	
	public void stopListening() {
		loc.removeUpdates(this);
		isListening = false;
	}
	
	public boolean isListening() {
		return isListening;
	}

	@Override
	public void onLocationChanged(Location location) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
}
