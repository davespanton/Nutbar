package com.davespanton.nutbar.service;

import java.util.ArrayList;

import android.location.LocationManager;
import android.location.GpsStatus.Listener;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.shadows.ShadowLocationManager;

@Implements(LocationManager.class)
public class ShadowNutLocationManager extends ShadowLocationManager {

	private ArrayList<Listener> listeners = new ArrayList<Listener>();
	
	@Implementation
	public boolean addGpsStatusListener(Listener listener) {
		
		if(!listeners.contains(listener))
			listeners.add(listener);
		
		return true;
	}
	
	@Implementation
	public void removeGpsStatusListener(Listener listener) {
		listeners.remove(listener);
	}
	
	public boolean hasListener(Listener listener) {
		return listeners.contains(listener);
	}
}
