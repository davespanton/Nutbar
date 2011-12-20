package com.davespanton.nutbar.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
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
    private static final String TEST_USERNAME = "test_user";

    @Inject
	private NutbarPreferenceActivity preferencesActivity;

    private SharedPreferences sharedPreferences;
    private Bundle createBundle;

    @Before
    public void setup() {
        sharedPreferences = preferencesActivity.getSharedPreferences(preferencesActivity.getString(R.string.shared_preferences_package), Context.MODE_PRIVATE);
        createBundle = new Bundle();
    }

    @After
    public void tearDown() {
        preferencesActivity = null;
        sharedPreferences = null;
        createBundle = null;
    }
    
	@Test
	public void shouldHaveEditTextForSmsAlarmNumber() {
		preferencesActivity.onCreate(createBundle);
        assertPreferenceIsNotNull(NutbarPreferenceActivity.SMS_ALARM_KEY);
	}

    private void assertPreferenceIsNotNull(String preferenceKey) {
        Preference preference = getPreferenceFromActivity(preferenceKey);
        Assert.assertNotNull(preference);
    }

    private Preference getPreferenceFromActivity(String preferenceKey) {
        ShadowPreferenceScreen prefScreen = Robolectric.shadowOf(preferencesActivity.getPreferenceScreen());
        return prefScreen.findPreference(preferenceKey);
    }

    @Test
    public void shouldHaveUsernameEditText() {
        preferencesActivity.onCreate(createBundle);
        assertPreferenceIsNotNull(NutbarPreferenceActivity.USERNAME_KEY);
    }

    @Test
    public void shouldHavePasswordEditTest() {
        preferencesActivity.onCreate(createBundle);
        assertPreferenceIsNotNull(NutbarPreferenceActivity.PASSWORD_KEY);
    }
    
    @Test
    public void shouldSetSmsAlarmSummaryToExistingSharedPreference() {
        assertPrefSummaryIsSetToExistingSharedPref(NutbarPreferenceActivity.SMS_ALARM_KEY, TEST_SMS_ALARM_VALUE);
    }

    private void updateSharedPreference(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);
        editor.commit();
    }

    private void assertPrefSummaryIsSetToExistingSharedPref(String prefKey, String expected) {
        updateSharedPreference(prefKey, expected);
        preferencesActivity.onCreate(createBundle);

        Preference pref = getPreferenceFromActivity(prefKey);
        Assert.assertTrue(pref.getSummary().equals(expected));
    }

    @Test
    public void shouldUpdateXMPPUsernameToExistingSharedPreference() {
        assertPrefSummaryIsSetToExistingSharedPref(NutbarPreferenceActivity.USERNAME_KEY, TEST_USERNAME);
    }

    @Test
    public void shouldLeaveSmsAlarmSummaryDefaultWhenNoSharedPreference() {
        preferencesActivity.onCreate(createBundle);

        Preference smsAlarmNumber = getPreferenceFromActivity(NutbarPreferenceActivity.SMS_ALARM_KEY);
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        Assert.assertTrue(smsAlarmNumber.getSummary().equals(context.getString(R.string.sms_alarm_no_number)));
    }

    @Test
    public void shouldUpdateSmsAlarmSummaryOnPreferenceChange() {
        preferencesActivity.onCreate(createBundle);
        assertPrefSummaryUpdatesWhenPrefChanges(NutbarPreferenceActivity.SMS_ALARM_KEY, TEST_SMS_ALARM_VALUE, TEST_SMS_ALARM_VALUE);
    }

    @Test
    public void shouldUpdateXMPPUsernameOnPreferenceChange() {
        preferencesActivity.onCreate(createBundle);
        assertPrefSummaryUpdatesWhenPrefChanges(NutbarPreferenceActivity.USERNAME_KEY, TEST_USERNAME, TEST_USERNAME);
    }

    private void assertPrefSummaryUpdatesWhenPrefChanges(String prefKey, String value, String expected) {
        Preference pref = getPreferenceFromActivity(prefKey);
        updateSharedPreference(prefKey, value);
        preferencesActivity.onSharedPreferenceChanged(sharedPreferences, prefKey);
        Assert.assertEquals(expected, pref.getSummary());
    }

    @Test
    public void shouldReturnSmsAlarmSummaryToDefaultWhenSetToNothing() {
        preferencesActivity.onCreate(createBundle);
        assertPrefSummaryUpdatesWhenPrefChanges(NutbarPreferenceActivity.SMS_ALARM_KEY, "", preferencesActivity.getString(R.string.sms_alarm_no_number));
    }

    @Test
    public void shouldReturnUsernameSummaryToDefaultWhenSetToNothing() {
        preferencesActivity.onCreate(createBundle);
        assertPrefSummaryUpdatesWhenPrefChanges(NutbarPreferenceActivity.USERNAME_KEY, "", preferencesActivity.getString(R.string.username_not_set));
    }
}