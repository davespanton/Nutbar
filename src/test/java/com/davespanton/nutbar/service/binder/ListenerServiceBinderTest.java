package com.davespanton.nutbar.service.binder;

import static junit.framework.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.davespanton.nutbar.service.ListenerService;
import com.davespanton.nutbar.service.StubListenerService;
import com.davespanton.nutbar.service.binder.ListenerServiceBinder;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ListenerServiceBinderTest {

	private ListenerServiceBinder sut;
	private ListenerService service;
	
	@Before
	public void setup() {
		service = new StubListenerService();
		sut = new ListenerServiceBinder(service);
	}
	
	@After
	public void tearDown() {
		service = null;
		sut = null;
	}
	
	@Test
	public void shouldReflectServicesListeningState() {
		service.startListening();
		assertTrue(sut.isListening());
		service.stopListening();
		assertFalse(sut.isListening());
	}

}
