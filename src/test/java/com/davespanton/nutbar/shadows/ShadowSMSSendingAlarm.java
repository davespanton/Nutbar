package com.davespanton.nutbar.shadows;

import android.app.PendingIntent;
import android.content.Context;
import com.davespanton.nutbar.alarms.SMSSendingAlarm;
import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.internal.RealObject;

@Implements(SMSSendingAlarm.class)
public class ShadowSMSSendingAlarm {

    @RealObject SMSSendingAlarm alarm;

    public void __constructor__(Context context) {

    }

    private int tripCount = 0;

    @Implementation
    public void tripAlarm(PendingIntent pendingIntent) {
        tripCount++;
    }

    public int getTripCount() {
        return tripCount;
    }
}
