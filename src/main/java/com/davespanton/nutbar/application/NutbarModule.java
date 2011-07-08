package com.davespanton.nutbar.application;

import com.davespanton.nutbar.service.GPSListenerService;
import com.davespanton.nutbar.service.ListenerService;

import roboguice.config.AbstractAndroidModule;


public class NutbarModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		bind(ListenerService.class).to(GPSListenerService.class);
	}

}
