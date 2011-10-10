package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.service.ListenerService;

public class StubAccelerometerBinderBuilder extends AccelerometerBinderBuilder {

	@Override
	public AccelerometerListenerServiceBinder build(ListenerService service) {
		return new StubAccelerometerListenerServiceBinder(service);
	}

}
