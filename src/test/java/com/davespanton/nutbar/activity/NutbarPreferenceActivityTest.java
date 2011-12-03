package com.davespanton.nutbar.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowPreferenceScreen;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectedTestRunner.class)
public class NutbarPreferenceActivityTest {

    private static final String TEST_SMS_ALARM_VALUE = "test_sms_alarm_key";

    @Inject
	private NutbarPreferenceActivity preferencesActivity;

    private SharedPreferences sharedPreferences;

    @Before
    public void setup() {
        sharedPreferences = preferencesActivity.getSharedPreferences(preferencesActivity.getSharedPreferenceName(), Context.MODE_PRIVATE);
    }

    @After
    public void tearDown() {
        preferencesActivity = null;
        sharedPreferences = null;
    }
    
	@Test
	public void shouldHaveEditTextForSmsAlarmNumber() {
		preferencesActivity.onCreate(null);

        Preference smsAlarmNumber = getPreferenceFromActivity(NutbarPreferenceActivity.SMS_ALARM_KEY);
		
		Assert.assertNotNull(smsAlarmNumber);
	}

    private Preference getPreferenceFromActivity(String preferenceKey) {
        ShadowPreferenceScreen prefScreen = Robolectric.shadowOf(preferencesActivity.getPreferenceScreen());
        return prefScreen.findPreference(preferenceKey);
    }

    @Test
    public void shouldHaveUsernameEditText() {
        preferencesActivity.onCreate(null);

        Preference username = getPreferenceFromActivity(NutbarPreferenceActivity.USERNAME_KEY);

        Assert.assertNotNull(username);
    }
    
    @Test
    public void shouldSetSmsAlarmSummaryToExistingSharedPreference() {
        updateSharedPreference(NutbarPreferenceActivity.SMS_ALARM_KEY, TEST_SMS_ALARM_VALUE);

        preferencesActivity.onCreate(null);

        Preference smsAlarmNumber = getPreferenceFromActivity(NutbarPreferenceActivity.SMS_ALARM_KEY);
        Assert.assertTrue(smsAlarmNumber.getSummary().equals(TEST_SMS_ALARM_VALUE));
    }

    private void updateSharedPreference(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);
        editor.commit();
    }

    @Test
    public void shouldLeaveSmsAlarmSummaryWhenNoSharedPreference() {
        preferencesActivity.onCreate(null);

        Preference smsAlarmNumber = getPreferenceFromActivity(NutbarPreferenceActivity.SMS_ALARM_KEY);
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        Assert.assertTrue(smsAlarmNumber.getSummary().equals(context.getString(R.string.sms_alarm_no_number)));
    }

    @Test
    public void shouldUpdateSmsAlarmSummaryOnPreferenceChange() {
        preferencesActivity.onCreate(null);
        Preference smsAlarmNumber = getPreferenceFromActivity(NutbarPreferenceActivity.SMS_ALARM_KEY);
        updateSharedPreference(NutbarPreferenceActivity.SMS_ALARM_KEY, TEST_SMS_ALARM_VALUE);

        preferencesActivity.onSharedPreferenceChanged(sharedPreferences, NutbarPreferenceActivity.SMS_ALARM_KEY);

        Assert.assertEquals(TEST_SMS_ALARM_VALUE, smsAlarmNumber.getSummary());
    }

    @Test
    public void shouldReturnSmsAlarmSummaryToDefaultWhenSetToNothing() {
        preferencesActivity.onCreate(null);
        Preference smsAlarmNumber = getPreferenceFromActivity(NutbarPreferenceActivity.SMS_ALARM_KEY);
        updateSharedPreference(NutbarPreferenceActivity.SMS_ALARM_KEY, "");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Robolectric.getShadowApplication().getApplicationContext());
        preferencesActivity.onSharedPreferenceChanged(prefs, NutbarPreferenceActivity.SMS_ALARM_KEY);

        Assert.assertEquals(preferencesActivity.getString(R.string.sms_alarm_no_number), smsAlarmNumber.getSummary());
    }
}