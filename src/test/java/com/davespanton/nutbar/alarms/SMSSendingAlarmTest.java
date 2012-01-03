package com.davespanton.nutbar.alarms;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.NutbarPreferenceActivity;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowSmsManager;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class SMSSendingAlarmTest {

	private SMSSendingAlarm sut;
	
	private ShadowSmsManager shadowSmsManager;

    private SharedPreferences sharedPreferences;

    private final String testDestinationAddress = "01234567";
	
	@Before
	public void setup() {
		sut = new SMSSendingAlarm(Robolectric.getShadowApplication().getApplicationContext());
		shadowSmsManager = Robolectric.shadowOf(SmsManager.getDefault());
        shadowSmsManager.clearLastSentTextMessageParams();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Robolectric.getShadowApplication().getApplicationContext());
        setupTestDestinationAddress(testDestinationAddress);
    }

    private void setupTestDestinationAddress(String address) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NutbarPreferenceActivity.SMS_ALARM_KEY, address);
        editor.commit();
    }

    @After
	public void tearDown() {
		sut = null;
		shadowSmsManager = null;
	}
	
	@Test
	public void shouldSendSMSOnTrip() {
		sut.tripAlarm();

        ShadowSmsManager.TextSmsParams sentTextParams = shadowSmsManager.getLastSentTextMessageParams();

        Assert.assertEquals(testDestinationAddress, sentTextParams.getDestinationAddress());
        Assert.assertEquals(Robolectric.application.getString(R.string.sms_alarm_body), sentTextParams.getText());
	}

    @Test
    public void shouldNotSendSMSWhenNoDestinationIsSet() {
        setupTestDestinationAddress("");
        sut.tripAlarm();
        
        Assert.assertNull(shadowSmsManager.getLastSentTextMessageParams());
    }
}
