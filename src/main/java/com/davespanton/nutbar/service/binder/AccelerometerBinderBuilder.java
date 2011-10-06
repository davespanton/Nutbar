package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.service.ListenerService;

public class AccelerometerBinderBuilder {
	
	public AccelerometerListenerServiceBinder build(ListenerService service) {
		return new AccelerometerListenerServiceBinder(service);
	}
}
