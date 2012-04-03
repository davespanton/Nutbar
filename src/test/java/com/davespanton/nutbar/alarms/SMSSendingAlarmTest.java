package com.davespanton.nutbar.alarms;

import static junit.framework.Assert.*;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.davespanton.nutbar.alarms.factory.SMSSendingAlarmFactory;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowPendingIntent;
import com.xtremelabs.robolectric.shadows.ShadowSmsManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class SMSSendingAlarmTest {

    private SMSSendingAlarm smsSendingAlarm;

    @Inject
    private SMSSendingAlarmFactory smsSendingAlarmFactory;


	private ShadowSmsManager shadowSmsManager;

    @Inject
    private SharedPreferences sharedPreferences;

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
		PendingIntent pendingIntent = getPendingIntent();
        smsSendingAlarm.tripAlarm(pendingIntent);

        ShadowSmsManager.TextSmsParams sentTextParams = shadowSmsManager.getLastSentTextMessageParams();

        assertEquals(testDestinationAddress, sentTextParams.getDestinationAddress());
        assertEquals(Robolectric.application.getString(R.string.sms_alarm_body), sentTextParams.getText());
        assertEquals(pendingIntent, sentTextParams.getSentIntent());
	}

    private PendingIntent getPendingIntent() {
        Context context = Robolectric.application.getApplicationContext();
        return ShadowPendingIntent.getService(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Test
    public void shouldNotSendSMSWhenNoDestinationIsSet() {
        setupTestDestinationAddress("");
        smsSendingAlarm.tripAlarm(getPendingIntent());
        
        assertNull(shadowSmsManager.getLastSentTextMessageParams());
    }
}
