package com.davespanton.nutbar.alarms.broadcastreceiver;

import android.content.Intent;
import com.davespanton.nutbar.alarms.StubTrippable;
import com.davespanton.nutbar.alarms.Trippable;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(InjectedTestRunner.class)
public class ReTripReceiverTest {

    @Inject
    private ReTripReceiver reTripReceiver;

    @Inject
    private Trippable trippable;

    @Before
    public void setup() {
        Robolectric.pauseMainLooper();
    }

    @After
    public void tearDown() {
        Robolectric.unPauseMainLooper();
    }

    @Test
    public void shouldTripTrippableAfterDelay() {
        reTripReceiver.setTrippable(trippable);
        callOnReceive();

        Robolectric.idleMainLooper(ReTripReceiver.RE_TRIP_DELAY + 1);

        assertTrue(((StubTrippable) trippable).isTripped());
    }

    private void callOnReceive() {
        reTripReceiver.onReceive(Robolectric.getShadowApplication().getApplicationContext(), new Intent());
    }

    @Test
    public void shouldNotTripTrippableStraightAway() {
        reTripReceiver.setTrippable(trippable);
        callOnReceive();

        Robolectric.idleMainLooper(ReTripReceiver.RE_TRIP_DELAY - 1);

        assertFalse(((StubTrippable) trippable).isTripped());
    }

    @Test(expected = NullPointerException.class)
    public void shouldErrorIfNoTrippableIsSet() {
        callOnReceive();

        Robolectric.idleMainLooper(ReTripReceiver.RE_TRIP_DELAY + 1);
    }
}
