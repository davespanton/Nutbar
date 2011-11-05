package com.davespanton.nutbar.service.binder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.davespanton.nutbar.activity.StubListenerServiceView;
import com.davespanton.nutbar.service.StubAccelerometerListenerService;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AccelerometerListenerServiceBinderTest {

		private AccelerometerListenerServiceBinder sut;
		private StubAccelerometerListenerService service;
		private StubListenerServiceView testView;
		
		@Before
		public void setup() {
			service = new StubAccelerometerListenerService();
			service.setBinder(sut);
			testView = new StubListenerServiceView();
			sut = new AccelerometerListenerServiceBinder(service);
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
			assertTrue(testView.isAccelerometerServiceConnected());
		}
			
		@Test
		public void shouldCallAccelerometerDisconnectedOnUnbind() {
			sut.onServiceDisconnection(testView);
			assertFalse(testView.isAccelerometerServiceConnected());
		}
		
		@Test
		public void shouldUpdateViewWhenServiceStartsListening() {
			sut.onServiceConnection(testView);
			sut.onArmed();
			assertTrue(testView.isArmed());
		}
		
		@Test
		public void shouldUpdateViewWhenServiceStopsListening() {
			sut.onServiceConnection(testView);
			sut.onDisarmed();
			assertTrue(testView.isDisarmed());
		}
		
		@Test
		public void shouldUpdateViewWhenTripped() {
			sut.onServiceConnection(testView);
			sut.onTripped();
			assertTrue(testView.isTripped());
		}

}
