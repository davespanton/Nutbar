package com.davespanton.nutbar;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NutbarActivity extends RoboActivity {

    private static String TAG = "nutbar";
    
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

