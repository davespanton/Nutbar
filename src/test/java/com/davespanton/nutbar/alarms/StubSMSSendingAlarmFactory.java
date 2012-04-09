package com.davespanton.nutbar.alarms;

import android.content.Context;
import com.davespanton.nutbar.alarms.factory.SMSSendingAlarmFactory;

public class StubSMSSendingAlarmFactory implements SMSSendingAlarmFactory {

    private final SMSSendingAlarm alarm = new StubSmsSendingAlarm();
    @Override
    public SMSSendingAlarm create(Context context) {
        return alarm;
    }
}
