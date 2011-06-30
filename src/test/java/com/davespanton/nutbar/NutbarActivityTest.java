package com.davespanton.nutbar;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class NutbarActivityTest {

	private NutbarActivity sut;
	
	@Before
	public void setup() {
		sut = new NutbarActivity();
		sut.onCreate(null);
	}
	
	@Test
	public void shouldHaveGPSListenerService() {
		assertNotNull(sut.service);
	}
}
