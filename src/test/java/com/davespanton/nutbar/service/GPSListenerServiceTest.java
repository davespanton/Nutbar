package com.davespanton.nutbar.service;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.davespanton.nutbar.InjectedTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowLocationManager;

@RunWith(InjectedTestRunner.class)
public class GPSListenerServiceTest {

	private GPSListenerService sut;
	private ShadowLocationManager shadow;
	
	@Before
	public void setUp() {
		sut = new GPSListenerService();
		sut.onCreate();
		shadow = (ShadowLocationManager) shadowOf(sut.loc);
	}
	
	@Test
	public void shouldHaveLocationManagerInjected() {
		Assert.assertNotNull(sut.loc);
	}
	
	@Test
	public void shouldAddListenerOnStartListening() {
		sut.startListening();
		Assert.assertTrue(shadow.hasListener(sut));
	}
	
	@Test
	public void shouldRemoveListenerOnStopListening() {
		sut.startListening();
		Assert.assertTrue(shadow.hasListener(sut));
		sut.stopListening();
		Assert.assertFalse(shadow.hasListener(sut));
	}
	
}
