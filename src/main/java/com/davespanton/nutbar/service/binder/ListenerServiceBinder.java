package com.davespanton.nutbar.service.binder;

import android.os.Binder;

import com.davespanton.nutbar.activity.ListenerServiceView;
import com.davespanton.nutbar.service.ListenerService;

public class ListenerServiceBinder extends Binder {
	
	private ListenerService listenerService; 
	
	public ListenerServiceBinder(ListenerService service) {
		listenerService = service;
	}
	
	public ListenerService getService() {
		return listenerService;
	}
	
	public void onServiceConnection(ListenerServiceView view) {
		//template
	}

	public void onServiceDisconnection(ListenerServiceView view) {
		// template
	}
}
