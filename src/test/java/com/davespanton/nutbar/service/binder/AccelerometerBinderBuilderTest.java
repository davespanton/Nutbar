package com.davespanton.nutbar.service.binder;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.davespanton.nutbar.service.AccelerometerListenerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(InjectedTestRunner.class)
public class AccelerometerBinderBuilderTest {

	private AccelerometerBinderBuilder sut;
	private AccelerometerListenerService service;
	
	@Before
	public void setup() {
		sut = new AccelerometerBinderBuilder();
		service = new AccelerometerListenerService();
	}
	
	@After
	public void tearDown() {
		sut = null;
		service = null;
	}
	
	@Test
	public void shouldBuildAccelerometerListenerServiceBinder() {
		AccelerometerListenerServiceBinder binder = sut.build(service);
		assertNotNull(binder);
	}
}
