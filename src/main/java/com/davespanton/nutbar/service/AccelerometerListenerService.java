package com.davespanton.nutbar.service;

import roboguice.service.RoboService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import com.davespanton.nutbar.R;
import com.davespanton.nutbar.service.binder.AccelerometerListenerServiceBinder;
import com.davespanton.nutbar.service.binder.ListenerServiceBinder;
import com.davespanton.nutbar.service.sensor.SensorChangeListener;
import com.davespanton.nutbar.service.sensor.SensorMonitorListener;
import com.google.inject.Inject;

public class AccelerometerListenerService extends RoboService implements SensorEventListener, ListenerService, SensorMonitorListener {

	private ListenerServiceBinder binder = new AccelerometerListenerServiceBinder(this);
	
	private SensorManager sensorManager; 
	private Sensor accelorometerSensor;
	
	@Inject 
	private SensorChangeListener sensorChangeMonitor;
	
	private boolean isListening = false;
	
	private boolean hasBeenTripped = false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		sensorManager = (SensorManager) getApplication().getSystemService(SENSOR_SERVICE);
		accelorometerSensor = sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
		
		sensorChangeMonitor.setSensorChangeListener(this);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent.getAction().equals(getString(R.string.acc_service_start_listening)))
			startListening();
		else if(intent.getAction().equals(getString(R.string.acc_service_stop_listening)))
			stopListening();
	
		return START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		sensorChangeMonitor.onSensorChanged(event.values);
	}

	@Override
	public void startListening() {
		if(isListening)
			return;
		
		isListening = sensorManager.registerListener(this, accelorometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void stopListening() {
		sensorManager.unregisterListener(this, accelorometerSensor);
		requestLocationListenerStop();
		isListening = hasBeenTripped = false;
		sensorChangeMonitor.reset();
	}
	
	private void requestLocationListenerStop() {
		Intent i = new Intent();
		i.setAction(getString(R.string.gps_service_stop_listening));
		startService(i);
	}
	
	@Override
	public boolean isListening() {
		return isListening;
	}

	@Override
	public void sensorMonitorTripped() {
		if(!hasBeenTripped) {
			Intent i = new Intent();
			i.setAction(getString(R.string.gps_service_start_listening));
			startService(i);
			hasBeenTripped = true;
		}	
	}

}
