package com.davespanton.nutbar;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class NutbarActivity extends RoboActivity {

    private Button toggleAccelerometer;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        
        toggleAccelerometer = (Button) findViewById(R.id.accelerometer_button);
        
        startGpsService();
    }

    private void startGpsService() {
    	Intent intent = new Intent();
        intent.setAction(getString(R.string.start_gps_listener_service));
        startService(intent);
    }
    
	public void onAccelerometerServiceConnected() {
		toggleAccelerometer.setEnabled(true);
	}

	public void onAccelerometerServiceDisconnected() {
		toggleAccelerometer.setEnabled(false);
	}
    
    
}

