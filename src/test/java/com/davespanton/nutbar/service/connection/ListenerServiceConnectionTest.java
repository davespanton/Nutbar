package com.davespanton.nutbar.service.connection;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.ComponentName;

import com.davespanton.nutbar.activity.NutbarActivity;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.davespanton.nutbar.service.TestListenerService;
import com.davespanton.nutbar.service.binder.AccelerometerListenerServiceBinder;

@RunWith(InjectedTestRunner.class)
public class ListenerServiceConnectionTest {
	
	private ListenerServiceConnection sut;
	private TestNutbarActivity nutbar;
	
	private String accelerometerListenerServiceName = "AccelerometerListenerService";
	
	@Before
	public void setup() {
		nutbar = new TestNutbarActivity();
		nutbar.onCreate(null);
		sut = new ListenerServiceConnection();
		sut.setActivity(nutbar);
	}
	
	@Test
	public void testAccelerometerServiceConnectionCallsActivity() {
		makeConnection();
		assertTrue(nutbar.accelerometerServiceConnectedCalled);
	}
	
	private void makeConnection() {
		AccelerometerListenerServiceBinder binder = new AccelerometerListenerServiceBinder(new TestListenerService());
		sut.onServiceConnected(getComponentName(accelerometerListenerServiceName), binder);
	}

	private ComponentName getComponentName(String className) {
		ComponentName name = new ComponentName(
				"com.davespanton.nutbar.service",
				className);
		return name;
	}
	
	@Test
	public void testAccelerometerServiceDisconnectionCallsActivity() {
		makeConnection();
		
		sut.onServiceDisconnected(getComponentName(accelerometerListenerServiceName));
		assertTrue(nutbar.accelerometerServiceDisconnectedCalled);
	}
	
	private class TestNutbarActivity extends NutbarActivity {

		public boolean accelerometerServiceConnectedCalled = false;
		public boolean accelerometerServiceDisconnectedCalled = false;
		
		@Override
		public void onAccelerometerServiceConnected() {
			super.onAccelerometerServiceConnected();
			accelerometerServiceConnectedCalled = true;
		}

		@Override
		public void onAccelerometerServiceDisconnected() {
			super.onAccelerometerServiceDisconnected();
			accelerometerServiceDisconnectedCalled = true;
		}
		
	}
}
