package com.davespanton.nutbar;

import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.davespanton.nutbar.service.connection.StubListenerServiceConnection;

import roboguice.config.AbstractAndroidModule;
import roboguice.util.Ln;
import android.util.Log;

public class NutbarTestModule extends AbstractAndroidModule {

	@Override 
	protected void configure() {
		bind(Ln.BaseConfig.class).toInstance(new NutbarLoggerConfig());
		bind(ListenerServiceConnection.class).toInstance(new StubListenerServiceConnection());
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
