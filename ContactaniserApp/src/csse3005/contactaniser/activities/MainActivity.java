package csse3005.contactaniser.activities;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.contactaniserapp.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends FragmentActivity {
	
	ViewPager ViewPager;
	 TabsAdapter TabsAdapter;
	 
	    /** Called when the activity is first created. */
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	 
	        //create a new ViewPager and set to the pager we have created in Ids.xml
	        ViewPager = new ViewPager(this);
	        ViewPager.setId(R.id.pager);
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
	
	 // create TabsAdapter to create tabs and behavior
	 public static class TabsAdapter extends FragmentPagerAdapter
	 implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
	 
	  private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
	 
	        static final class TabInfo {
	            private final Class<?> clss;
	            private final Bundle args;
	 
	            TabInfo(Class<?> _class, Bundle _args) {
	                clss = _class;
	                args = _args;
	            }
	        }
	 
	  public TabsAdapter(FragmentActivity activity, ViewPager pager) {
	   super(activity.getSupportFragmentManager());
	            mContext = activity;
	            mActionBar = activity.getActionBar();
	            mViewPager = pager;
	            mViewPager.setAdapter(this);
	            mViewPager.setOnPageChangeListener(this);
	        }
	 
	  public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
	            TabInfo info = new TabInfo(clss, args);
	            tab.setTag(info);
	            tab.setTabListener(this);
	            mTabs.add(info);
	            mActionBar.addTab(tab);
	            notifyDataSetChanged();
	 
	        }
	 
	  @Override
	  public void onPageSelected(int position) {
	   // TODO Auto-generated method stub
	   mActionBar.setSelectedNavigationItem(position);
	  }
	 
	  public void onTabSelected(Tab tab, FragmentTransaction ft) {
	   Object tag = tab.getTag();
	            for (int i=0; i<mTabs.size(); i++) {
	                if (mTabs.get(i) == tag) {
	                    mViewPager.setCurrentItem(i);
	                }
	            }
	  }
	 
	 
	  	@Override
	  	public Fragment getItem(int position) {
		  TabInfo info = mTabs.get(position);
		  return Fragment.instantiate(mContext, info.clss.getName(), info.args);
	  	}
	 
	  	@Override
	  	public int getCount() {
		  return mTabs.size();
	  	}

	  
		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
	
		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
	
		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
		 // TODO Auto-generated method stub
		}
		 
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		 // TODO Auto-generated method stub
		}
		
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
		
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
	 
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_change_password:
	        	openPasswordActivity();
	            return true;
	        case R.id.menu_logoff:
	        	// log off action - save info to db, etc
	        	startActivity(new Intent(this, LoginActivity.class));
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
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
