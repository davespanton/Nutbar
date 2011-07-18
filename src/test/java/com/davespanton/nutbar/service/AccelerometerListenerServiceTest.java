package com.davespanton.nutbar.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Service;
import android.hardware.SensorManager;

import com.davespanton.nutbar.shadows.ShadowSensorManager;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.bytecode.ShadowWrangler;

@RunWith(RobolectricTestRunner.class)
public class AccelerometerListenerServiceTest {

	private AccelerometerListenerService sut;
	private ShadowSensorManager shadowSensorManager;
	
	@Before
	public void setup() {
		Robolectric.bindShadowClass(ShadowSensorManager.class);
		SensorManager sensorManager = (SensorManager) Robolectric.application.getSystemService(Service.SENSOR_SERVICE);
		shadowSensorManager = (ShadowSensorManager) ShadowWrangler.getInstance().shadowOf(sensorManager);
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
	
}
