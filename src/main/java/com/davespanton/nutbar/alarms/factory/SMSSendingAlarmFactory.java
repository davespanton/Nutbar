package com.davespanton.nutbar.alarms.factory;

import android.content.Context;
import com.davespanton.nutbar.alarms.SMSSendingAlarm;

public interface SMSSendingAlarmFactory {
    public SMSSendingAlarm create(Context context);
}
