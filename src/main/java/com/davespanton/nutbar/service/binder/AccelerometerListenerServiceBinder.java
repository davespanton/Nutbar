package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.activity.ListenerServiceView;
import com.davespanton.nutbar.service.ListenerService;

public class AccelerometerListenerServiceBinder extends ListenerServiceBinder {

	private ListenerServiceView listenerServiceView;
	
	public AccelerometerListenerServiceBinder(ListenerService service) {
		super(service);
	}

	@Override
	public void onServiceConnection(ListenerServiceView view) {
		view.onAccelerometerServiceConnected();
		listenerServiceView = view;
	}
	
	@Override
	public void onServiceDisconnection(ListenerServiceView view) {
		view.onAccelerometerServiceDisconnected();
		listenerServiceView = null;
	}
	
	public void onArmed() {
		listenerServiceView.onArmed();
	}
	
	public void onDisarmed() {
		listenerServiceView.onDisarmed();
	}
}
