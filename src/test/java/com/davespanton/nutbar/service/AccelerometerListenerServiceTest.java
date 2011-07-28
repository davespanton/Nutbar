package com.davespanton.nutbar.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static com.xtremelabs.robolectric.Robolectric.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Service;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.IBinder;

import com.davespanton.nutbar.service.binder.ListenerServiceBinder;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowSensorManager;

@RunWith(RobolectricTestRunner.class)
public class AccelerometerListenerServiceTest {

	private AccelerometerListenerService sut;
	private ShadowSensorManager shadowSensorManager;
	
	@Before
	public void setup() {
		
		SensorManager sensorManager = (SensorManager) Robolectric.application.getSystemService(Service.SENSOR_SERVICE);
		shadowSensorManager = (ShadowSensorManager) shadowOf(sensorManager);
		sut = new AccelerometerListenerService();
		sut.onCreate();
	}
	
	@After
	public void teardown() {
		sut = null;
		shadowSensorManager = null;
	}
	
	@Test
	public void shouldRegisterListenerOnStartListening() {
		sut.startListening();
		assertTrue(shadowSensorManager.hasListener(sut));
	}
	
	@Test
	public void shouldRemoveListenerOnStopListening() {
		sut.startListening();
		sut.stopListening();
		assertFalse(shadowSensorManager.hasListener(sut));
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
	public void shouldReturnIsNotListeningAfterFailureToStart() {
		shadowSensorManager.forceListenersToFail = true;
		sut.startListening();
		assertFalse(sut.isListening());
	}
	
	@Test
	public void shouldReturnListenerServiceBinderOnBind() {
		IBinder binder = sut.onBind(new Intent());
		assertTrue(binder instanceof ListenerServiceBinder);
	}
	
}
