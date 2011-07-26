package com.davespanton.nutbar.service.connection;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.davespanton.nutbar.NutbarActivity;

public class ListenerServiceConnection implements ServiceConnection {

	private NutbarActivity activity;
	
	public ListenerServiceConnection(NutbarActivity nutbar) {
		activity = nutbar;
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder binder) {
		//TODO switch this to template pattern via binders
		if(name.getClassName() == "AccelerometerListenerService")
			activity.onAccelerometerServiceConnected();
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		if(name.getClassName() == "AccelerometerListenerService")
			activity.onAccelerometerServiceDisconnected();
	}

}
