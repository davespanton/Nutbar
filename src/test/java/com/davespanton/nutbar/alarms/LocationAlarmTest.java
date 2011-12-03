package com.davespanton.nutbar.alarms;

import android.app.Application;
import android.location.LocationManager;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowLocationManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(InjectedTestRunner.class)
public class LocationAlarmTest {

	@Inject
    private LocationAlarm sut;

	private ShadowLocationManager shadow;
	
	@Before
	public void setup() {
		shadow = (ShadowLocationManager) shadowOf((LocationManager) Robolectric.application.getSystemService(Application.LOCATION_SERVICE));
	}
	
	@After
	public void tearDown() {
		sut = null;
		shadow = null;
	}
	
	@Test
	public void shouldAddListenerOnTripAlarm() {
		sut.tripAlarm();
		assertTrue(shadow.getRequestLocationUpdateListeners().contains(sut));
	}
	
	@Test
	public void shouldRemoveListenerOnResetAlarm() {
		sut.tripAlarm();
		assertTrue(shadow.getRequestLocationUpdateListeners().contains(sut));
		sut.resetAlarm();
		assertFalse(shadow.getRequestLocationUpdateListeners().contains(sut));
	}
	
	@Test
	public void shouldReturnIsListeningWhenListening() {
		sut.tripAlarm();
		assertTrue(sut.isListening());
	}
	
	@Test
	public void shouldReturnIsNotListeningWhenNotListening() {
		sut.tripAlarm();
		sut.resetAlarm();
		assertFalse(sut.isListening());
	}
}
