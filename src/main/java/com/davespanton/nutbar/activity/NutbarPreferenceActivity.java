package com.davespanton.nutbar.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.davespanton.nutbar.R;

public class NutbarPreferenceActivity extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
	}
}
