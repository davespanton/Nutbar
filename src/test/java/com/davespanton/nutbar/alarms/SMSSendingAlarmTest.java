package com.davespanton.nutbar.alarms;

import android.content.SharedPreferences;
import android.telephony.SmsManager;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowSmsManager;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class SMSSendingAlarmTest {

    @Inject
	private SMSSendingAlarm smsSendingAlarm;
	
	private ShadowSmsManager shadowSmsManager;

    @Inject
    private SharedPreferences sharedPreferences;

    private final String testDestinationAddress = "01234567";
	
	@Before
	public void setup() {
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

        Assert.assertEquals(testDestinationAddress, sentTextParams.getDestinationAddress());
        Assert.assertEquals(Robolectric.application.getString(R.string.sms_alarm_body), sentTextParams.getText());
	}

    @Test
    public void shouldNotSendSMSWhenNoDestinationIsSet() {
        setupTestDestinationAddress("");
        smsSendingAlarm.tripAlarm();
        
        Assert.assertNull(shadowSmsManager.getLastSentTextMessageParams());
    }
}
