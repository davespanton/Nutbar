package com.davespanton.nutbar.alarms;

public class StubTrippable implements Trippable {

    private boolean tripped = false;

    @Override
    public void tripAlarm() {
        tripped = true;
    }

    public boolean isTripped() {
        return tripped;
    }
}
