package com.davespanton.nutbar.service;

import android.content.Intent;
import android.os.IBinder;

public class StubAccelerometerListenerService extends
		AccelerometerListenerService {

	private IBinder binder;
	
	public void setBinder(IBinder binder) {
		this.binder = binder;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		if(binder == null)
			return super.onBind(intent);
		else 
			return binder;
	}
	
	@Override
	public void updateBinder() {
		// stub
	}
}
