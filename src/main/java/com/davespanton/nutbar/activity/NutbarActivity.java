package com.davespanton.nutbar.activity;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.davespanton.nutbar.R;
import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.google.inject.Inject;

public class NutbarActivity extends RoboActivity implements ListenerServiceView {

    private Button toggleAccelerometer;
    
    @Inject
    private ListenerServiceConnection gpsServiceConn;
    @Inject
    private ListenerServiceConnection accServiceConn;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        
        toggleAccelerometer = (Button) findViewById(R.id.accelerometer_button);
        toggleAccelerometer.setOnClickListener(accelerometerButtonListener);
        
        gpsServiceConn.setActivity(this);
        accServiceConn.setActivity(this);
        
        startBindListenerService(getString(R.string.start_gps_listener_service), gpsServiceConn);
        startBindListenerService(getString(R.string.start_acc_listener_service), accServiceConn);
    }
    
    private void startBindListenerService(String action, ListenerServiceConnection conn) {
    	Intent intent = new Intent();
        intent.setAction(action);
        startService(intent);
        bindService(intent, conn, 0);
    }
    
    @Override
    public void onDestroy() {
    	unbindService(accServiceConn);
    	unbindService(gpsServiceConn);
    	
    	if(shouldStopListenerServices())
    	{
    		stopListenerService(getString(R.string.start_acc_listener_service));
    		stopListenerService(getString(R.string.start_gps_listener_service));
    	}
    	
    	super.onDestroy();
    }
    
    private boolean shouldStopListenerServices() {
		return !gpsServiceConn.isListening() && !accServiceConn.isListening();
	}

	private void stopListenerService(String action) {
    	Intent intent = new Intent();
    	intent.setAction(action);
    	stopService(intent);
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
		Log.v("VIEW", "GPS connected");
	}

	@Override
	public void onGPSServiceDisconnected() {
		Log.v("VIEW", "GPS disconnected");
	}
	
    private OnClickListener accelerometerButtonListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent i = new Intent();
			
			if(accServiceConn.isListening())
				i.setAction(getString(R.string.acc_service_stop_listening));
			else
				i.setAction(getString(R.string.acc_service_start_listening));
			
			startService(i);
		}
    };
}

