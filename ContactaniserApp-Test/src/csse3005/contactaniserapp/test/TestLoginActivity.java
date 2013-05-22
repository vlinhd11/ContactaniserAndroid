package csse3005.contactaniserapp.test;

import com.jayway.android.robotium.solo.Solo;

import csse3005.contactaniser.activities.LoginActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TestLoginActivity extends ActivityInstrumentationTestCase2<LoginActivity> {

	private Solo solo;

	public TestLoginActivity() {
		super(LoginActivity.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testStartActivity() {
		solo.assertCurrentActivity("Check on LoginActivity", LoginActivity.class);
	}
	
	public void testActivityTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", LoginActivity.class);
    }
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
