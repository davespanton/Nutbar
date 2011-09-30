package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.activity.ListenerServiceView;
import com.davespanton.nutbar.service.ListenerService;

public class GPSListenerServiceBinder extends ListenerServiceBinder {

	private ListenerServiceView listenerServiceView;
	
	public GPSListenerServiceBinder(ListenerService service) {
		super(service);
	}

	@Override
	public void onServiceConnection(ListenerServiceView view) {
		listenerServiceView = view;
	}

	@Override
	public void onServiceDisconnection(ListenerServiceView view) {
		listenerServiceView = null;
	}
	
	public void onTripped() {
		listenerServiceView.onTripped();
	}
}
