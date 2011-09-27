package com.davespanton.nutbar.service.binder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.davespanton.nutbar.StubListenerServiceView;
import com.davespanton.nutbar.service.StubListenerService;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GPSListenerServiceBinderTest {

	private GPSListenerServiceBinder sut;
	private StubListenerService service;
	private StubListenerServiceView testView;
	
	@Before
	public void setup() {
		service = new StubListenerService();
		testView = new StubListenerServiceView();
		sut = new GPSListenerServiceBinder(service);
	}
	
	@After
	public void tearDown() {
		sut = null;
		service = null;
		testView = null;
	}
	
	@Test
	public void shouldCallAccelerometerConnectedOnBind() {
		sut.onServiceConnection(testView);
		assertTrue(testView.isGPSServiceConnected());
	}
	
	@Test
	public void shouldCallAccelerometerDisconnectedOnUnbind() {
		sut.onServiceDisconnection(testView);
		assertFalse(testView.isGPSServiceConnected());
	}
}