package com.davespanton.nutbar.alarms;

import android.location.LocationListener;

public class StubLocationAlarm extends LocationAlarm {

    private LocationAlarmListener locationListener;

    @Override
    public void setOnLocationChangeListener(LocationAlarmListener listener) {
        locationListener = listener;
        super.setOnLocationChangeListener(listener);
    }

    public LocationAlarmListener getOnLocationChangeLocationListener() {
        return locationListener;
    }
}
