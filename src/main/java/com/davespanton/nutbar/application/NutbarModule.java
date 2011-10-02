package com.davespanton.nutbar.application;

import com.davespanton.nutbar.service.binder.GPSBinderBuilder;
import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.davespanton.nutbar.service.sensor.SensorChangeListener;
import com.davespanton.nutbar.service.sensor.SensorChangeMonitor;

import roboguice.config.AbstractAndroidModule;


public class NutbarModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		bind(ListenerServiceConnection.class);
		bind(SensorChangeListener.class).to(SensorChangeMonitor.class);
		bind(GPSBinderBuilder.class);
	}

}
