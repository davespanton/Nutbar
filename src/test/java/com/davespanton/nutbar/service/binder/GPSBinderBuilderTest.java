package com.davespanton.nutbar.service.binder;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.davespanton.nutbar.service.ListenerService;
import com.davespanton.nutbar.service.StubListenerService;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GPSBinderBuilderTest {
	
	private GPSBinderBuilder sut;
	private ListenerService service;
	
	@Before
	public void setup() {
		sut = new GPSBinderBuilder();
		service = new StubListenerService();
	}
	
	@After
	public void tearDown() {
		sut = null;
		service = null;
	}
	
	@Test
	public void shouldBuildGPSListenerServiceBinder() {
		GPSListenerServiceBinder binder = sut.build(service);
		Assert.assertNotNull(binder);
	}
	
}
