package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.service.AccelerometerListenerService;

public class AccelerometerBinderBuilder {
	
	public AccelerometerListenerServiceBinder build(AccelerometerListenerService service) {
		return new AccelerometerListenerServiceBinder(service);
	}
}
