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
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_ProjectDataSource;
import csse3005.contactaniser.datasource.User_TaskDataSource;
import csse3005.contactaniser.library.InternetCheck;
import csse3005.contactaniser.models.TabsAdapter;
import csse3005.contactaniser.models.Task;
import csse3005.contactaniser.models.User_Task;
import csse3005.contactaniserapp.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ProjectActivity extends FragmentActivity {

	 ViewPager ViewPager;
	 TabsAdapter TabsAdapter;
	 Long mRowId;
	 private UserDataSource userdatasource;
	 private User_ProjectDataSource userprojectdatasource;
	 private User_TaskDataSource usertaskdatasource;
	 private TaskDataSource taskdatasource;
	 private MenuItem menuItem;
	 
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	 
	        userdatasource = new UserDataSource(this);
		    userdatasource.open();

		    userprojectdatasource = new User_ProjectDataSource(this);
		    userprojectdatasource.open();
		    
		    taskdatasource = new TaskDataSource(this);
		    taskdatasource.open();
		    
		    usertaskdatasource = new User_TaskDataSource(this);
		    usertaskdatasource.open();
	        
	        //create a new ViewPager and set to the pager in Ids.xml
	        ViewPager = new ViewPager(this);
	        ViewPager.setId(R.id.taskListPager);
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
	        
//	        TabsAdapter.getItem(1)
	        int userid = getIntent().getIntExtra("userid", 0);
    	    String useridstring = String.valueOf(userid);
	    
    	    InternetCheck internet = new InternetCheck();
			boolean internetOn = internet.internetOn(this);
			if (internetOn) {
    	    
	        DownSycnTaskAndUserTask dsUT = new DownSycnTaskAndUserTask();
			dsUT.setContext(this);
			

    	    HttpPost httpPost = new HttpPost("http://triple11.com/BlueTeam/android/syncDownTask.php");
        	List<NameValuePair> nvp = new ArrayList<NameValuePair>(1);
        	nvp.add(new BasicNameValuePair("userID", useridstring));
        	try {
    			httpPost.setEntity(new UrlEncodedFormEntity(nvp));
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}
        	dsUT.setHttpPost(httpPost);
        	dsUT.execute();

			}
			else {
				
				internet.NetworkError(this);
				
			}
	        
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
	
	@SuppressLint("NewApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    
	    	case R.id.change_order:
	    		 ActiveTasks taskFragment = (ActiveTasks) getSupportFragmentManager().findFragmentByTag(
	                        "android:switcher:"+R.id.taskListPager+":1");
	    		
	    		taskFragment.setByDate(!taskFragment.getByDate());
	    		
	    		if (taskFragment.getByDate()) {
	    			item.setTitle(R.string.orderby_importance);
	    		} else {
	    			item.setTitle(R.string.orderby_date);
	    		}
	    		taskFragment.fillTaskData();
	    		return true;

        	case R.id.add_task_bar: 
        		openNewTaskActivity();
        		return true;
        		
        	case R.id.menu_refresh:
        		menuItem = item;
        	    menuItem.setActionView(R.layout.progressbar);
        	    
        	    InternetCheck internet = new InternetCheck();
    			boolean internetOn = internet.internetOn(this);
    			if (internetOn) {
    			
    			//implement syncuptask here
    			JSONParserSend syncuptask = new JSONParserSend();
        		syncuptask.setContext(ProjectActivity.this);
        		
        		JSONArray jsonArray = new JSONArray();
        		ArrayList<Task> tasklist = taskdatasource.getALLTasks();
        		
        		for (int z = 0;z<tasklist.size();z++){
        			JSONObject object = new JSONObject();
        			Task task = tasklist.get(z);
        			try {
        				object.put("taskid", task.getTaskid());
            		    object.put("projectid", task.getTaskProjectid());
            		    object.put("taskname", task.getTaskName());
            		    object.put("taskdescription", task.getTaskDescription());
            		    object.put("taskimportanceindex", task.getTaskImportanceLevel());
            		    object.put("duedate", task.getTaskDueDate());
            		    object.put("completion", task.getTaskCompletion());
            		    object.put("category", task.getTaskCategory());
            		    
            		    jsonArray.put(object); 
        			} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		    
            	
	        		}
	        		
	        		System.out.println(jsonArray.toString());
	
	        		HttpPost httpPost = new HttpPost("http://protivity.triple11.com/android/syncUpTask.php");
	            	
	    		    List<NameValuePair> nvp = new ArrayList<NameValuePair>(1);
	            	nvp.add(new BasicNameValuePair("taskList", jsonArray.toString()));
	            	try {
	        			httpPost.setEntity(new UrlEncodedFormEntity(nvp));
	        		} catch (UnsupportedEncodingException e) {
	        			e.printStackTrace();
	        		}
	            	
	            	syncuptask.setHttpPost(httpPost);
	            	syncuptask.execute();
	            	
	            	
	            	
	            	//implement syncupusertask here
	    			JSONParserSend syncupusertask = new JSONParserSend();
	    			syncupusertask.setContext(ProjectActivity.this);
	        		
	        		JSONArray jsonArrayUT = new JSONArray();
    				
	        		ArrayList<User_Task> usertasklist = usertaskdatasource.getAllUser_Task();

	        		

	        		for (int j = 0;j<usertasklist.size();j++){
	        			JSONObject objectUT = new JSONObject();
	        			User_Task user_task = usertasklist.get(j);
	        			try {
	        				
	            		    objectUT.put("usertaskid", user_task.getUTid());
	             		    objectUT.put("usertaskuserid", user_task.getUTUid());
	             		    objectUT.put("usertasktaskid", user_task.getUTTid());
	             		    objectUT.put("status", user_task.getUTStatus());

	            		    
	            		    jsonArrayUT.put(objectUT); 
	        			} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            		    
	            	
		        		}
		        		
		        		System.out.println(jsonArrayUT.toString());
		
		        		HttpPost httpPostUT = new HttpPost("http://protivity.triple11.com/android/syncUpUserTask.php");
		            	
		    		    List<NameValuePair> nvpUT = new ArrayList<NameValuePair>(1);
		            	nvpUT.add(new BasicNameValuePair("userTaskList", jsonArrayUT.toString()));
		            	try {
		        			httpPostUT.setEntity(new UrlEncodedFormEntity(nvpUT));
		        		} catch (UnsupportedEncodingException e) {
		        			e.printStackTrace();
		        		}
		            	
		            	syncupusertask.setHttpPost(httpPostUT);
		            	syncupusertask.execute();

		    			menuItem.collapseActionView();
		    	    	menuItem.setActionView(null);
    			}
    			else {
    				
    				menuItem.collapseActionView();
			    	menuItem.setActionView(null);
			    	internet.NetworkError(this);
    				
    			}
	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void openNewTaskActivity() {
		Intent intent = new Intent(this, CreateTaskActivity.class);
		intent.putExtra("projectid", getIntent().getExtras().getLong("projId"));
		intent.putExtra("userid", getIntent().getIntExtra("userid", 0));
		startActivity(intent);
	}
	
	private class DownSycnTaskAndUserTask extends JSONParser {

		@SuppressLint("NewApi")
			@Override
			public void processJSON(JSONObject json) {
				try {
					// if the JSON comes back successfully

					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
					
						JSONArray jsonArrayDSTask = json.getJSONArray("taskUserList");
						//JSONArray jsonArray = json.getJSONArray("userProjectList");
						for (int g = 0; g < jsonArrayDSTask.length(); g++) {


								// if the JSON object contains a staff update get the information
								// about the staff that needs to be updated
								JSONObject DwUserTaskObject = jsonArrayDSTask.getJSONObject(g);
								//JSONObject userprojectObject = jsonArray.getJSONObject(i);
								
								String usertaskid = DwUserTaskObject.getString("userTaskId");
								String user_taskstring = DwUserTaskObject.getString("UserId");
								long user_task = Long.parseLong(user_taskstring);
								String task_taskstring = DwUserTaskObject.getString("TaskId");
								long task_task = Long.parseLong(task_taskstring);
								String statusstring = DwUserTaskObject.getString("Status");
								int status = Integer.parseInt(statusstring);

		             		    
								Calendar CalNow = Calendar.getInstance();
					        	Date DateNow = new Date(CalNow.getTimeInMillis());

					        	usertaskdatasource.createUser_Task(usertaskid, user_task, task_task, DateNow, status);
					        	

							}
						
						
						JSONArray jsonArrayDSUserTask = json.getJSONArray("taskList");
						//JSONArray jsonArray = json.getJSONArray("userProjectList");
						for (int f = 0; f < jsonArrayDSUserTask.length(); f++) {


								// if the JSON object contains a staff update get the information
								// about the staff that needs to be updated
								JSONObject DwTaskObject = jsonArrayDSUserTask.getJSONObject(f);
								//JSONObject userprojectObject = jsonArray.getJSONObject(i);
								
								String taskid = DwTaskObject.getString("TaskId");
								String taskprojectidstring = DwTaskObject.getString("ProjectId");
								long taskprojectid = Long.parseLong(taskprojectidstring);
								String taskname = DwTaskObject.getString("TaskName");
								String taskdescription = DwTaskObject.getString("TaskDescription");
								String taskimportancelevelstring = DwTaskObject.getString("TaskILevel");
								int taskimportance = Integer.parseInt(taskimportancelevelstring);
								String DueDateString = DwTaskObject.getString("TaskDD");
								java.util.Date DueDateUtil =  df.parse(DueDateString);
								java.sql.Date taskduedate = new java.sql.Date(DueDateUtil.getTime());
								
								String taskcompletionstring = DwTaskObject.getString("TaskCompletion");
								int taskcompletion = Integer.parseInt(taskcompletionstring);
								String taskcategorystring = DwTaskObject.getString("Category");
								int taskcategory = Integer.parseInt(taskcategorystring);
								Calendar CalNow = Calendar.getInstance();
					        	Date DateNow = new Date(CalNow.getTimeInMillis());

					        	
					        	taskdatasource.createTask(taskid, taskprojectid, taskname, taskdescription, taskimportance, taskduedate, taskcompletion, DateNow, taskcategory);
					        	
					        	

							}
						
						ActiveTasks fragment = (ActiveTasks) getSupportFragmentManager().findFragmentByTag(
		                        "android:switcher:"+R.id.taskListPager+":1");
		        	    CompletedTasks fragment2 = (CompletedTasks) getSupportFragmentManager().findFragmentByTag(
		                        "android:switcher:"+R.id.taskListPager+":2");
		        	    if(fragment != null)  // could be null if not instantiated yet
		        	      {
		        	         if(fragment.getView() != null) 
		        	         {
		        	            // no need to call if fragment's onDestroyView() 
		        	            //has since been called.
		        	            fragment.fillTaskData(); // do what updates are required
		        	         }
		        	      }

		        	    if(fragment2 != null)  // could be null if not instantiated yet
			      	      {
			      	         if(fragment2.getView() != null) 
			      	         {
			      	            // no need to call if fragment's onDestroyView() 
			      	            //has since been called.
			      	            fragment2.fillTaskData(); // do what updates are required
			      	         }
			      	      } 
						

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

	    }
	
}
