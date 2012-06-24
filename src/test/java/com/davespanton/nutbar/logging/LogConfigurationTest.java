package com.davespanton.nutbar.logging;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class LogConfigurationTest {

    @Inject
    private LogConfiguration logConfiguration;

    @Test
    public void shouldConfigureMicroLog() {
        Assert.fail("Unimplemented");
    }
}
