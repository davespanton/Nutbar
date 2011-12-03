package com.davespanton.nutbar.activity;

public class StubListenerServiceView implements ListenerServiceView {
	
	private enum States { armed, disarmed, tripped };
	
	private boolean isAccConnected = false;
	private States state;
	
	@Override
	public void onAccelerometerServiceConnected() {
		isAccConnected = true;
	}

	@Override
	public void onAccelerometerServiceDisconnected() {
		isAccConnected = false;
	}
	
	public boolean isAccelerometerServiceConnected() {
		return isAccConnected;
	}

	@Override
	public void onArmed() {
		state = States.armed;
	}

	@Override
	public void onDisarmed() {
		state = States.disarmed;
	}

	@Override
	public void onTripped() {
		state = States.tripped;	
	}
	
	public boolean isArmed() {
		return state == States.armed;
	}
	
	public boolean isDisarmed() {
		return state == States.disarmed;
	}
	
	public boolean isTripped() {
		return state == States.tripped;
	}
}
