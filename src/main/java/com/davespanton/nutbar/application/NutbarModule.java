package com.davespanton.nutbar.application;

import android.app.Application;
import com.davespanton.nutbar.service.xmpp.XmppCommunication;
import com.davespanton.nutbar.service.xmpp.XmppConnectionProvider;
import com.google.inject.Provides;
import org.jivesoftware.smack.XMPPConnection;
import roboguice.config.AbstractAndroidModule;

import com.davespanton.nutbar.activity.menu.OptionsMenuDelegate;
import com.davespanton.nutbar.alarms.SMSSendingAlarm;
import com.davespanton.nutbar.service.binder.AccelerometerBinderBuilder;
import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.davespanton.nutbar.service.sensor.SensorChangeListener;
import com.davespanton.nutbar.service.sensor.SensorChangeMonitor;


public class NutbarModule extends AbstractAndroidModule {

    private Application application;

    public NutbarModule(Application application) {
        this.application = application;
    }

	@Override
	protected void configure() {
		bind(ListenerServiceConnection.class);
		bind(SensorChangeListener.class).to(SensorChangeMonitor.class);
		bind(AccelerometerBinderBuilder.class);
		bind(OptionsMenuDelegate.class);
        bind(XMPPConnection.class).toProvider(XmppConnectionProvider.class);
	}

    @Provides
    private SMSSendingAlarm providesSMSSendingAlarm() {
        return new SMSSendingAlarm(application.getApplicationContext());
    }

}
