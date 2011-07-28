package com.davespanton.nutbar.service;

public class TestListenerService implements ListenerService {

	@Override
	public boolean isListening() {
		return false;
	}

	@Override
	public void startListening() {
	}

	@Override
	public void stopListening() {
	}
}
