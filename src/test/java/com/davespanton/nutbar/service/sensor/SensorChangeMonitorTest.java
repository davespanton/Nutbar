package com.davespanton.nutbar.service.sensor;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SensorChangeMonitorTest {

	private SensorChangeMonitor sut;
	private StubMonitorListener listener;
	
	private float threshold;
	
	@Before
	public void setup() {
		listener = new StubMonitorListener();
		sut = new SensorChangeMonitor(listener);
		
		threshold = sut.getThreshold();
	}
	
	@After
	public void tearDown() {
		sut = null;
		listener = null;
	}
	
	@Test
	public void shouldNotifyWhenSensorChangeExceedsThreshold() {
		float testValue = threshold + 3.0f;
		float[] values = {threshold, testValue, threshold};
		
		sut.onSensorChanged(values);
		
		assertTrue(listener.hasSensorMonitorChanged());
	}
	
	@Test
	public void shouldNotNotifyWhenSensorChangeIsWithinThreshold() {
		float testValue = threshold - 1.0f;
		float[] values = {testValue, testValue, testValue};
		
		sut.onSensorChanged(values);
		
		assertFalse(listener.hasSensorMonitorChanged());
	}
	
	private class StubMonitorListener implements SensorMonitorListener {
		
		private Boolean sensorMonitorChanged = false;
		
		@Override
		public void sensorMonitorTripped() {
			sensorMonitorChanged = true;
		}
		
		public Boolean hasSensorMonitorChanged() {
			return sensorMonitorChanged;
		}
	};
	
}
