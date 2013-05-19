package csse3005.contactaniser.activities;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import csse3005.contactaniser.datasource.ProjectDataSource;
import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_ProjectDataSource;
import csse3005.contactaniser.library.InternetCheck;
import csse3005.contactaniser.models.TabsAdapter;
import csse3005.contactaniserapp.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends FragmentActivity {
	
	private ProjectDataSource projectdatasource;
	private UserDataSource userdatasource;
	private User_ProjectDataSource userprojectdatasource;
	private MenuItem menuItem; //menu item used by sync refresh
	private String username;
	private int userID;
	
	ViewPager ViewPager;
	TabsAdapter TabsAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	        
	    projectdatasource = new ProjectDataSource(this);
	    projectdatasource.open();
	    
	    userdatasource = new UserDataSource(this);
	    userdatasource.open();
	    
	    userprojectdatasource = new User_ProjectDataSource(this);
	    userprojectdatasource.open();
	    
	   
	    // retrieve username and userID
	    Intent receivedIntent = getIntent();
	    setUsername(receivedIntent.getStringExtra("username")); 
	    setUserID(receivedIntent.getIntExtra("userID", 0));
	        
	    //create a new ViewPager and set to the pager we have created in Ids.xml
	    ViewPager = new ViewPager(this);
	    ViewPager.setId(R.id.projectListPager);
	    setContentView(ViewPager);
	 
	    //Create a new Action bar and set title to strings.xml
	    final ActionBar bar = getActionBar();
	    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    bar.setTitle(R.string.project_list_title);
	 
	    //Attach the Tabs to the fragment classes and set the tab title.
	    TabsAdapter = new TabsAdapter(this, ViewPager);
	        
	    TabsAdapter.addTab(bar.newTab().setText("Active Projects"), ActiveProjects.class, null);
	    TabsAdapter.addTab(bar.newTab().setText("Completed Projects"), CompletedProjects.class, null);
	 
	    if (savedInstanceState != null) {
	        bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
	    }   
	}
	 
	@SuppressLint("NewApi")
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//TODO sync on start up
        /*menuItem = (MenuItem)findViewById(R.id.menu_refresh);
        menuItem.setActionView(R.layout.progressbar);
	    menuItem.expandActionView();
	    SyncProgress task = new SyncProgress();
	    task.execute("http://triple11.com/BlueTeam/android/syncDownProject.php");*/
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
        	    
            	InternetCheck internet = new InternetCheck();
    			boolean internetOn = internet.internetOn(this);
    			if (!internetOn) {
    				new AlertDialog.Builder(this)
    			    .setTitle(R.string.network_error)
    			    .setMessage(R.string.network_error_message)
    			    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
    			        public void onClick(DialogInterface dialog, int which) { 
    			            // do nothing
    			        }
    			     })
    			     .show();
    				menuItem.collapseActionView();
			    	menuItem.setActionView(null);
			    	return super.onOptionsItemSelected(item); 
    			}
        	    
        	    DownSycnUserProject dsUP = new DownSycnUserProject();
        		dsUP.setContext(this);
        		
        		DownSycnProject dsP = new DownSycnProject();
        		dsP.setContext(this);
        		
        		DownSycnUser dsU = new DownSycnUser();
        		dsU.setContext(this);
        	        
        	    
        	    int userid = getIntent().getIntExtra("userID", 0);
        	    String useridstring = String.valueOf(userid);
        	    
        	    //UserProjectList
        	    HttpPost httpPost = new HttpPost("http://triple11.com/BlueTeam/android/syncDownUserProject.php");
            	List<NameValuePair> nvp = new ArrayList<NameValuePair>(1);
            	nvp.add(new BasicNameValuePair("userID", useridstring));
            	try {
        			httpPost.setEntity(new UrlEncodedFormEntity(nvp));
        		} catch (UnsupportedEncodingException e) {
        			e.printStackTrace();
        		}
            	dsUP.setHttpPost(httpPost);
            	
            	dsUP.setUserid(userid);
            	
            	dsUP.execute();
            	
            	//ProjectList
            	HttpPost httpPostProject = new HttpPost("http://triple11.com/BlueTeam/android/syncDownProject.php");
            	List<NameValuePair> nvp1 = new ArrayList<NameValuePair>(1);
            	nvp1.add(new BasicNameValuePair("userID", useridstring));
            	try {
        			httpPostProject.setEntity(new UrlEncodedFormEntity(nvp1));
        		} catch (UnsupportedEncodingException e) {
        			e.printStackTrace();
        		}
            	dsP.setHttpPost(httpPostProject);
            	
            	dsP.setUserid(userid);
            	
            	dsP.execute();
            	
            	
            	//UserList
            	HttpPost httpPostUser = new HttpPost("http://triple11.com/BlueTeam/android/syncDownUser.php");
            	List<NameValuePair> nvp2 = new ArrayList<NameValuePair>(1);
            	nvp2.add(new BasicNameValuePair("userID", useridstring));
            	try {
        			httpPostUser.setEntity(new UrlEncodedFormEntity(nvp2));
        		} catch (UnsupportedEncodingException e) {
        			e.printStackTrace();
        		}
            	dsU.setHttpPost(httpPostUser);
            	
            	dsU.setUserid(userid);
            	
            	dsU.execute();
        	 
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
	
	
		private class DownSycnUserProject extends JSONParser {
		
			@SuppressLint("NewApi")
				@Override
				public void processJSON(JSONObject json) {
					try {
						// if the JSON comes back successfully
							
							JSONArray jsonArray = json.getJSONArray("userProjectList");
							//JSONArray jsonArray = json.getJSONArray("userProjectList");
							for (int i = 0; i < jsonArray.length(); i++) {
								
									
									// if the JSON object contains a staff update get the information
									// about the staff that needs to be updated
									JSONObject userprojectObject = jsonArray.getJSONObject(i);
									//JSONObject userprojectObject = jsonArray.getJSONObject(i);
									String upid = userprojectObject.getString("Id");
									Log.i("userprojectidmasuk", upid);
									String upufidstring = userprojectObject.getString("uId");
									int upufid = Integer.parseInt(upufidstring);
									String uppfidstring = userprojectObject.getString("pId");
									int uppfid = Integer.parseInt(uppfidstring);
									String role = userprojectObject.getString("role");
									Calendar CalNow = Calendar.getInstance();
						        	Date DateNow = new Date(CalNow.getTimeInMillis());
										
									
									userprojectdatasource.createUser_Project(upid, upufid, uppfid, role, DateNow);
									
								}
								
				        	    ActiveProjects fragment = (ActiveProjects) getSupportFragmentManager().findFragmentByTag(
				                        "android:switcher:"+R.id.projectListPager+":0");
				        	    CompletedProjects fragment2 = (CompletedProjects) getSupportFragmentManager().findFragmentByTag(
				                        "android:switcher:"+R.id.projectListPager+":1");
				        	    if(fragment != null)  // could be null if not instantiated yet
				        	      {
				        	         if(fragment.getView() != null) 
				        	         {
				        	            // no need to call if fragment's onDestroyView() 
				        	            //has since been called.
				        	            fragment.fillData(); // do what updates are required
				        	         }
				        	      }
				        	    
				        	    if(fragment2 != null)  // could be null if not instantiated yet
					      	      {
					      	         if(fragment2.getView() != null) 
					      	         {
					      	            // no need to call if fragment's onDestroyView() 
					      	            //has since been called.
					      	            fragment2.fillData(); // do what updates are required
					      	         }
					      	      }
					
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					menuItem.collapseActionView();
			    	menuItem.setActionView(null);
				}
		    	
		    }
		
		private class DownSycnProject extends JSONParser {
			
			@SuppressLint("NewApi")
			@Override
			public void processJSON(JSONObject json) {
				try {
					// if the JSON comes back successfully
						
						JSONArray jsonArray = json.getJSONArray("projectList");
						//JSONArray jsonArray = json.getJSONArray("userProjectList");
						for (int i = 0; i < jsonArray.length(); i++) {
							
								//JSONObject userprojectObject = jsonArray.getJSONObject(i);
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
								
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								String ID = jsonObject.getString("Id");
								Log.i("projectidmasuk", ID);
								String Name = jsonObject.getString("Name");
							
								String Description = jsonObject.getString("Description");
								String StartDateString = jsonObject.getString("StartDate");
								java.util.Date StartDateUtil =  df.parse(StartDateString); 
								java.sql.Date StartDate = new java.sql.Date(StartDateUtil.getTime());
								String DueDateString = jsonObject.getString("DueDate");
								java.util.Date DueDateUtil =  df.parse(DueDateString);
								java.sql.Date DueDate = new java.sql.Date(DueDateUtil.getTime());
								String Completion = jsonObject.getString("Completion");
									
								Calendar CalNow = Calendar.getInstance();
					        	Date DateNow = new Date(CalNow.getTimeInMillis());
									
								// open the staff dao and update/insert the staff information
								projectdatasource.createProject(ID, Name,Description,StartDate,DueDate,Completion,DateNow);
								
							}
							
			        	    ActiveProjects fragment = (ActiveProjects) getSupportFragmentManager().findFragmentByTag(
			                        "android:switcher:"+R.id.projectListPager+":0");
			        	    CompletedProjects fragment2 = (CompletedProjects) getSupportFragmentManager().findFragmentByTag(
			                        "android:switcher:"+R.id.projectListPager+":1");
			        	    if(fragment != null)  // could be null if not instantiated yet
			        	      {
			        	         if(fragment.getView() != null) 
			        	         {
			        	            // no need to call if fragment's onDestroyView() 
			        	            //has since been called.
			        	            fragment.fillData(); // do what updates are required
			        	         }
			        	      }
			        	    
			        	    if(fragment2 != null)  // could be null if not instantiated yet
				      	      {
				      	         if(fragment2.getView() != null) 
				      	         {
				      	            // no need to call if fragment's onDestroyView() 
				      	            //has since been called.
				      	            fragment2.fillData(); // do what updates are required
				      	         }
				      	      }
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	
			    	//Stop the progress on the syncing icon
			    	menuItem.collapseActionView();
			    	menuItem.setActionView(null);
			}
	    	
	    }
		
		private class DownSycnUser extends JSONParser {
			
			@SuppressLint("NewApi")
			@Override
			public void processJSON(JSONObject json) {
				try {
					// if the JSON comes back successfully
						
						JSONArray jsonArray = json.getJSONArray("userList");
						//JSONArray jsonArray = json.getJSONArray("userProjectList");
						for (int i = 0; i < jsonArray.length(); i++) {
							
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								String ID = jsonObject.getString("uId");
								Log.i("useridmasuk", ID);
								String User_Username = jsonObject.getString("uUsername");
								String Username = jsonObject.getString("uName");
								int Phonenumber = Integer.parseInt(jsonObject.getString("uPhone"));
								String Email = User_Username;
									
								Calendar CalNow = Calendar.getInstance();
					        	Date DateNow = new Date(CalNow.getTimeInMillis());
									
								userdatasource.createUser(ID, User_Username, Username, Phonenumber, Email, DateNow);
								
							}
							
			        	    ActiveProjects fragment = (ActiveProjects) getSupportFragmentManager().findFragmentByTag(
			                        "android:switcher:"+R.id.projectListPager+":0");
			        	    CompletedProjects fragment2 = (CompletedProjects) getSupportFragmentManager().findFragmentByTag(
			                        "android:switcher:"+R.id.projectListPager+":1");
			        	    if(fragment != null)  // could be null if not instantiated yet
			        	      {
			        	         if(fragment.getView() != null) 
			        	         {
			        	            // no need to call if fragment's onDestroyView() 
			        	            //has since been called.
			        	            fragment.fillData(); // do what updates are required
			        	         }
			        	      }
			        	    
			        	    if(fragment2 != null)  // could be null if not instantiated yet
				      	      {
				      	         if(fragment2.getView() != null) 
				      	         {
				      	            // no need to call if fragment's onDestroyView() 
				      	            //has since been called.
				      	            fragment2.fillData(); // do what updates are required
				      	         }
				      	      }
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	
			    	//Stop the progress on the syncing icon
			    	menuItem.collapseActionView();
			    	menuItem.setActionView(null);
			}
	    	
	    }
		  
	@Override
	public void onBackPressed() {		
		exitAppConfirmation();
	}
	
	private void openPasswordActivity() {
		Intent intent = new Intent(this, ChangePassword.class);
		intent.putExtra("username", getUsername());
		intent.putExtra("userID", getUserID());
		startActivity(intent);
	}
	
	private void exitAppConfirmation() {
		new AlertDialog.Builder(this)
	    .setTitle(R.string.exit)
	    .setMessage(R.string.exit_confirmation)
	    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // TODO: Any cleanup (database, syncing etc) goes here!
	        	System.exit(0);
	        }
	     })
	    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	     .show();
	}

	private void setUsername(String username) {
		this.username = username;
	}
	
	private String getUsername() {
		return this.username;
	}
	
	private void setUserID(int userID) {
		this.userID = userID;
	}
	
	private int getUserID() {
		return this.userID;
	}
}
