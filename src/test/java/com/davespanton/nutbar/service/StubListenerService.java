package com.davespanton.nutbar.service;

public class StubListenerService implements ListenerService {
	
	private boolean isListening = false;
	
	@Override
	public boolean isListening() {
		return isListening;
	}

	@Override
	public void startListening() {
		isListening = true;
	}

	@Override
	public void stopListening() {
		isListening = false;
	}
}
