package com.davespanton.nutbar;

import static com.xtremelabs.robolectric.Robolectric.getShadowApplication;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;

import com.xtremelabs.robolectric.shadows.ShadowActivity;

@RunWith(InjectedTestRunner.class)
public class NutbarActivityTest {

	private NutbarActivity sut;
	
	@Before
	public void setup() {
		sut = new NutbarActivity();
		sut.onCreate(null);
	}
	
	@Test
	public void shouldHaveGPSListenerService() {
		assertNotNull(sut.service);
	}
	
	@Test
	public void shouldStartServiceOnCreate() {
		ShadowActivity shadow = shadowOf(sut);
		Intent intent = shadow.peekNextStartedService();
		assertEquals(intent.getAction(), getShadowApplication().getString(R.string.start_gps_listener_service));
	}
	
}
