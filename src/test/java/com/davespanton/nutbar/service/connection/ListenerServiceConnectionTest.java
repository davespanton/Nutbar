package com.davespanton.nutbar.service.connection;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.ComponentName;
import android.os.Binder;

import com.davespanton.nutbar.NutbarActivity;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ListenerServiceConnectionTest {
	
	private ListenerServiceConnection sut;
	private TestNutbarActivity nutbar;
	
	private String accelerometerListenerServiceName = "AccelerometerListenerService";
	
	@Before
	public void setup() {
		nutbar = new TestNutbarActivity();
		nutbar.onCreate(null);
		sut = new ListenerServiceConnection(nutbar);
	}
	
	@Test
	public void testAccelerometerServiceConnectionCallsActivity() {
		sut.onServiceConnected(getComponentName(accelerometerListenerServiceName), new Binder());
		assertTrue(nutbar.accelerometerServiceConnectedCalled);
	}

	private ComponentName getComponentName(String className) {
		ComponentName name = new ComponentName(
				"com.davespanton.nutbar.service",
				className);
		return name;
	}
	
	@Test
	public void testAccelerometerServiceDisconnectionCallsActivity() {
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
