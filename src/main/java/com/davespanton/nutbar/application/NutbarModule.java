package com.davespanton.nutbar.application;

import com.davespanton.nutbar.service.connection.ListenerServiceConnection;

import roboguice.config.AbstractAndroidModule;


public class NutbarModule extends AbstractAndroidModule {

	@Override
	protected void configure() {
		bind(ListenerServiceConnection.class);
	}

}
