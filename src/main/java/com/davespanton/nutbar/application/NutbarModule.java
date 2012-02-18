package com.davespanton.nutbar.application;

import android.app.Application;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.menu.OptionsMenuDelegate;
import com.davespanton.nutbar.service.binder.AccelerometerBinderBuilder;
import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.davespanton.nutbar.service.sensor.SensorChangeListener;
import com.davespanton.nutbar.service.sensor.SensorChangeMonitor;
import com.davespanton.nutbar.service.xmpp.XMPPConnectionProvider;
import org.jivesoftware.smack.XMPPConnection;
import roboguice.config.AbstractAndroidModule;
import roboguice.inject.SharedPreferencesName;


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
        bind(XMPPConnection.class).toProvider(XMPPConnectionProvider.class);

        bindConstant().annotatedWith(SharedPreferencesName.class)
                .to(application.getString(R.string.shared_preferences_package));
    }
}
