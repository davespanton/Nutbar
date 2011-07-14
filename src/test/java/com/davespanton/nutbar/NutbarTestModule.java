package com.davespanton.nutbar;

import com.davespanton.nutbar.service.GPSListenerService;
import com.davespanton.nutbar.service.ListenerService;

import roboguice.config.AbstractAndroidModule;
import roboguice.util.Ln;
import android.hardware.SensorManager;
import android.util.Log;

public class NutbarTestModule extends AbstractAndroidModule {

	@Override protected void configure() {
		bind(Ln.BaseConfig.class).toInstance(new NutbarLoggerConfig());
		bind(ListenerService.class).to(GPSListenerService.class);
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
