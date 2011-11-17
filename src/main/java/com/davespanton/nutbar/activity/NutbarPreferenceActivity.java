package com.davespanton.nutbar.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import android.preference.PreferenceManager;
import com.davespanton.nutbar.R;

public class NutbarPreferenceActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String SMS_ALARM_KEY = "sms_alarm_key";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);

        updateSummaryToStoredSmsAlarmNumber();

        registerOnPreferenceChangeListener();
	}

    private void registerOnPreferenceChangeListener() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void updateSummaryToStoredSmsAlarmNumber() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String currentValue = preferences.getString(NutbarPreferenceActivity.SMS_ALARM_KEY, "");
        Preference pref = getPreferenceScreen().findPreference(SMS_ALARM_KEY);

        if(currentValue.equals("")) {
            pref.setSummary(getString(R.string.sms_alarm_no_number));
            return;
        }

        pref.setSummary(currentValue);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(SMS_ALARM_KEY)) {
            Preference pref = getPreferenceScreen().findPreference(SMS_ALARM_KEY);
            String newValue = sharedPreferences.getString(key, getString(R.string.sms_alarm_no_number));
            if(newValue.equals(""))
                newValue = getString(R.string.sms_alarm_no_number);
            pref.setSummary(newValue);
        }
    }

    @Override
    public void onDestroy() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}
