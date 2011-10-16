package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.activity.ListenerServiceView;
import com.davespanton.nutbar.service.AccelerometerListenerService;

public class AccelerometerListenerServiceBinder extends ListenerServiceBinder {

	private ListenerServiceView listenerServiceView;
	private AccelerometerListenerService accelerometerListenerService;
	
	public AccelerometerListenerServiceBinder(AccelerometerListenerService service) {
		super(service);
		accelerometerListenerService = service;
	}

	@Override
	public void onServiceConnection(ListenerServiceView view) {
		view.onAccelerometerServiceConnected();
		listenerServiceView = view;
		accelerometerListenerService.updateBinder();
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
	
	public void onTripped() {
		listenerServiceView.onTripped();
	}
}
