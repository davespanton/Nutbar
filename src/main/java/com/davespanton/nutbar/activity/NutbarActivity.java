package com.davespanton.nutbar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.menu.OptionsMenuDelegate;
import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;

public class NutbarActivity extends RoboActivity implements ListenerServiceView {

    private Button toggleAccelerometer;

    @Inject
    private ListenerServiceConnection accServiceConn;
    
    @Inject
    private OptionsMenuDelegate optionsMenuDelegate;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        
        toggleAccelerometer = (Button) findViewById(R.id.accelerometer_button);
        toggleAccelerometer.setOnClickListener(accelerometerButtonListener);

        accServiceConn.setActivity(this);
        
        startBindListenerService(getString(R.string.start_acc_listener_service), accServiceConn);

        startService(new Intent(getString(R.string.start_xmpp_service)));
    }
    
	private void startBindListenerService(String action, ListenerServiceConnection conn) {
    	Intent intent = new Intent();
        intent.setAction(action);
        startService(intent);
        bindService(intent, conn, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(!accServiceConn.isListening())
            finish();
    }

    @Override
    public void onDestroy() {
    	unbindService(accServiceConn);

    	if(!accServiceConn.isListening()) {
    		stopService(new Intent(getString(R.string.start_acc_listener_service)));
            stopService(new Intent(getString(R.string.start_xmpp_service)));
        }

    	super.onDestroy();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return optionsMenuDelegate.populateOptionsMenu(this, menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		optionsMenuDelegate.onOptionsItemSelected(item);
		return true;
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
	public void onArmed() {
		((TextView) findViewById(R.id.status_text)).setText(R.string.armed);
        ((Button) findViewById(R.id.accelerometer_button)).setText(R.string.disarm);
	}
	
	@Override
	public void onDisarmed() {
		((TextView) findViewById(R.id.status_text)).setText(R.string.disarmed);
        ((Button) findViewById(R.id.accelerometer_button)).setText(R.string.arm);
	}
	
	@Override
	public void onTripped() {
		((TextView) findViewById(R.id.status_text)).setText(R.string.tripped);
        ((Button) findViewById(R.id.accelerometer_button)).setText(R.string.reset);
	}
	
    private OnClickListener accelerometerButtonListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent i = new Intent();
			
			if(accServiceConn.isListening()) {
				i.setAction(getString(R.string.acc_service_stop_listening));
            }
			else {
				i.setAction(getString(R.string.acc_service_start_listening));
            }
			
			startService(i);
		}
    };
}

