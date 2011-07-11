package com.davespanton.nutbar.service;

import com.google.inject.Inject;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import roboguice.service.RoboService;

public class AccelerometerListenerService extends RoboService implements SensorEventListener, ListenerService {

	@Inject private SensorManager sensorManager; 
	private Sensor accelorometerSensor;
	
	@Override
	public void onCreate() {
		accelorometerSensor = sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
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
		sensorManager.registerListener(this, accelorometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void stopListening() {
		sensorManager.unregisterListener(this, accelorometerSensor);
	}

}
