package com.davespanton.nutbar.service.sensor;


public class SensorChangeMonitor {

	private SensorMonitorListener listener;
	
	private float threshold = 2.0f;
	
	private float[] baseValues;
	
	public SensorChangeMonitor(SensorMonitorListener monitorListener) {
		listener = monitorListener;
	}
	
	public void onSensorChanged(float[] values) {
		if(baseValues == null) {
			baseValues = values;
			return;
		}
		
		for(int i = 0; i < values.length; i++) {
			float difference = Math.abs(baseValues[i] - values[i]);
			if(difference > threshold)
				listener.sensorMonitorTripped();
		}
		
		baseValues = values;
	}
	
	public float getThreshold() {
		return threshold;
	}
}
