package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.service.ListenerService;

public class GPSBinderBuilder {

	public GPSListenerServiceBinder build(ListenerService service) {
		return new GPSListenerServiceBinder(service);
	}
}
