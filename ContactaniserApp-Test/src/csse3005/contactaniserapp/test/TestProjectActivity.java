package csse3005.contactaniserapp.test;

import com.jayway.android.robotium.solo.Solo;

import csse3005.contactaniser.activities.ProjectActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TestProjectActivity extends ActivityInstrumentationTestCase2<ProjectActivity> {

	private Solo solo;

	public TestProjectActivity() {
		super(ProjectActivity.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testStartActivity() {
		solo.assertCurrentActivity("Check on ProjectActivity", ProjectActivity.class);
	}
	
	public void testActivityTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", ProjectActivity.class);
    }
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
