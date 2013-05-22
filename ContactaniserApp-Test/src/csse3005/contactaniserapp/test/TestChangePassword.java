package csse3005.contactaniserapp.test;

import com.jayway.android.robotium.solo.Solo;

import csse3005.contactaniser.activities.ChangePassword;
import android.test.ActivityInstrumentationTestCase2;

public class TestChangePassword extends ActivityInstrumentationTestCase2<ChangePassword> {

	private Solo solo;

	public TestChangePassword() {
		super(ChangePassword.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testStartActivity() {
		solo.assertCurrentActivity("Check on ChangePassword", ChangePassword.class);
	}
	
	public void testActivityTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", ChangePassword.class);
    }
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
