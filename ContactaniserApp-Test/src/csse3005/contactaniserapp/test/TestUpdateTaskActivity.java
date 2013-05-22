package csse3005.contactaniserapp.test;

import com.jayway.android.robotium.solo.Solo;

import csse3005.contactaniser.activities.UpdateTaskActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TestUpdateTaskActivity extends ActivityInstrumentationTestCase2<UpdateTaskActivity> {

	private Solo solo;

	public TestUpdateTaskActivity() {
		super(UpdateTaskActivity.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testStartActivity() {
		solo.assertCurrentActivity("Check on UpdateTaskActivity", UpdateTaskActivity.class);
	}
	
	public void testActivityTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", UpdateTaskActivity.class);
    }
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
