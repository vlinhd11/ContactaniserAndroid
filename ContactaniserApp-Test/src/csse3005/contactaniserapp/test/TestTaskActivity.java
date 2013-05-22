package csse3005.contactaniserapp.test;

import com.jayway.android.robotium.solo.Solo;

import csse3005.contactaniser.activities.TaskActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TestTaskActivity extends ActivityInstrumentationTestCase2<TaskActivity> {

	private Solo solo;

	public TestTaskActivity() {
		super(TaskActivity.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testStartActivity() {
		solo.assertCurrentActivity("Check on TaskActivity", TaskActivity.class);
	}
	
	public void testActivityTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", TaskActivity.class);
    }
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
