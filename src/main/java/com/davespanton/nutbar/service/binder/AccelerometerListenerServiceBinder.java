package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.activity.ListenerServiceView;
import com.davespanton.nutbar.service.ListenerService;

public class AccelerometerListenerServiceBinder extends ListenerServiceBinder {

	public AccelerometerListenerServiceBinder(ListenerService service) {
		super(service);
	}

	@Override
	public void onServiceConnection(ListenerServiceView view) {
		view.onAccelerometerServiceConnected();
	}
	
	@Override
	public void onServiceDisconnection(ListenerServiceView view) {
		view.onAccelerometerServiceDisconnected();
	}
}
