package com.davespanton.nutbar.activity;

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

	private NutbarPreferenceActivity preferencesActivity;
	
	@Before
	public void setup() {
		preferencesActivity = new NutbarPreferenceActivity();
		preferencesActivity.onCreate(null);
	}
	
	@Test
	public void shouldHaveEditTextForSmsAlarmNumber() {
		//String smsAlarmKey = preferencesActivity.getString(R.string.pref_sms_alarm_number);
		ShadowPreferenceScreen prefScreen = Robolectric.shadowOf(preferencesActivity.getPreferenceScreen());
		
		//should use smsAlarmKey commented out above. issue in robolectric?
		Preference smsAlarmNumber = prefScreen.findPreference("@string/pref_sms_alarm_number");
		
		Assert.assertNotNull(smsAlarmNumber);
	}
}
