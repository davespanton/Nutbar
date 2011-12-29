package com.davespanton.nutbar.alarms;

import android.app.Application;
import android.location.Location;
import android.location.LocationManager;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowLocationManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.Provider;
import java.util.ArrayList;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(InjectedTestRunner.class)
public class LocationAlarmTest {

	@Inject
    private LocationAlarm sut;

	private ShadowLocationManager shadow;

    private ArrayList<Location> locations;
	
	@Before
	public void setup() {
		shadow = shadowOf((LocationManager) Robolectric.application.getSystemService(Application.LOCATION_SERVICE));
        locations = new ArrayList<Location>();
	}
	
	@After
	public void tearDown() {
		sut = null;
		shadow = null;
        locations = null;
	}
	
	@Test
	public void shouldAddListenerOnTripAlarm() {
		sut.tripAlarm();
		assertTrue(shadow.getRequestLocationUpdateListeners().contains(sut));
	}
	
	@Test
	public void shouldRemoveListenerOnResetAlarm() {
		sut.tripAlarm();
		assertTrue(shadow.getRequestLocationUpdateListeners().contains(sut));
		sut.resetAlarm();
		assertFalse(shadow.getRequestLocationUpdateListeners().contains(sut));
	}
	
	@Test
	public void shouldReturnIsListeningWhenListening() {
		sut.tripAlarm();
		assertTrue(sut.isListening());
	}
	
	@Test
	public void shouldReturnIsNotListeningWhenNotListening() {
		sut.tripAlarm();
		sut.resetAlarm();
		assertFalse(sut.isListening());
	}

    @Test
    public void shouldNotifyListenerOnLocationUpdates() {
        sut.setOnLocationChangeListener(locationAlarmListener);
        Location loc = new Location(LocationManager.GPS_PROVIDER);
        sut.onLocationChanged(loc);

        assertTrue(locations.contains(loc));
    }

    @Test
    public void shouldNotNotifyOnLocationUpdatesWithNoListenerSet() {
        sut.setOnLocationChangeListener(null);
        Location loc = new Location(LocationManager.GPS_PROVIDER);
        sut.onLocationChanged(loc);

        assertFalse(locations.contains(loc));
    }

    private LocationAlarmListener locationAlarmListener = new LocationAlarmListener() {

        @Override
        public void onLocationChanged(Location location) {
            locations.add(location);
        }
    };
}
