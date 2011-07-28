package com.davespanton.nutbar;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NutbarActivity extends RoboActivity {

    private Button toggleAccelerometer;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        
        toggleAccelerometer = (Button) findViewById(R.id.accelerometer_button);
        toggleAccelerometer.setOnClickListener(accelerometerButtonListener);
        
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
    
    private OnClickListener accelerometerButtonListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			
		}
    };
}

