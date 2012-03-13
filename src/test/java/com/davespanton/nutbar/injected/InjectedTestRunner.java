package com.davespanton.nutbar.injected;

import com.davespanton.nutbar.NutbarTestModule;
import com.davespanton.nutbar.shadows.ShadowChat;
import com.davespanton.nutbar.shadows.ShadowChatManager;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.runners.model.InitializationError;
import roboguice.RoboGuice;

public class InjectedTestRunner extends RobolectricTestRunner {

    public InjectedTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override public void prepareTest(Object test) {
        NutbarTestModule module = new NutbarTestModule(Robolectric.application);
        RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application)).with(module));

        Injector injector = RoboGuice.getInjector(Robolectric.application.getApplicationContext());
        injector.injectMembers(test);
    }

    @Override
    protected void bindShadowClasses() {
        super.bindShadowClasses();
        Robolectric.bindShadowClass(ShadowChatManager.class);
        Robolectric.bindShadowClass(ShadowChat.class);
    }
}
