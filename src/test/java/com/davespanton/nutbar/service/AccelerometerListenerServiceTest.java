package com.davespanton.nutbar.service;

import static com.xtremelabs.robolectric.Robolectric.getShadowApplication;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Service;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.IBinder;

import com.davespanton.nutbar.R;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.davespanton.nutbar.service.binder.AccelerometerListenerServiceBinder;
import com.davespanton.nutbar.service.binder.StubAccelerometerListenerServiceBinder;
import com.davespanton.nutbar.service.sensor.SensorChangeListener;
import com.davespanton.nutbar.service.sensor.StubSensorChangeMonitor;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowSensorManager;

@RunWith(InjectedTestRunner.class)
public class AccelerometerListenerServiceTest {

	private AccelerometerListenerService sut;
	private ShadowSensorManager shadowSensorManager;
	
	@Inject
	private SensorChangeListener sensorChangeListener;
	
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
	public void shouldRegisterListenerOnCorrectStartIntent() {
		Intent i = getIntentWithAction(getShadowApplication().getString(R.string.acc_service_start_listening));
		sut.onStartCommand(i, 0, 0);
		assertTrue(shadowSensorManager.hasListener(sut));
	}
	
	private Intent getIntentWithAction(String action) {
		Intent intent = new Intent();
		intent.setAction(action);
		return intent;
	}
	
	@Test
	public void shouldRemoveListenerOnStopListening() {
		startStopListener();
		assertFalse(shadowSensorManager.hasListener(sut));
	}
	
	private void startStopListener() {
		sut.startListening();
		sut.stopListening();
	}
	
	@Test
	public void shouldResetMonitorOnStopListening() {
		startStopListener();
		assertTrue( ((StubSensorChangeMonitor) sensorChangeListener).hasBeenReset() );
	}
	
	@Test
	public void shouldRemoveListenerOnCorrectStopIntent() {
		sut.startListening();
		Intent i = getIntentWithAction(getShadowApplication().getString(R.string.acc_service_stop_listening));
		sut.onStartCommand(i, 0, 0);
		assertFalse(shadowSensorManager.hasListener(sut));
	}
	
	@Test
	public void shouldReturnIsListeningWhenListening() {
		sut.startListening();
		assertTrue(sut.isListening());
	}
	
	@Test
	public void shouldReturnIsNotListeningWhenNotListening() {
		startStopListener();
		assertFalse(sut.isListening());
	}
	
	@Test
	public void shouldReturnIsNotListeningAfterFailureToStart() {
		shadowSensorManager.forceListenersToFail = true;
		sut.startListening();
		assertFalse(sut.isListening());
	}
	
	@Test
	public void shouldReturnAccelerometerListenerServiceBinderOnBind() {
		IBinder binder = sut.onBind(new Intent());
		assertTrue(binder instanceof AccelerometerListenerServiceBinder);
	}
	
	@Test
	public void shouldTellBinderWhenStartingToListen() {
		sut.startListening();
		StubAccelerometerListenerServiceBinder binder = (StubAccelerometerListenerServiceBinder) sut.onBind(new Intent());
		assertTrue(binder.isArmed());
	}
	
	@Test
	public void shouldTellBinderWhenNoLongerListening() {
		sut.startListening();
		sut.stopListening();
		StubAccelerometerListenerServiceBinder binder = (StubAccelerometerListenerServiceBinder) sut.onBind(new Intent());
		assertFalse(binder.isArmed());
	}
	
	@Test
	public void shouldTellBinderWhenTripped() {
		sut.sensorMonitorTripped();
		StubAccelerometerListenerServiceBinder binder = (StubAccelerometerListenerServiceBinder) sut.onBind(new Intent());
		assertTrue(binder.isTripped());
	}
	
	@Test
	public void shouldSendStartGPSListeningIntentOnActivation() {
		sut.sensorMonitorTripped();
		String lastAction = getShadowApplication().getNextStartedService().getAction();
		assertEquals(getShadowApplication().getString(R.string.gps_service_start_listening), lastAction); 
	}
	
	@Test
	public void shouldSendAlarmServiceTripOnActivation() {
		sut.sensorMonitorTripped();
		getShadowApplication().getNextStartedService(); //consume gps intent first
		String lastAction = getShadowApplication().getNextStartedService().getAction();
		assertEquals(getShadowApplication().getString(R.string.alarm_service_trip), lastAction);
	}
	
	@Test
	public void shouldSendStopGPSListenerServiceOnStopListening() {
		sut.stopListening();
		String lastAction = getShadowApplication().getNextStartedService().getAction();
		assertEquals(lastAction, getShadowApplication().getString(R.string.gps_service_stop_listening), lastAction);
	}
	
	@Test
	public void shouldStopAlarmServiceOnDestroy() {
		sut.onDestroy();
		String lastStopAction = getShadowApplication().getNextStoppedService().getAction();
		assertEquals(lastStopAction, getShadowApplication().getString(R.string.alarm_service_trip));
	}
	
	@Test
	public void shouldUpdateBinderWhenRequested() {
		StubAccelerometerListenerServiceBinder binder = (StubAccelerometerListenerServiceBinder) sut.onBind(new Intent());
		
		sut.startListening();
		binder.onDisarmed();
		sut.updateBinder();
		assertTrue(binder.isArmed());
		
		sut.sensorMonitorTripped();
		binder.onDisarmed();
		sut.updateBinder();
		assertTrue(binder.isTripped());
	}
}
