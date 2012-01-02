package com.davespanton.nutbar.alarms;

public class StubLocationAlarm extends LocationAlarm {

    private LocationAlarmListener locationListener;

    private int tripCount = 0;

    @Override
    public void setOnLocationChangeListener(LocationAlarmListener listener) {
        super.setOnLocationChangeListener(listener);
        locationListener = listener;
    }

    public LocationAlarmListener getOnLocationChangeLocationListener() {
        return locationListener;
    }

    @Override
    public void tripAlarm() {
        super.tripAlarm();
        tripCount++;
    }

    public int getTripCount() {
        return tripCount;
    }
}
