package com.davespanton.nutbar;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.davespanton.nutbar.activity.menu.OptionsMenuDelegate;
import com.davespanton.nutbar.activity.menu.StubOptionsMenuDelegate;
import com.davespanton.nutbar.alarms.LocationAlarm;
import com.davespanton.nutbar.alarms.SMSSendingAlarm;
import com.davespanton.nutbar.alarms.StubLocationAlarm;
import com.davespanton.nutbar.alarms.StubSmsSendingAlarm;
import com.davespanton.nutbar.application.NutbarModule;
import com.davespanton.nutbar.service.binder.AccelerometerBinderBuilder;
import com.davespanton.nutbar.service.binder.StubAccelerometerBinderBuilder;
import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.davespanton.nutbar.service.connection.StubListenerServiceConnection;
import com.davespanton.nutbar.service.sensor.SensorChangeListener;
import com.davespanton.nutbar.service.sensor.StubSensorChangeMonitor;
import com.davespanton.nutbar.service.xmpp.StubXMPPConnectionProvider;
import com.davespanton.nutbar.service.xmpp.XMPPCommunication;
import com.davespanton.nutbar.service.xmpp.XMPPReconnectionHandler;
import com.google.inject.AbstractModule;
import com.xtremelabs.robolectric.Robolectric;
import org.jivesoftware.smack.XMPPConnection;
import roboguice.inject.SharedPreferencesName;

public class NutbarTestModule extends AbstractModule {

    @Override
	protected void configure() {
        bind(AccelerometerBinderBuilder.class).to(StubAccelerometerBinderBuilder.class);

        bind(ListenerServiceConnection.class).toInstance(new StubListenerServiceConnection());
        bind(SensorChangeListener.class).toInstance(new StubSensorChangeMonitor());
		bind(SMSSendingAlarm.class).toInstance(new StubSmsSendingAlarm());
		bind(OptionsMenuDelegate.class).toInstance(new StubOptionsMenuDelegate());
        bind(LocationAlarm.class).toInstance(new StubLocationAlarm());
        bind(XMPPReconnectionHandler.class).toInstance(new XMPPReconnectionHandler());
        bind(XMPPCommunication.class).toInstance(new XMPPCommunication());
        
        bind(XMPPConnection.class).toProvider(StubXMPPConnectionProvider.class);

        bindConstant().annotatedWith(SharedPreferencesName.class)
            .to(NutbarModule.SHARED_PREFERENCE_PACKAGE);

        bind(SharedPreferences.class).toInstance(PreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext()));

    }
}
