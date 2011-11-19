package com.davespanton.nutbar.activity;

import static com.xtremelabs.robolectric.Robolectric.getShadowApplication;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.davespanton.nutbar.R;
import com.davespanton.nutbar.activity.menu.OptionsMenuDelegate;
import com.davespanton.nutbar.activity.menu.StubOptionsMenuDelegate;
import com.davespanton.nutbar.injected.InjectedTestRunner;
import com.davespanton.nutbar.service.connection.ListenerServiceConnection;
import com.davespanton.nutbar.service.connection.StubListenerServiceConnection;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.tester.android.view.TestMenu;
import com.xtremelabs.robolectric.tester.android.view.TestMenuItem;

@RunWith(InjectedTestRunner.class)
public class NutbarActivityTest {

	private NutbarActivity sut;
	
	@Inject
	private ListenerServiceConnection conn;
	
	private ShadowActivity shadow;
	
	@Inject
	private OptionsMenuDelegate optionsMenuDelegate;
	
	@Before
	public void setup() {
		sut = new NutbarActivity();
		sut.onCreate(null);
		shadow = shadowOf(sut);
	}
	
	@Test
	public void shouldStartListenerServicesOnCreate() {
		Intent intent = shadow.getNextStartedService(); //consume binding intent
		intent = shadow.getNextStartedService();
		assertEquals(intent.getAction(), getShadowApplication().getString(R.string.start_acc_listener_service));
	}
	
	@Test
	public void shouldStopListenerServicesIfIdleOnDestroy() {
		sut.onDestroy();
		Intent intent = shadow.getNextStoppedService();
		assertEquals(intent.getAction(), getShadowApplication().getString(R.string.start_acc_listener_service));
	}
	
	@Test
	public void shouldNotStopListenerServicesIfListening() {
		((StubListenerServiceConnection) conn).setIsListening(true);
		sut.onDestroy();
		assertTrue(shadow.getNextStoppedService() == null);
	}
	
	@Test
	public void shouldHaveAccelerometerServiceToggleButton() {
		assertNotNull(sut.findViewById(R.id.accelerometer_button));
	}
	
	@Test
	public void shouldHaveStatusTextField() {
		assertNotNull(sut.findViewById(R.id.status_text));
	}

    @Test
    public void shouldHaveStatusTitleTextField() {
        assertNotNull(sut.findViewById(R.id.status_text_title));
    }
	
	@Test
	public void shouldHaveDisarmedStatusTextByDefault() {
		String disarmedText = getShadowApplication().getString(R.string.disarmed);
		assertEquals(disarmedText, getStatusText());
	}
	
	private String getStatusText() {
		return ((TextView) sut.findViewById(R.id.status_text)).getText().toString();
	}

    @Test
    public void shouldHaveCorrectStatusTitleText() {
        String statusTitle = getShadowApplication().getString(R.string.current_status);
        assertEquals(statusTitle, getStatusTitleText());
    }

    private String getStatusTitleText() {
		return ((TextView) sut.findViewById(R.id.status_text_title)).getText().toString();
	}
	
	@Test
	public void shouldHaveButtonDisabledByDefault() {
		assertFalse(getAccelerometerButton().isEnabled());
	}
	
	private Button getAccelerometerButton() {
		return (Button) sut.findViewById(R.id.accelerometer_button);
	}
	
	@Test
	public void shouldHaveAccelerometerServiceToggleButtonWithCorrectLabel() {
		String expectedLabel = sut.getResources().getString(R.string.arm);
		assertEquals(expectedLabel, getAccelerometerButton().getText());
	}
	
	@Test 
	public void shouldEnableButtonOnAccelerometerServiceConnected() {
		sut.onAccelerometerServiceConnected();
		assertTrue(getAccelerometerButton().isEnabled());
	}
	
	@Test
	public void shouldDisableButtonOnAccelerometerServiceDisconnected() {
		sut.onAccelerometerServiceConnected();
		sut.onAccelerometerServiceDisconnected();
		assertFalse(getAccelerometerButton().isEnabled());
	}
	
	@Test
	public void shouldStartInactiveAccServiceListeningOnAccButtonClick() {
		sut.onAccelerometerServiceConnected();
		
		shadow.clearStartedServices();
		
		getAccelerometerButton().performClick();
		Intent i = shadow.getNextStartedService();
		assertEquals(getShadowApplication().getString(R.string.acc_service_start_listening), i.getAction());
	}
	
	
	@Test
	public void shouldStopActiveAccServiceListeningOnAccButtonClick() {
		sut.onAccelerometerServiceConnected();
		((StubListenerServiceConnection) conn).setIsListening(true);
		
		shadow.clearStartedServices();
		
		getAccelerometerButton().performClick();
		Intent i = shadow.getNextStartedService();
		assertEquals(getShadowApplication().getString(R.string.acc_service_stop_listening), i.getAction());
	}

	@Test
	public void shouldUpdateTextFieldOnArmed() {
		sut.onArmed();
		assertEquals(getShadowApplication().getString(R.string.armed), getStatusText());
	}
	
	@Test
	public void shouldUpdateTextFieldOnTripped() {
		sut.onTripped();
		assertEquals(getShadowApplication().getString(R.string.tripped), getStatusText());
	}
	
	@Test
	public void shouldUpdateTextFieldOnDisarmed() {
		sut.onArmed();
		sut.onDisarmed();
		assertEquals(getShadowApplication().getString(R.string.disarmed), getStatusText());
	}

    @Test
    public void shouldUpdateButtonTextOnArmed() {
        sut.onArmed();
        assertEquals(getShadowApplication().getString(R.string.disarm), getButtonText());
    }

    private String getButtonText() {
        return ((Button) sut.findViewById(R.id.accelerometer_button)).getText().toString();
    }

    @Test
    public void shouldUpdateButtonTextOnDisarmed() {
        sut.onArmed();
        sut.onDisarmed();
        assertEquals(getShadowApplication().getString(R.string.arm), getButtonText());
    }

    @Test
    public void shouldUpdateButtonTextOnTripped() {
        sut.onTripped();
        assertEquals(getShadowApplication().getString(R.string.reset), getButtonText());
    }

    @Test
	public void shouldCallDelegateToCreateOptionsMenu() {
		sut.onCreateOptionsMenu(new TestMenu(Robolectric.application));
		assertTrue(((StubOptionsMenuDelegate) optionsMenuDelegate).hasBeenPopulated());
	}
	
	@Test
	public void shouldCallDelegateOnOptionSelected() {
		MenuItem testItem = new TestMenuItem(); 
		sut.onOptionsItemSelected(testItem);
		
		MenuItem actual = ((StubOptionsMenuDelegate) optionsMenuDelegate).getLastSelectedItem();
		assertEquals(testItem, actual);
	}
}
