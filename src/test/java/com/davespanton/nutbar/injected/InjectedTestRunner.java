package com.davespanton.nutbar.injected;

import android.app.Application;
import com.davespanton.nutbar.NutbarTestModule;
import com.davespanton.nutbar.application.NutbarApplication;
import com.google.inject.Injector;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.runners.model.InitializationError;
import roboguice.inject.ContextScope;

public class InjectedTestRunner extends RobolectricTestRunner {
    
	public InjectedTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override protected Application createApplication() {
        NutbarApplication application = (NutbarApplication) super.createApplication();
        application.setModule(new NutbarTestModule());
        return application;
    }

    @Override public void prepareTest(Object test) {
        NutbarApplication application = (NutbarApplication) Robolectric.application;

        //This project's application does not extend GuiceInjectableApplication therefore we need to enter the ContextScope manually.
        Injector injector = application.getInjector();
        ContextScope scope = injector.getInstance(ContextScope.class);
        scope.enter(application);

        injector.injectMembers(test);
    }
}
