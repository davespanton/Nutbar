package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.service.ListenerService;
import com.davespanton.nutbar.service.StubListenerService;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

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
