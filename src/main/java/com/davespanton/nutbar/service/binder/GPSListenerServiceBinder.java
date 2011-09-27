package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.activity.ListenerServiceView;
import com.davespanton.nutbar.service.ListenerService;

public class GPSListenerServiceBinder extends ListenerServiceBinder {

	public GPSListenerServiceBinder(ListenerService service) {
		super(service);
	}

	@Override
	public void onServiceConnection(ListenerServiceView view) {
		view.onGPSServiceConnected();
	}

	@Override
	public void onServiceDisconnection(ListenerServiceView view) {
		view.onGPSServiceDisconnected();
	}

}
