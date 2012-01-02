package com.davespanton.nutbar.service;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import com.davespanton.nutbar.R;
import com.davespanton.nutbar.service.binder.AccelerometerBinderBuilder;
import com.davespanton.nutbar.service.binder.AccelerometerListenerServiceBinder;
import com.davespanton.nutbar.service.sensor.SensorChangeListener;
import com.davespanton.nutbar.service.sensor.SensorMonitorListener;
import com.google.inject.Inject;
import roboguice.service.RoboService;

public class AccelerometerListenerService extends RoboService implements SensorEventListener, ListenerService, SensorMonitorListener {

	@Inject
	private AccelerometerBinderBuilder binderBuilder;
	
	private AccelerometerListenerServiceBinder binder;
	
	private SensorManager sensorManager; 
	private Sensor accelorometerSensor;
	
	@Inject 
	private SensorChangeListener sensorChangeMonitor;
	
	private boolean isListening = false;
	
	private boolean hasBeenTripped = false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		binder = binderBuilder.build(this);
		
		sensorManager = (SensorManager) getApplication().getSystemService(SENSOR_SERVICE);
		accelorometerSensor = sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
		
		sensorChangeMonitor.setSensorChangeListener(this);
	}
	
	@Override
	public void onDestroy() {
		binder = null;
		binderBuilder = null;
		sensorManager = null;
		accelorometerSensor = null;
		sensorChangeMonitor = null;
		
		stopAlarmService();
		
		super.onDestroy();
	}
	
	private void stopAlarmService() {
		Intent intent = new Intent();
		intent.setAction(getString(R.string.alarm_service_trip));
		stopService(intent);
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
		
		if(isListening)
			binder.onArmed();
	}

	@Override
	public void stopListening() {
		sensorManager.unregisterListener(this, accelorometerSensor);
		isListening = hasBeenTripped = false;
		sensorChangeMonitor.reset();
        resetAlarmService();
		binder.onDisarmed();
	}
	
	@Override
	public boolean isListening() {
		return isListening;
	}

	@Override
	public void sensorMonitorTripped() {
		if(!hasBeenTripped) {
			startAlarmService();
			binder.onTripped();
			hasBeenTripped = true;
		}	
	}
	
	private void startAlarmService() {
		Intent i = new Intent(getString(R.string.alarm_service_trip));
		startService(i);
	}

    private void resetAlarmService() {
        Intent i = new Intent(getString(R.string.alarm_service_reset));
        startService(i);
    }

	public void updateBinder() {
		if(hasBeenTripped)
			binder.onTripped();
		else if(isListening)
			binder.onArmed();
		else
			binder.onDisarmed();
	}

}
