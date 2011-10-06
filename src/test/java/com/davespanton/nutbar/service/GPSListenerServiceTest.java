package com.davespanton.nutbar.service;

import static com.xtremelabs.robolectric.Robolectric.getShadowApplication;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Application;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

import com.davespanton.nutbar.R;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.davespanton.nutbar.service.binder.ListenerServiceBinder;
import com.davespanton.nutbar.service.binder.StubGPSListenerServiceBinder;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowLocationManager;

@RunWith(InjectedTestRunner.class)
public class GPSListenerServiceTest {

	private GPSListenerService sut;
	private ShadowLocationManager shadow;
	
	@Before
	public void setup() {
		sut = new GPSListenerService();
		sut.onCreate();
		shadow = (ShadowLocationManager) shadowOf((LocationManager) Robolectric.application.getSystemService(Application.LOCATION_SERVICE));
	}
	
	@After
	public void tearDown() {
		sut = null;
		shadow = null;
	}
	
	@Test
	public void shouldAddListenerOnStartListening() {
		sut.startListening();
		assertTrue(shadow.getRequestLocationUpdateListeners().contains(sut));
	}
	
	@Test
	public void shouldRemoveListenerOnStopListening() {
		sut.startListening();
		assertTrue(shadow.getRequestLocationUpdateListeners().contains(sut));
		sut.stopListening();
		assertFalse(shadow.getRequestLocationUpdateListeners().contains(sut));
	}
	
	@Test
	public void shouldReturnIsListeningWhenListening() {
		sut.startListening();
		assertTrue(sut.isListening());
	}
	
	@Test
	public void shouldReturnIsNotListeningWhenNotListening() {
		sut.startListening();
		sut.stopListening();
		assertFalse(sut.isListening());
	}
	
	@Test
	public void shouldReturnListenerServiceBinderOnBind() {
		IBinder binder = sut.onBind(new Intent());
		assertTrue(binder instanceof ListenerServiceBinder);
	}
	
	@Test
	public void shouldRegisterListeningOnCorrectIntent() {
		Intent i = getIntentWithAction(getShadowApplication().getString(R.string.gps_service_start_listening));
		
		sut.onStartCommand(i, 0, 0);
		
		assertTrue(shadow.getRequestLocationUpdateListeners().contains(sut));
	}
	
	private Intent getIntentWithAction(String action) {
		Intent i = new Intent();
		i.setAction(action);
		return i;
	}
	
	@Test
	public void shouldUnregisterListenerOnCorrectIntent() {
		Intent i = getIntentWithAction(getShadowApplication().getString(R.string.gps_service_stop_listening));
		
		sut.startListening();
		sut.onStartCommand(i, 0, 0);
		
		assertFalse(shadow.getRequestLocationUpdateListeners().contains(sut));
	}
	
	@Test
	public void shouldCallBinderWhenStartingToListen() {
		Intent i = getIntentWithAction(getShadowApplication().getString(R.string.gps_service_start_listening));
		StubGPSListenerServiceBinder binder = (StubGPSListenerServiceBinder) sut.onBind(i);
		
		sut.onStartCommand(i, 0, 0);
		
		assertTrue(binder.isTripped());
		
	}
}
