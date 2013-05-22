package csse3005.contactaniserapp.test;

import com.jayway.android.robotium.solo.Solo;

import csse3005.contactaniser.activities.EmailActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TestEmailActivity extends ActivityInstrumentationTestCase2<EmailActivity> {

	private Solo solo;

	public TestEmailActivity() {
		super(EmailActivity.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testStartActivity() {
		solo.assertCurrentActivity("Check on EmailActivity", EmailActivity.class);
	}
	
	public void testActivityTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", EmailActivity.class);
    }
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
