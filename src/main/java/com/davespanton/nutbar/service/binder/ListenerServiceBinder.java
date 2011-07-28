package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.NutbarActivity;
import com.davespanton.nutbar.service.ListenerService;

import android.os.Binder;

public class ListenerServiceBinder extends Binder {
	
	private ListenerService listenerService; 
	
	public ListenerServiceBinder(ListenerService service) {
		listenerService = service;
	}
	
	public ListenerService getService() {
		return listenerService;
	}
	
	public void onServiceConnection(NutbarActivity activity) {
		//template
	}

	public void onServiceDisconnection(NutbarActivity activity) {
		// template
	}
}
