package com.davespanton.nutbar.service;

import roboguice.service.RoboService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class AccelerometerListenerService extends RoboService implements SensorEventListener, ListenerService {

	private ListenerServiceBinder binder = new ListenerServiceBinder(this);
	
	private SensorManager sensorManager; 
	private Sensor accelorometerSensor;
	
	private boolean isListening = false;
	
	@Override
	public void onCreate() {
		sensorManager = (SensorManager) getApplication().getSystemService(SENSOR_SERVICE);
		accelorometerSensor = sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startListening() {
		isListening = sensorManager.registerListener(this, accelorometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void stopListening() {
		sensorManager.unregisterListener(this, accelorometerSensor);
		isListening = false;
	}

	@Override
	public boolean isListening() {
		return isListening;
	}

}
