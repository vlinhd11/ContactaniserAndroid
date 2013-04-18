package csse3005.contactaniser.models;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
	 
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
	   mActionBar.setSelectedNavigationItem(position);
	  }
	 
	  @Override
	  public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
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