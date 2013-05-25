package csse3005.contactaniserapp.test;

import com.jayway.android.robotium.solo.Solo;

import csse3005.contactaniser.activities.MemberInfoActivity;
import android.test.ActivityInstrumentationTestCase2;

public class TestMemberInfoActivity extends ActivityInstrumentationTestCase2<MemberInfoActivity> {

	private Solo solo;

	public TestMemberInfoActivity() {
		super(MemberInfoActivity.class);
	}
	

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testStartActivity() {
		solo.assertCurrentActivity("Check on MemberInfoActivity", MemberInfoActivity.class);
	}
	
	public void testActivityTestCaseSetUpProperly() {
        assertNotNull("Activity should be launched successfully", MemberInfoActivity.class);
    }
	
	public void testMemoryNotLow() {
		solo.assertMemoryNotLow();
	}
	
	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
