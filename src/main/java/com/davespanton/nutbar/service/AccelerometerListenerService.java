package com.davespanton.nutbar.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import com.davespanton.nutbar.R;
import com.davespanton.nutbar.service.binder.AccelerometerListenerServiceBinder;
import com.davespanton.nutbar.service.binder.ListenerServiceBinder;

public class AccelerometerListenerService extends Service implements SensorEventListener, ListenerService {

	private ListenerServiceBinder binder = new AccelerometerListenerServiceBinder(this);
	
	private SensorManager sensorManager; 
	private Sensor accelorometerSensor;
	
	private boolean isListening = false;
	
	@Override
	public void onCreate() {
		sensorManager = (SensorManager) getApplication().getSystemService(SENSOR_SERVICE);
		accelorometerSensor = sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent.getAction().equals(getString(R.string.acc_service_start_listening)))
			startListening();
		else if(intent.getAction().equals(getString(R.string.acc_service_stop_listening)))
			stopListening();
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		
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
//		Log.d("ACC", "sensor changed " + 
//				Double.toString(Math.floor(event.values[0])) + " " + 
//				Double.toString(Math.floor(event.values[1])) + " " + 
//				Double.toString(Math.floor(event.values[2])) );
		Intent i = new Intent();
		i.setAction(getString(R.string.gps_service_start_listening));
		startService(i);
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
		stopLocationListening();
		isListening = false;
	}
	
	private void stopLocationListening() {
		Intent i = new Intent();
		i.setAction(getString(R.string.gps_service_stop_listening));
		startService(i);
	}

	@Override
	public boolean isListening() {
		return isListening;
	}

}
