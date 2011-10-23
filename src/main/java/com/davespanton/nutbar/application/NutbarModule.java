package com.davespanton.nutbar.application;

import roboguice.config.AbstractAndroidModule;

import com.davespanton.nutbar.alarms.SMSSendingAlarm;
import com.davespanton.nutbar.service.binder.AccelerometerBinderBuilder;
import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.davespanton.nutbar.service.sensor.SensorChangeListener;
import com.davespanton.nutbar.service.sensor.SensorChangeMonitor;


public class NutbarModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		bind(ListenerServiceConnection.class);
		bind(SensorChangeListener.class).to(SensorChangeMonitor.class);
		bind(AccelerometerBinderBuilder.class);
		bind(SMSSendingAlarm.class);
	}

}
