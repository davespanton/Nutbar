package com.davespanton.nutbar.service;

import android.content.Intent;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.alarms.LocationAlarm;
import com.davespanton.nutbar.alarms.SMSSendingAlarm;
import com.davespanton.nutbar.alarms.StubLocationAlarm;
import com.davespanton.nutbar.alarms.StubSmsSendingAlarm;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.*;

@RunWith(InjectedTestRunner.class)
public class AlarmServiceTest {

	private static final int EXPECTED_TRIP_COUNT = 1;

	private AlarmService alarmService;
	
	@Inject
	private SMSSendingAlarm smsAlarm;

    @Inject
    private LocationAlarm locationAlarm;
	
	@Before
	public void setup() {
		alarmService = new AlarmService();
		alarmService.onCreate();
	}
	
	@After
	public void tearDown() {
		alarmService = null;
		smsAlarm = null;
	}
	
	@Test
	public void shouldTripSmsSendingAlarmOnCorrectStartCommand() {
		Intent intent = new Intent();
		intent.setAction(Robolectric.application.getString(R.string.alarm_service_trip));
		alarmService.onStartCommand(intent, 0, 0);
		
		assertEquals(EXPECTED_TRIP_COUNT, ((StubSmsSendingAlarm) smsAlarm).getTripCount());
	}

    @Test
    public void shouldAddListenerToLocationAlarmOnCreate() {
        alarmService.onCreate();
        assertNotNull(((StubLocationAlarm) locationAlarm).getOnLocationChangeLocationListener());
    }
}
