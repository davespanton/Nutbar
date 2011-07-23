package com.davespanton.nutbar;

import static com.xtremelabs.robolectric.Robolectric.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.ComponentName;
import android.content.Intent;
import android.widget.Button;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricTestRunner.class)
public class NutbarActivityTest {

	private NutbarActivity sut;
	
	@Before
	public void setup() {
		sut = new NutbarActivity();
		sut.onCreate(null);
	}
	
	@Test
	public void shouldStartGpsListenerServiceOnCreate() {
		//ShadowActivity shadow = shadowOf(sut);
		//Intent intent = shadow.peekNextStartedService();
		//assertEquals(intent.getAction(), getShadowApplication().getString(R.string.start_gps_listener_service));
		
		Intent i = new Intent();
		i.setAction(Robolectric.application.getResources().getString(R.string.start_gps_listener_service));
		ComponentName serviceName = Robolectric.application.startService(i);
		assertNotNull(serviceName);
	}
	
	@Test
	public void shouldHaveAccelerometerServiceToggleButton() {
		assertNotNull(sut.findViewById(R.id.accelerometer_button));
	}
	
	@Test
	public void shouldHaveAccelerometerServiceToggleButtonWithCorrectLabel() {
		Button accelerometerServiceButton = (Button) sut.findViewById(R.id.accelerometer_button);
		String expectedLabel = sut.getResources().getString(R.string.toggle_accelerometer);
		assertEquals(accelerometerServiceButton.getText(), expectedLabel);
	}
	
	
}
