package com.davespanton.nutbar;

import com.davespanton.nutbar.activity.ListenerServiceView;

public class StubListenerServiceView implements ListenerServiceView {
	
	private boolean isAccConnected = false;
	
	private boolean isGPSConnected = false;
	
	@Override
	public void onAccelerometerServiceConnected() {
		isAccConnected = true;
	}

	@Override
	public void onAccelerometerServiceDisconnected() {
		isAccConnected = false;
	}

	@Override
	public void onGPSServiceConnected() {
		isGPSConnected = true;
	}

	@Override
	public void onGPSServiceDisconnected() {
		isGPSConnected = false;
	}
	
	public boolean isAccelerometerServiceConnected() {
		return isAccConnected;
	}
	
	public boolean isGPSServiceConnected() {
		return isGPSConnected;
	}
}
