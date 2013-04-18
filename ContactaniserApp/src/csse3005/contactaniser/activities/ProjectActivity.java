package csse3005.contactaniser.activities;
import csse3005.contactaniserapp.R;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import csse3005.contactaniser.models.TabsAdapter;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ProjectActivity extends FragmentActivity {

	ViewPager ViewPager;
	 TabsAdapter TabsAdapter;
	 
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	 
	        //create a new ViewPager and set to the pager in Ids.xml
	        ViewPager = new ViewPager(this);
	        ViewPager.setId(R.id.projectListPager);
	        setContentView(ViewPager);
	 
	        //Create a new Action bar and set title to strings.xml
	        final ActionBar bar = getActionBar();
	        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        // TODO: replace with resource
	        bar.setTitle(getIntent().getStringExtra("projName"));
	 
	        //Attach the Tabs to the fragment classes and set the tab title.
	        TabsAdapter = new TabsAdapter(this, ViewPager);
	        
	        TabsAdapter.addTab(bar.newTab().setText("Project Info"), ProjectInfoActivity.class, null);
	        TabsAdapter.addTab(bar.newTab().setText("Active"), ActiveTasks.class, null);
	        TabsAdapter.addTab(bar.newTab().setText("Completed"), CompletedTasks.class, null);
	        
	        TabsAdapter.onPageSelected(1);
	 
	        if (savedInstanceState != null) {
	            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
	        }
	 
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project, menu);
		return true;
	}

}
