package com.davespanton.nutbar.service.binder;

import static junit.framework.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.davespanton.nutbar.service.StubListenerService;

@RunWith(InjectedTestRunner.class)
public class AccelerometerBinderBuilderTest {

	private AccelerometerBinderBuilder sut;
	private StubListenerService service;
	
	@Before
	public void setup() {
		sut = new AccelerometerBinderBuilder();
		service = new StubListenerService();
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
