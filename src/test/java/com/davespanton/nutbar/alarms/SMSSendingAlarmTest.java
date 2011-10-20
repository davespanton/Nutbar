package com.davespanton.nutbar.alarms;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.telephony.SmsManager;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowSmsManager;

@RunWith(RobolectricTestRunner.class)
public class SMSSendingAlarmTest {

	private SMSSendingAlarm sut;
	
	private ShadowSmsManager shadoSmsManager;
	
	@Before
	public void setup() {
		sut = new SMSSendingAlarm();
		shadoSmsManager = Robolectric.shadowOf(SmsManager.getDefault());
	}

	@After
	public void tearDown() {
		sut = null;
		shadoSmsManager = null;
	}
	
	@Test
	public void shouldSendSMSOnTrip() {
		sut.tripAlarm();
		
		Assert.assertNotNull(shadoSmsManager.getLastSentTextMessageParams());
	}
}
