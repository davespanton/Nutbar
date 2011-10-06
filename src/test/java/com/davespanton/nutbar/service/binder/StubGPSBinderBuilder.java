package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.service.ListenerService;

public class StubGPSBinderBuilder extends GPSBinderBuilder {

	@Override
	public GPSListenerServiceBinder build(ListenerService service) {
		return new StubGPSListenerServiceBinder(service);
	}

}
