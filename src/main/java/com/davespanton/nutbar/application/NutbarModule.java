package com.davespanton.nutbar.application;

import android.app.Application;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.menu.OptionsMenuDelegate;
import com.davespanton.nutbar.alarms.SMSSendingAlarm;
import com.davespanton.nutbar.alarms.factory.SMSSendingAlarmFactory;
import com.davespanton.nutbar.service.binder.AccelerometerBinderBuilder;
import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.davespanton.nutbar.service.sensor.SensorChangeListener;
import com.davespanton.nutbar.service.sensor.SensorChangeMonitor;
import com.davespanton.nutbar.service.xmpp.XMPPConnectionProvider;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.jivesoftware.smack.XMPPConnection;
import roboguice.inject.SharedPreferencesName;

import javax.inject.Inject;


public class NutbarModule extends AbstractModule {

    public static final String SHARED_PREFERENCE_PACKAGE = "com.davespanton.nutbar_preferences";
    
	@Override
	protected void configure() {
		bind(ListenerServiceConnection.class);
		bind(SensorChangeListener.class).to(SensorChangeMonitor.class);
		bind(AccelerometerBinderBuilder.class);
		bind(OptionsMenuDelegate.class);
        bind(XMPPConnection.class).toProvider(XMPPConnectionProvider.class);

        bindConstant().annotatedWith(SharedPreferencesName.class)
                .to(SHARED_PREFERENCE_PACKAGE);

        install(new FactoryModuleBuilder()
            .build(SMSSendingAlarmFactory.class));
    }
}
