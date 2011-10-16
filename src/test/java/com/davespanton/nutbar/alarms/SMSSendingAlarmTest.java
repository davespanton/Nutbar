package com.davespanton.nutbar.alarms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SMSSendingAlarmTest {

	private SMSSendingAlarm sut;
	
	@Before
	public void setup() {
		sut = new SMSSendingAlarm();
	}

	@After
	public void tearDown() {
		sut = null;
	}
	
	@Test
	public void shouldSendSMSOrSomething() {
		sut.tripAlarm();
		//Assert.fail("doesn't send sms or something");
	}
}
