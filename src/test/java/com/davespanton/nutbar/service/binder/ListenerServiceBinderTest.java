package com.davespanton.nutbar.service.binder;

import static junit.framework.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.davespanton.nutbar.service.ListenerService;
import com.davespanton.nutbar.service.TestListenerService;
import com.davespanton.nutbar.service.binder.ListenerServiceBinder;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ListenerServiceBinderTest {

	private ListenerServiceBinder sut;
	private ListenerService service;
	
	@Before
	public void setup() {
		service = new TestListenerService();
		sut = new ListenerServiceBinder(service);
	}
	
	@After
	public void tearDown() {
		service = null;
		sut = null;
	}
	
	@Test
	public void shouldReturnCorrectService() {
		assertEquals(service, sut.getService());
	}
}
