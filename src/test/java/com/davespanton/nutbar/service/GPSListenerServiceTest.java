package com.davespanton.nutbar.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.davespanton.nutbar.InjectedTestRunner;
import com.xtremelabs.robolectric.Robolectric;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;

@RunWith(InjectedTestRunner.class)
public class GPSListenerServiceTest {

	private GPSListenerService sut;
	private ShadowNutLocationManager shadow;
	
	@Before
	public void setUp() {
		Robolectric.bindShadowClass(ShadowNutLocationManager.class);
		sut = new GPSListenerService();
		sut.onCreate();
		shadow = (ShadowNutLocationManager) shadowOf(sut.loc);
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
