package com.davespanton.nutbar.service.binder;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.davespanton.nutbar.NutbarActivity;
import com.davespanton.nutbar.service.TestListenerService;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AccelerometerListenerServiceBinderTest {

		private AccelerometerListenerServiceBinder sut;
		private TestListenerService service;
		private TestNutbarActivity activity;
		
		@Before
		public void setup() {
			activity = new TestNutbarActivity();
			service = new TestListenerService();
			sut = new AccelerometerListenerServiceBinder(service);
		}
		
		@After
		public void tearDown() {
			sut = null;
			service = null;
			activity = null;
		}
		
		@Test
		public void shouldCallAccelerometerConnectedOnBind() {
			sut.onServiceConnection(activity);
			assertTrue(activity.isConnected);
		}
		
		@Test
		public void shouldCallAccelerometerDisconnectedOnUnbind() {
			activity.isConnected = true;
			sut.onServiceDisconnection(activity);
			assertFalse(activity.isConnected);
		}
		
		
		private class TestNutbarActivity extends NutbarActivity {

			public boolean isConnected = false;
			
			@Override
			public void onAccelerometerServiceConnected() {
				isConnected = true;
				
			}

			@Override
			public void onAccelerometerServiceDisconnected() {
				isConnected = false;
			}
			
		}
}
