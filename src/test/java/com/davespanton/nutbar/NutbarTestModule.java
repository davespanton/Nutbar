package com.davespanton.nutbar;

import android.app.Application;
import android.util.Log;
import com.davespanton.nutbar.activity.menu.OptionsMenuDelegate;
import com.davespanton.nutbar.activity.menu.StubOptionsMenuDelegate;
import com.davespanton.nutbar.alarms.LocationAlarm;
import com.davespanton.nutbar.alarms.SMSSendingAlarm;
import com.davespanton.nutbar.alarms.StubLocationAlarm;
import com.davespanton.nutbar.alarms.StubSmsSendingAlarm;
import com.davespanton.nutbar.application.NutbarApplication;
import com.davespanton.nutbar.service.binder.AccelerometerBinderBuilder;
import com.davespanton.nutbar.service.binder.StubAccelerometerBinderBuilder;
import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.davespanton.nutbar.service.connection.StubListenerServiceConnection;
import com.davespanton.nutbar.service.sensor.SensorChangeListener;
import com.davespanton.nutbar.service.sensor.StubSensorChangeMonitor;
import com.davespanton.nutbar.service.xmpp.StubXMPPConnectionProvider;
import com.xtremelabs.robolectric.Robolectric;
import org.jivesoftware.smack.XMPPConnection;
import roboguice.config.AbstractAndroidModule;
import roboguice.inject.SharedPreferencesName;
import roboguice.util.Ln;

public class NutbarTestModule extends AbstractAndroidModule {

    private Application application;

    public NutbarTestModule(Application application) {
        this.application = application;
    }

    @Override
	protected void configure() {
        bind(AccelerometerBinderBuilder.class).to(StubAccelerometerBinderBuilder.class);

        bind(Ln.BaseConfig.class).toInstance(new NutbarLoggerConfig());
        bind(ListenerServiceConnection.class).toInstance(new StubListenerServiceConnection());
        bind(SensorChangeListener.class).toInstance(new StubSensorChangeMonitor());
		bind(SMSSendingAlarm.class).toInstance(new StubSmsSendingAlarm());
		bind(OptionsMenuDelegate.class).toInstance(new StubOptionsMenuDelegate());
        bind(LocationAlarm.class).toInstance(new StubLocationAlarm());

        bind(XMPPConnection.class).toProvider(StubXMPPConnectionProvider.class);

        bindConstant().annotatedWith(SharedPreferencesName.class)
            .to(application.getString(R.string.shared_preferences_package));
    }

    static class NutbarLoggerConfig extends Ln.BaseConfig {
        public NutbarLoggerConfig() {
            super();
            this.packageName = "nutbar";
            this.minimumLogLevel = Log.VERBOSE;
            this.scope = "NUT";
        }
    }

}
