package com.davespanton.nutbar.service;

import android.os.Binder;

public class ListenerServiceBinder extends Binder {
	
	private ListenerService listenerService; 
	
	public ListenerServiceBinder(ListenerService service) {
		listenerService = service;
	}
	
	public ListenerService getService() {
		return listenerService;
	}
}
