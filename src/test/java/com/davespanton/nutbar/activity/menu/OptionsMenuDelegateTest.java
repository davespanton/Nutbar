package com.davespanton.nutbar.activity.menu;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.davespanton.nutbar.R;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.tester.android.view.TestMenu;
import com.xtremelabs.robolectric.tester.android.view.TestMenuItem;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class OptionsMenuDelegateTest {

	private static final int EXPECTED_MENU_SIZE = 1;
	
	private OptionsMenuDelegate menuDelegate;
	private Activity testActivity;
	
	private Menu testMenu;
	
	@Before
	public void setup() {
		menuDelegate = new OptionsMenuDelegate();
		testActivity = new Activity();
	}
	
	@After
	public void tearDown() {
		menuDelegate = null;
		testActivity = null;
		testMenu = null;
	}
	
	@Test
	public void shouldPopulatePassedMenu() {
		populateMenu();
		Assert.assertEquals(EXPECTED_MENU_SIZE, testMenu.size());
	}
	
	private void populateMenu() {
		testMenu = new TestMenu(Robolectric.application);
		menuDelegate.populateOptionsMenu(testActivity, testMenu);
	}
	
	@Test
	public void shouldLaunchPreferencesActivityOnItemSelected() {
		populateMenu();
		
		MenuItem menuItem = new TestMenuItem(R.id.settings);
		menuDelegate.onOptionsItemSelected(menuItem);
		
		Intent i = Robolectric.getShadowApplication().getNextStartedActivity();
		String expected = Robolectric.getShadowApplication().getString(R.string.start_preference_activity);
		
		Assert.assertTrue(i.getAction().equals(expected));
	}
	
	@Test(expected = Error.class)
	public void shouldThrowExceptionOnItemSelectedBeforePopulating() {
		MenuItem menuItem = new TestMenuItem();
		menuDelegate.onOptionsItemSelected(menuItem);
	}
}
