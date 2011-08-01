package com.davespanton.nutbar.service.binder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.davespanton.nutbar.activity.ListenerServiceView;
import com.davespanton.nutbar.service.TestListenerService;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AccelerometerListenerServiceBinderTest {

		private AccelerometerListenerServiceBinder sut;
		private TestListenerService service;
		
		public boolean isConnected = false;
		
		@Before
		public void setup() {
			isConnected = false;
			service = new TestListenerService();
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
			assertTrue(isConnected);
		}
		
		@Test
		public void shouldCallAccelerometerDisconnectedOnUnbind() {
			isConnected = true;
			sut.onServiceDisconnection(testView);
			assertFalse(isConnected);
		}
		
		
		private ListenerServiceView testView = new ListenerServiceView() {
			
			@Override
			public void onAccelerometerServiceConnected() {
				isConnected = true;
				
			}

			@Override
			public void onAccelerometerServiceDisconnected() {
				isConnected = false;
			}

			@Override
			public void onGPSServiceConnected() {
				//not needed for test
			}

			@Override
			public void onGPSServiceDisconnected() {
				//not needed for test
			}
		};
}
