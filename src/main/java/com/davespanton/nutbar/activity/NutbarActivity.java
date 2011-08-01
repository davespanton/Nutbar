package com.davespanton.nutbar.activity;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.davespanton.nutbar.R;

public class NutbarActivity extends RoboActivity implements ListenerServiceView {

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
    
    @Override
	public void onAccelerometerServiceConnected() {
		toggleAccelerometer.setEnabled(true);
	}
	
	@Override
	public void onAccelerometerServiceDisconnected() {
		toggleAccelerometer.setEnabled(false);
	}

	@Override
	public void onGPSServiceConnected() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onGPSServiceDisconnected() {
		// TODO Auto-generated method stub
	}
	
    private OnClickListener accelerometerButtonListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			
		}
    };
}

