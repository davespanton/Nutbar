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
	private float[] baseValues = {5f, 5f, 5f};
	
	@Before
	public void setup() {
		listener = new StubMonitorListener();
		sut = new SensorChangeMonitor();
		sut.setSensorChangeListener(listener);
		
		threshold = sut.getThreshold();
	}
	
	@After
	public void tearDown() {
		sut = null;
		listener = null;
	}
	
	@Test
	public void shouldNotNotifyOnFirstSensorChange() {
		sut.onSensorChanged(baseValues);
		
		assertFalse(listener.hasSensorMonitorChanged());
	}
	
	@Test
	public void shouldNotifyWhenSensorChangeExceedsThreshold() {
		sut.onSensorChanged(baseValues);
		
		float testValue = threshold + 1f;
		sut.onSensorChanged(getTweakedValuesFromBase(testValue));
		
		assertTrue(listener.hasSensorMonitorChanged());
	}
	
	private float[] getTweakedValuesFromBase(float tweakBy) {
		float[] copy = baseValues.clone();
		copy[1] += tweakBy;
		return copy;
	}
	
	@Test
	public void shouldNotNotifyWhenSensorChangeIsWithinThreshold() {
		sut.onSensorChanged(baseValues);
		
		float testValue = 1f;
		sut.onSensorChanged(getTweakedValuesFromBase(testValue));
		
		assertFalse(listener.hasSensorMonitorChanged());
	}
	
	@Test
	public void shouldNotNotifyWhenMultipleSensorChangesAreWithinThreshold() {
		sut.onSensorChanged(baseValues);
		
		for( float i = 1f; i < 10f; i++) {
			sut.onSensorChanged(getTweakedValuesFromBase(i));
		}
		
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
