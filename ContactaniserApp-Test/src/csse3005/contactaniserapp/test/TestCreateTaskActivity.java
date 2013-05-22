package csse3005.contactaniserapp.test;

import com.jayway.android.robotium.solo.Solo;

import csse3005.contactaniser.activities.CreateTaskActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TestCreateTaskActivity extends ActivityInstrumentationTestCase2<CreateTaskActivity> {

	private Solo solo;

	public TestCreateTaskActivity() {
		super(CreateTaskActivity.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testStartActivity() {
		solo.assertCurrentActivity("Check on CreateTaskActivity", CreateTaskActivity.class);
	}
	
	public void testActivityTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", CreateTaskActivity.class);
    }
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
