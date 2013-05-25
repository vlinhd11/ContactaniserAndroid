package csse3005.contactaniserapp.test;

import com.jayway.android.robotium.solo.Solo;

import csse3005.contactaniser.activities.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TestMainActivity extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;

	public TestMainActivity() {
		super(MainActivity.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testStartActivity() {
		solo.assertCurrentActivity("Check on MainActivity", MainActivity.class);
	}
	
	public void testActivityTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", MainActivity.class);
    }
	
	public void testMemoryNotLow() {
		solo.assertMemoryNotLow();
	}
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
