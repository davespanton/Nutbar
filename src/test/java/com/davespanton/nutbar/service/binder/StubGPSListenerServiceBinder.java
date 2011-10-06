package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.service.ListenerService;

public class StubGPSListenerServiceBinder extends GPSListenerServiceBinder {

	private boolean isTripped = false;
	
	public StubGPSListenerServiceBinder(ListenerService service) {
		super(service);
	}

	@Override
	public void onTripped() {
		isTripped = true;
	}
	
	public boolean isTripped() {
		return isTripped;
	}
}
