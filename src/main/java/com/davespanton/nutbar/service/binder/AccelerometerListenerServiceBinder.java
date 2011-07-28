package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.NutbarActivity;
import com.davespanton.nutbar.service.ListenerService;

public class AccelerometerListenerServiceBinder extends ListenerServiceBinder {

	public AccelerometerListenerServiceBinder(ListenerService service) {
		super(service);
	}

	@Override
	public void onServiceConnection(NutbarActivity activity) {
		activity.onAccelerometerServiceConnected();
	}
	
	@Override
	public void onServiceDisconnection(NutbarActivity activity) {
		activity.onAccelerometerServiceDisconnected();
	}
}
