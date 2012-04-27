package com.davespanton.nutbar.alarms;

import static junit.framework.Assert.*;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.davespanton.nutbar.alarms.broadcastreceiver.ReTripReceiver;
import com.davespanton.nutbar.alarms.factory.SMSSendingAlarmFactory;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowApplication;
import com.xtremelabs.robolectric.shadows.ShadowPendingIntent;
import com.xtremelabs.robolectric.shadows.ShadowSmsManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import roboguice.inject.InjectResource;

import java.sql.Wrapper;
import java.util.List;

@RunWith(InjectedTestRunner.class)
public class SMSSendingAlarmTest {

    private SMSSendingAlarm smsSendingAlarm;

    @Inject
    private SMSSendingAlarmFactory smsSendingAlarmFactory;

	private ShadowSmsManager shadowSmsManager;

    @Inject
    private SharedPreferences sharedPreferences;

    @Inject
    private ReTripReceiver reTripReceiver;

    @InjectResource(R.string.sms_failed)
    private String failedSMSAction;

    private final String testDestinationAddress = "01234567";
	
	@Before
	public void setup() {
		smsSendingAlarm = smsSendingAlarmFactory.create(Robolectric.getShadowApplication().getApplicationContext());
        shadowSmsManager = Robolectric.shadowOf(SmsManager.getDefault());
        shadowSmsManager.clearLastSentTextMessageParams();
        setupTestDestinationAddress(testDestinationAddress);
    }

    private void setupTestDestinationAddress(String address) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NutbarPreferenceActivity.SMS_ALARM_KEY, address);
        editor.commit();
    }

    @After
	public void tearDown() {
		smsSendingAlarm = null;
		shadowSmsManager = null;
	}

	@Test
	public void shouldSendSMSOnTrip() {
		smsSendingAlarm.tripAlarm();

        ShadowSmsManager.TextSmsParams sentTextParams = shadowSmsManager.getLastSentTextMessageParams();

        assertEquals(testDestinationAddress, sentTextParams.getDestinationAddress());
        assertEquals(Robolectric.application.getString(R.string.sms_alarm_body), sentTextParams.getText());
        assertNotNull(sentTextParams.getSentIntent());
	}

    @Test
    public void shouldNotSendSMSWhenNoDestinationIsSet() {
        setupTestDestinationAddress("");
        smsSendingAlarm.tripAlarm();
        
        assertNull(shadowSmsManager.getLastSentTextMessageParams());
    }

    @Test
    public void shouldRegisterBroadcastReceiverForFailedSMSOnTrip() {
        smsSendingAlarm.tripAlarm();

        String action = getReceivers().get(0).intentFilter.getAction(0);

        assertEquals(failedSMSAction, action);
    }

    private List<ShadowApplication.Wrapper> getReceivers() {
        return Robolectric.getShadowApplication().getRegisteredReceivers();
    }

    @Test
    public void shouldRegisterRetripReceiverOnTrip() {
        smsSendingAlarm.tripAlarm();

        assertEquals(reTripReceiver, getReceivers().get(0).broadcastReceiver);
    }

    @Test
    public void shouldRemoveRetripReceiverOnReset() {
        smsSendingAlarm.tripAlarm();
        smsSendingAlarm.resetAlarm();

        assertTrue(getReceivers().isEmpty());
    }

    @Test
    public void shouldCancelRetripWhenReset() {
        Robolectric.pauseMainLooper();

        smsSendingAlarm.tripAlarm();
        reTripReceiver.onReceive(Robolectric.getShadowApplication().getApplicationContext(), new Intent());
        smsSendingAlarm.resetAlarm();

        Robolectric.idleMainLooper(ReTripReceiver.RE_TRIP_DELAY + 1);

        assertFalse(smsSendingAlarm.isListening());
    }
}
