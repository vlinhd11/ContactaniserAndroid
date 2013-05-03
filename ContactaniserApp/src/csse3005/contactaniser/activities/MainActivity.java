package csse3005.contactaniser.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import csse3005.contactaniser.models.TabsAdapter;
import csse3005.contactaniserapp.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends FragmentActivity {
	
	ViewPager ViewPager;
	TabsAdapter TabsAdapter;
	private MenuItem menuItem; //menu item used by sync refresh
	 
	    /** Called when the activity is first created. */
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	 
	        //create a new ViewPager and set to the pager we have created in Ids.xml
	        ViewPager = new ViewPager(this);
	        ViewPager.setId(R.id.projectListPager);
	        setContentView(ViewPager);
	 
	        //Create a new Action bar and set title to strings.xml
	        final ActionBar bar = getActionBar();
	        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        // TODO: replace with resource
	        bar.setTitle("Projects");
	 
	        //Attach the Tabs to the fragment classes and set the tab title.
	        TabsAdapter = new TabsAdapter(this, ViewPager);
	        
	        TabsAdapter.addTab(bar.newTab().setText("Active Projects"), ActiveProjects.class, null);
	        TabsAdapter.addTab(bar.newTab().setText("Completed Projects"), CompletedProjects.class, null);
	 
	        if (savedInstanceState != null) {
	            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
	        }
	 
	    }
	 
	@Override
	 protected void onSaveInstanceState(Bundle outState) {
	  super.onSaveInstanceState(outState);
	        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	 
	 }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {

        	case R.id.menu_refresh:
        	    menuItem = item;
        	    menuItem.setActionView(R.layout.progressbar);
        	    menuItem.expandActionView();
        	    SyncProgress task = new SyncProgress();
        	    task.execute();
        		return true;

	        case R.id.menu_change_password:
	        	openPasswordActivity();
	            return true;
	        case R.id.menu_logoff:
	        	// log off action here - save info to db, etc
	        	startActivity(new Intent(this, LoginActivity.class));
	        	return true;

	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private class SyncProgress extends AsyncTask<String, Void, String> {

	    @Override
	    protected String doInBackground(String... params) {
	    	///////////Dummy Simulate something long running
	    	try {
	    		Thread.sleep(2000);
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	    	///////////
	    	return null;
	    }

	    @SuppressLint("NewApi")
		@Override
	    protected void onPostExecute(String result) {
	    	menuItem.collapseActionView();
	    	menuItem.setActionView(null);
	    }
	};
		  
	@Override
	public void onBackPressed() {		
		exitAppConfirmation();
	}
	
	private void openPasswordActivity() {
		startActivity(new Intent(this, ChangePassword.class));
	}
	
	private void exitAppConfirmation() {
		new AlertDialog.Builder(this)
	    .setTitle(R.string.exit)
	    .setMessage(R.string.exit_confirmation)
	    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // Do cleanup here!!
	        	Intent intent = new Intent(Intent.ACTION_MAIN);
	        	intent.addCategory(Intent.CATEGORY_HOME);
	        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	startActivity(intent);
	        }
	     })
	    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	     .show();
	}

}
