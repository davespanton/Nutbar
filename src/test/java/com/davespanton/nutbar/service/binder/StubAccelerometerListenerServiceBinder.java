package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.service.ListenerService;

public class StubAccelerometerListenerServiceBinder extends AccelerometerListenerServiceBinder {

	private boolean isArmed = false;
	
	public StubAccelerometerListenerServiceBinder(ListenerService service) {
		super(service);
	}

	@Override
	public void onArmed() {
		isArmed = true;
	}

	@Override
	public void onDisarmed() {
		isArmed = false;
	}
	
	public boolean isArmed() {
		return isArmed;
	}

}
