package com.davespanton.nutbar;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.davespanton.nutbar.service.ListenerService;
import com.google.inject.Inject;

public class NutbarActivity extends RoboActivity {

    private static String TAG = "nutbar";
    
    @Inject ListenerService service;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        
        Intent intent = new Intent();
        intent.setAction(getString(R.string.start_gps_listener_service));
        startService(intent);
    }
}

