package com.davespanton.nutbar.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.davespanton.nutbar.R;
import com.xtremelabs.robolectric.tester.android.content.TestSharedPreferences;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.preference.Preference;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowPreferenceScreen;

@RunWith(RobolectricTestRunner.class)
public class NutbarPreferenceActivityTest {

    private static final String TEST_SMS_ALARM_VALUE = "test_sms_alarm_key";

	private NutbarPreferenceActivity preferencesActivity;
	
	@Before
	public void setup() {
		preferencesActivity = new NutbarPreferenceActivity();
	}
	
	@Test
	public void shouldHaveEditTextForSmsAlarmNumber() {
		preferencesActivity.onCreate(null);

        Preference smsAlarmNumber = getSmsAlarmPreferenceFromActivity();
		
		Assert.assertNotNull(smsAlarmNumber);
	}

    private Preference getSmsAlarmPreferenceFromActivity() {
        ShadowPreferenceScreen prefScreen = Robolectric.shadowOf(preferencesActivity.getPreferenceScreen());
        return prefScreen.findPreference(NutbarPreferenceActivity.SMS_ALARM_KEY);
    }

    @Test
    public void shouldSetSmsAlarmSummaryToExistingSharedPreference() {
        updateSharedPreference(NutbarPreferenceActivity.SMS_ALARM_KEY, TEST_SMS_ALARM_VALUE);

        preferencesActivity.onCreate(null);

        Preference smsAlarmNumber = getSmsAlarmPreferenceFromActivity();
        Assert.assertTrue(smsAlarmNumber.getSummary().equals(TEST_SMS_ALARM_VALUE));
    }

    private void updateSharedPreference(String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Robolectric.getShadowApplication().getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(key, value);
        editor.commit();
    }

    @Test
    public void shouldLeaveSmsAlarmSummaryWhenNoSharedPreference() {
        preferencesActivity.onCreate(null);

        Preference smsAlarmNumber = getSmsAlarmPreferenceFromActivity();
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        Assert.assertTrue(smsAlarmNumber.getSummary().equals(context.getString(R.string.sms_alarm_no_number)));
    }

    @Test
    public void shouldUpdateSmsAlarmSummaryOnPreferenceChange() {
        preferencesActivity.onCreate(null);
        Preference smsAlarmNumber = getSmsAlarmPreferenceFromActivity();
        updateSharedPreference(NutbarPreferenceActivity.SMS_ALARM_KEY, TEST_SMS_ALARM_VALUE);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Robolectric.getShadowApplication().getApplicationContext());
        preferencesActivity.onSharedPreferenceChanged(prefs, NutbarPreferenceActivity.SMS_ALARM_KEY);

        Assert.assertEquals(TEST_SMS_ALARM_VALUE, smsAlarmNumber.getSummary());
    }

    @Test
    public void shouldReturnSmsAlarmSummaryToDefaultWhenSetToNothing() {
        preferencesActivity.onCreate(null);
        Preference smsAlarmNumber = getSmsAlarmPreferenceFromActivity();
        updateSharedPreference(NutbarPreferenceActivity.SMS_ALARM_KEY, "");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Robolectric.getShadowApplication().getApplicationContext());
        preferencesActivity.onSharedPreferenceChanged(prefs, NutbarPreferenceActivity.SMS_ALARM_KEY);

        Assert.assertEquals(preferencesActivity.getString(R.string.sms_alarm_no_number), smsAlarmNumber.getSummary());

    }
}
