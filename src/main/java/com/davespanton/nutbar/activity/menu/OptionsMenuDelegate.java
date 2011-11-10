package com.davespanton.nutbar.activity.menu;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.davespanton.nutbar.R;

public class OptionsMenuDelegate {

	private Activity activity;
	
	public boolean populateOptionsMenu(Activity activity, Menu menu) {
		this.activity = activity;
		
		MenuInflater inflator = activity.getMenuInflater();
		inflator.inflate(R.menu.application_options, menu);
		
		return true;
	}

	public void onOptionsItemSelected(MenuItem item) {
		if(activity == null)
			throw new Error("onOptionsItemSelected called before populateOptionsMenu");
		
		switch(item.getItemId()) {
			case R.id.settings:
				launchSettingsActivity();
			break;
		}
	}

	private void launchSettingsActivity() {
		Intent i = new Intent(activity.getString(R.string.start_preference_activity));
		activity.startActivity(i);
	}
}
