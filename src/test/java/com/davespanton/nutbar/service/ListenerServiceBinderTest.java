package com.davespanton.nutbar.service;

import static junit.framework.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
	
	private class TestListenerService implements ListenerService {

		@Override
		public boolean isListening() {
			return false;
		}

		@Override
		public void startListening() {
		}

		@Override
		public void stopListening() {
		}
		
	}
}
