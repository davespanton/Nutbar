package com.davespanton.nutbar.service.sensor;


public class SensorChangeMonitor {

	private SensorMonitorListener listener;
	
	private float threshold = 2.0f;
	
	public SensorChangeMonitor(SensorMonitorListener monitorListener) {
		listener = monitorListener;
	}
	
	public void onSensorChanged(float[] values) {
		for(float f : values) {
			if( f > threshold)
				listener.sensorMonitorTripped();
		}
	}
	
	public float getThreshold() {
		return threshold;
	}
}
