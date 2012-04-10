package com.davespanton.nutbar.alarms;

import android.content.Context;
import com.davespanton.nutbar.alarms.factory.SMSSendingAlarmFactory;
import com.google.inject.Inject;

public class StubSMSSendingAlarmFactory implements SMSSendingAlarmFactory {

    @Inject
    private SMSSendingAlarm alarm;

    @Override
    public SMSSendingAlarm create(Context context) {
        return alarm;
    }
}
