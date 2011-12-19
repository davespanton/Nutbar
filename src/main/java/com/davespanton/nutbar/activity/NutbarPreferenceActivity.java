package com.davespanton.nutbar.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import com.davespanton.nutbar.R;
import roboguice.activity.RoboPreferenceActivity;

public class NutbarPreferenceActivity extends RoboPreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String SMS_ALARM_KEY = "sms_alarm_key";
    public static final String USERNAME_KEY = "username_key";
    public static final String PASSWORD_KEY = "password_key";

    private SharedPreferences sharedPreferences = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_package), Context.MODE_PRIVATE);

		addPreferencesFromResource(R.xml.preferences);

        updateSummaryToStoredSmsAlarmNumber();

        registerOnPreferenceChangeListener();
    }

    private void registerOnPreferenceChangeListener() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    private void updateSummaryToStoredSmsAlarmNumber() {
        String newValue = sharedPreferences.getString(SMS_ALARM_KEY, getString(R.string.sms_alarm_no_number));
        Preference pref = getPreferenceScreen().findPreference(SMS_ALARM_KEY);

        if(newValue.equals("")) {
            pref.setSummary(getString(R.string.sms_alarm_no_number));
            return;
        }

        pref.setSummary(newValue);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(SMS_ALARM_KEY))
            updateSummaryToStoredSmsAlarmNumber();

    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}
