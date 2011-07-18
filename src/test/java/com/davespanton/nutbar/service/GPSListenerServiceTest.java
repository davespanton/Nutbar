package com.davespanton.nutbar.service;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowLocationManager;

@RunWith(RobolectricTestRunner.class)
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
		assertNotNull(sut.loc);
	}
	
	@Test
	public void shouldAddListenerOnStartListening() {
		sut.startListening();
		assertTrue(shadow.hasListener(sut));
	}
	
	@Test
	public void shouldRemoveListenerOnStopListening() {
		sut.startListening();
		assertTrue(shadow.hasListener(sut));
		sut.stopListening();
		assertFalse(shadow.hasListener(sut));
	}
	
	@Test
	public void shouldReturnIsListeningWhenListening() {
		sut.startListening();
		assertTrue(sut.isListening());
	}
	
	@Test
	public void shouldReturnIsNotListeningWhenNotListening() {
		sut.startListening();
		sut.stopListening();
		assertFalse(sut.isListening());
	}
	
}
