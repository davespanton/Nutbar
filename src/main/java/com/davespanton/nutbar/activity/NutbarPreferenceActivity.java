package com.davespanton.nutbar.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import com.davespanton.nutbar.R;
import roboguice.activity.RoboPreferenceActivity;

public class NutbarPreferenceActivity extends RoboPreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String SMS_ALARM_KEY = "sms_alarm_key";

    private SharedPreferences sharedPreferences = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(getSharedPreferenceName(), Context.MODE_PRIVATE);

		addPreferencesFromResource(R.xml.preferences);

        updateSummaryToStoredSmsAlarmNumber();

        registerOnPreferenceChangeListener();
    }

    private void registerOnPreferenceChangeListener() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    private void updateSummaryToStoredSmsAlarmNumber() {
        String currentValue = sharedPreferences.getString(NutbarPreferenceActivity.SMS_ALARM_KEY, "");
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
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    public String getSharedPreferenceName() {
        return getPackageName() + "_preferences";
    }
}
