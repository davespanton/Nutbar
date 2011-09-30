package com.davespanton.nutbar.activity;

public interface ListenerServiceView {
	public void onAccelerometerServiceConnected();
	public void onAccelerometerServiceDisconnected();
	
	public void onArmed();
	public void onDisarmed();
	public void onTripped();
}
