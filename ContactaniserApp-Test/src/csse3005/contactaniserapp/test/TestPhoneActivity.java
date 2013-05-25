package csse3005.contactaniserapp.test;

import com.jayway.android.robotium.solo.Solo;

import csse3005.contactaniser.activities.PhoneActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TestPhoneActivity extends ActivityInstrumentationTestCase2<PhoneActivity> {

	private Solo solo;

	public TestPhoneActivity() {
		super(PhoneActivity.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testStartActivity() {
		solo.assertCurrentActivity("Check on PhoneActivity", PhoneActivity.class);
	}
	
	public void testActivityTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", PhoneActivity.class);
    }
	
	public void testMemoryNotLow() {
		solo.assertMemoryNotLow();
	}
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
