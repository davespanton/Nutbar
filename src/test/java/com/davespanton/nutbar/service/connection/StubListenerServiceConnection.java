package com.davespanton.nutbar.service.connection;

import android.content.ComponentName;
import android.os.IBinder;

public class StubListenerServiceConnection extends ListenerServiceConnection {

	@Override
	public void onServiceConnected(ComponentName name, IBinder binder) {
		// stub
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// stub
	}

}
