package com.davespanton.nutbar.alarms;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.google.inject.Inject;

public class LocationAlarm implements LocationListener {

	@Inject
    private LocationManager loc;

	private boolean isListening = false;

    private LocationAlarmListener locationAlarmListener;
	
	public void tripAlarm() {
		if(isListening)
			return;

        Log.d("NBAR", "TRIPPED!");
		loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        loc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

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
        if(locationAlarmListener != null)
	        locationAlarmListener.onLocationChanged(location);
    }

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
        Log.d("NBAR", provider + " was enabled");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("NBAR", provider + " status changed to " + Integer.toString(status));
	}


    public void setOnLocationChangeListener(LocationAlarmListener listener) {
        locationAlarmListener = listener;
    }
}
