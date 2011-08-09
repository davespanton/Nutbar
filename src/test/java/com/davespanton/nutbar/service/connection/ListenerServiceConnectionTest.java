package com.davespanton.nutbar.service.connection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.ComponentName;

import com.davespanton.nutbar.activity.NutbarActivity;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.davespanton.nutbar.service.ListenerService;
import com.davespanton.nutbar.service.StubListenerService;
import com.davespanton.nutbar.service.binder.AccelerometerListenerServiceBinder;
import com.davespanton.nutbar.service.binder.ListenerServiceBinder;

@RunWith(InjectedTestRunner.class)
public class ListenerServiceConnectionTest {
	
	private ListenerServiceConnection sut;
	private StubNutbarActivity nutbar;
	
	private String accelerometerListenerServiceName = "AccelerometerListenerService";
	
	@Before
	public void setup() {
		nutbar = new StubNutbarActivity();
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
		AccelerometerListenerServiceBinder binder = new AccelerometerListenerServiceBinder(new StubListenerService());
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
	
	@Test
	public void shouldReflectBinderIsListening() {
		ListenerService service = new StubListenerService();
		service.startListening();
		ListenerServiceBinder binder = new ListenerServiceBinder(service);
		sut.onServiceConnected(getComponentName(accelerometerListenerServiceName), binder);
		assertTrue(sut.isListening());
	}
	
	@Test
	public void shouldReturnNotListeningBeforeConnection() {
		assertFalse(sut.isListening());
	}
	
	private class StubNutbarActivity extends NutbarActivity {

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
