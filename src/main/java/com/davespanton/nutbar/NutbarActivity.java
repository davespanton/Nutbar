package com.davespanton.nutbar;

import roboguice.activity.RoboActivity;
import android.os.Bundle;
import android.util.Log;

import com.davespanton.nutbar.service.GPSListenerService;
import com.google.inject.Inject;

public class NutbarActivity extends RoboActivity {

    private static String TAG = "nutbar";
    
    @Inject GPSListenerService service;
    
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
    }
}

