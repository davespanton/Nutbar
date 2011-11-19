package com.davespanton.nutbar.alarms;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.inject.Inject;

public class LocationAlarm implements LocationListener {

	@Inject
    private LocationManager loc;

	private boolean isListening = false;
	
	public void tripAlarm() {
		if(isListening)
			return;
		
		loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		isListening = true;
	}
	
	public void resetAlarm() {
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
