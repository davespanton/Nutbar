package com.davespanton.nutbar;

import static com.xtremelabs.robolectric.Robolectric.getShadowApplication;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.widget.Button;

import com.davespanton.nutbar.activity.NutbarActivity;
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
	public void shouldStartListenerServicesOnCreate() {
		Intent intent = getNextStartedService();
		assertEquals(intent.getAction(), getShadowApplication().getString(R.string.start_gps_listener_service));
		
		intent = getNextStartedService();
		assertEquals(intent.getAction(), getShadowApplication().getString(R.string.start_acc_listener_service));
	}
	
	private Intent getNextStartedService() {
		ShadowActivity shadow = shadowOf(sut);
		return shadow.getNextStartedService();
	}
	
	@Test
	public void shouldHaveAccelerometerServiceToggleButton() {
		assertNotNull(sut.findViewById(R.id.accelerometer_button));
	}
	
	@Test
	public void shouldHaveButtonDisabledByDefault() {
		assertFalse(getAccelerometerButton().isEnabled());
	}
	
	private Button getAccelerometerButton() {
		return (Button) sut.findViewById(R.id.accelerometer_button);
	}
	
	@Test
	public void shouldHaveAccelerometerServiceToggleButtonWithCorrectLabel() {
		String expectedLabel = sut.getResources().getString(R.string.toggle_accelerometer);
		assertEquals(getAccelerometerButton().getText(), expectedLabel);
	}
	
	@Test 
	public void shouldEnableButtonOnAccelerometerServiceConnected() {
		sut.onAccelerometerServiceConnected();
		assertTrue(getAccelerometerButton().isEnabled());
	}
	
	@Test
	public void shouldDisableButtonOnAccelerometerServiceDisconnected() {
		sut.onAccelerometerServiceConnected();
		sut.onAccelerometerServiceDisconnected();
		assertFalse(getAccelerometerButton().isEnabled());
	}
}
