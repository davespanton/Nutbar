package com.davespanton.nutbar.alarms.broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.davespanton.nutbar.alarms.Trippable;

import javax.inject.Inject;

public class ReTripReceiver extends BroadcastReceiver {

    public static final int RE_TRIP_DELAY = 10000;

    private Trippable trippable;

    public void ReTripReceiver() {
        // for injection
    }

    @Inject
    private Handler handler;

    public void setTrippable(Trippable trippable) {
        this.trippable = trippable;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(getResultCode() == Activity.RESULT_OK) // not so easily testable
            return;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                trippable.tripAlarm();
            }
        }, RE_TRIP_DELAY);
    }
}
