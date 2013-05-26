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
import android.view.Menu;
import android.view.MenuItem;
import csse3005.contactaniser.datasource.ProjectDataSource;
import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_ProjectDataSource;
import csse3005.contactaniser.datasource.User_TaskDataSource;
import csse3005.contactaniser.library.InternetCheck;
import csse3005.contactaniser.library.JSONParser;
import csse3005.contactaniser.library.JSONParserSend;
import csse3005.contactaniser.models.TabsAdapter;
import csse3005.contactaniser.models.Task;
import csse3005.contactaniser.models.User_Task;
import csse3005.contactaniserapp.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends FragmentActivity {

	private ProjectDataSource projectdatasource;
	private UserDataSource userdatasource;
	private User_ProjectDataSource userprojectdatasource;
	private User_TaskDataSource usertaskdatasource;
	private TaskDataSource taskdatasource;
	private MenuItem menuItem; //menu item used by sync refresh
	private String username;
	private int userID;

	ViewPager ViewPager;
	TabsAdapter TabsAdapter;

	/** Called after login */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    
	    //Open database
	    projectdatasource = new ProjectDataSource(this);
	    projectdatasource.open();

	    userdatasource = new UserDataSource(this);
	    userdatasource.open();

	    userprojectdatasource = new User_ProjectDataSource(this);
	    userprojectdatasource.open();

	    usertaskdatasource = new User_TaskDataSource(this);
	    usertaskdatasource.open();

	    taskdatasource = new TaskDataSource(this);
	    taskdatasource.open();


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
	    
	    //Get userid from LoginActivity
	    int userid = getIntent().getIntExtra("userID", 0);
	    String useridstring = String.valueOf(userid);
	    
	    //Sync all data
	    DownSycnAllAuto dsAllAuto = new DownSycnAllAuto();
	    dsAllAuto.setContext(this);
	    
	    HttpPost httpPostAll= new HttpPost("http://triple11.com/BlueTeam/android/syncDown.php");
    	List<NameValuePair> nvpAll = new ArrayList<NameValuePair>(1);
    	nvpAll.add(new BasicNameValuePair("userID", useridstring));
    	try {
			httpPostAll.setEntity(new UrlEncodedFormEntity(nvpAll));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	dsAllAuto.setHttpPost(httpPostAll);

    	dsAllAuto.execute();
	    
    		
	}

	@SuppressLint("NewApi")
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//TODO sync on start up
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
	    	
	    //Sync all data when the menu selected
        	case R.id.menu_refresh:
        		
        	    menuItem = item;
        	    menuItem.setActionView(R.layout.progressbar);

        	    
    			InternetCheck internet = new InternetCheck();
    			boolean internetOn = internet.internetOn(this);
    			if (internetOn) {
    				int userid = getIntent().getIntExtra("userID", 0);
    			    String useridstring = String.valueOf(userid);
    				
    				DownSycnAll dsAll = new DownSycnAll();
    			    dsAll.setContext(this);
    			    
    			    HttpPost httpPostAll= new HttpPost("http://triple11.com/BlueTeam/android/syncDown.php");
    		    	List<NameValuePair> nvpAll = new ArrayList<NameValuePair>(1);
    		    	nvpAll.add(new BasicNameValuePair("userID", useridstring));
    		    	try {
    					httpPostAll.setEntity(new UrlEncodedFormEntity(nvpAll));
    				} catch (UnsupportedEncodingException e) {
    					e.printStackTrace();
    				}
    		    	dsAll.setHttpPost(httpPostAll);

    		    	dsAll.execute();
    			}
    			else {
    				
    				menuItem.collapseActionView();
			    	menuItem.setActionView(null);
			    	internet.NetworkError(this);
    				
    			}

        		return true;
        	 
        	//Menu to move to ChangePassword Activity
	        case R.id.menu_change_password:
	        	openPasswordActivity();
	            return true;
	        
	            
	            //Menu to move to LoginActivity and clean database
	        case R.id.menu_logoff:
	        	syncupalldata();
	        	userprojectdatasource.deleteAllUserProject();
	        	 userdatasource.deleteAllUser();
	        	 usertaskdatasource.deleteAllUserTask();
	        	 taskdatasource.deleteAllTask();
	        	 projectdatasource.deleteAllProject();

	        	startActivity(new Intent(this, LoginActivity.class));
	        	return true;

	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	
	/** Sync down all data when refresh menu selected */
		private class DownSycnAll extends JSONParser {

			@SuppressLint("NewApi")
			@Override
			public void processJSON(JSONObject json) {
				try {
					
					/*
					 * 
					 * Sycn Down All Project
					 * 
					 */
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
					JSONArray jsonArray = json.getJSONArray("projectList");
					for (int i = 0; i < jsonArray.length(); i++) {

							

							JSONObject jsonObject = jsonArray.getJSONObject(i);
							String ID = jsonObject.getString("Id");
							String Name = jsonObject.getString("Name");
							
							String Description = jsonObject.getString("Description");
							String StartDateString = jsonObject.getString("StartDate");
							java.util.Date StartDateUtil =  df.parse(StartDateString); 
							java.sql.Date StartDate = new java.sql.Date(StartDateUtil.getTime());
							String DueDateString = jsonObject.getString("DueDate");
							java.util.Date DueDateUtil =  df.parse(DueDateString);
							java.sql.Date DueDate = new java.sql.Date(DueDateUtil.getTime());
							String Completion = jsonObject.getString("Completion");
							String Status = jsonObject.getString("Status");
							Calendar CalNow = Calendar.getInstance();
				        	Date DateNow = new Date(CalNow.getTimeInMillis());

							projectdatasource.createProject(ID, Name,Description,StartDate,DueDate,Completion,DateNow,Status);

						}

		        	    ActiveProjects fragment = (ActiveProjects) getSupportFragmentManager().findFragmentByTag(
		                        "android:switcher:"+R.id.projectListPager+":0");
		        	    CompletedProjects fragment2 = (CompletedProjects) getSupportFragmentManager().findFragmentByTag(
		                        "android:switcher:"+R.id.projectListPager+":1");
		        	    if(fragment != null) 
		        	      {
		        	         if(fragment.getView() != null) 
		        	         {

		        	            fragment.fillData(); 
		        	         }
		        	      }

		        	    if(fragment2 != null) 
			      	      {
			      	         if(fragment2.getView() != null) 
			      	         {
			      	            fragment2.fillData(); 
			      	         }
			      	      }
		        	    
		        	    /*
						 * 
						 * Sycn Down All User
						 * 
						 */
		        	    
		        	    JSONArray jsonArrayDSUser = json.getJSONArray("userList");

						for (int i = 0; i < jsonArrayDSUser.length(); i++) {

								JSONObject DwUserjsonObject = jsonArrayDSUser.getJSONObject(i);
								String ID = DwUserjsonObject.getString("uId");
								String User_Username = DwUserjsonObject.getString("uUsername");
								String Username = DwUserjsonObject.getString("uName");
								String Phonenumber = DwUserjsonObject.getString("uPhone");

								Calendar CalNow = Calendar.getInstance();
					        	Date DateNow = new Date(CalNow.getTimeInMillis());

					        	userdatasource.createUser(ID, User_Username, Username, Phonenumber, User_Username, DateNow);
							

							}
						
						/*
						 * 
						 * Sycn Down All UserProject
						 * 
						 */
						
						JSONArray jsonArrayDSUserProject = json.getJSONArray("userProjectList");
						for (int i = 0; i < jsonArrayDSUserProject.length(); i++) {

								JSONObject userprojectObject = jsonArrayDSUserProject.getJSONObject(i);
								String upid = userprojectObject.getString("Id");
								String upufidstring = userprojectObject.getString("uId");
								int upufid = Integer.parseInt(upufidstring);
								String uppfidstring = userprojectObject.getString("pId");
								int uppfid = Integer.parseInt(uppfidstring);
								String role = userprojectObject.getString("role");
								String status = userprojectObject.getString("status");
								Calendar CalNow = Calendar.getInstance();
					        	Date DateNow = new Date(CalNow.getTimeInMillis());

								userprojectdatasource.createUser_Project(upid, upufid, uppfid, role, DateNow, status);

							}
						
						/*
						 * 
						 * Sycn Down All UserTask
						 * 
						 */
						
						JSONArray jsonArrayDSTask = json.getJSONArray("taskUserList");
						for (int g = 0; g < jsonArrayDSTask.length(); g++) {

								JSONObject DwUserTaskObject = jsonArrayDSTask.getJSONObject(g);
								
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
						
						
						/*
						 * 
						 * Sycn Down All Task
						 * 
						 */
						
						JSONArray jsonArrayDSUserTask = json.getJSONArray("taskList");
						for (int f = 0; f < jsonArrayDSUserTask.length(); f++) {

								JSONObject DwTaskObject = jsonArrayDSUserTask.getJSONObject(f);
								
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
		        	    

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				//Set menuItem Action off
				menuItem.collapseActionView();
		    	menuItem.setActionView(null);

			}

	    }
		
		/** Sync down all data after login */
		private class DownSycnAllAuto extends JSONParser {

			@SuppressLint("NewApi")
			@Override
			public void processJSON(JSONObject json) {
				try {
					
					/*
					 * 
					 * Sycn Down All Project
					 * 
					 */
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
					JSONArray jsonArray = json.getJSONArray("projectList");
					for (int i = 0; i < jsonArray.length(); i++) {

							

							JSONObject jsonObject = jsonArray.getJSONObject(i);
							String ID = jsonObject.getString("Id");
							String Name = jsonObject.getString("Name");
							
							String Description = jsonObject.getString("Description");
							String StartDateString = jsonObject.getString("StartDate");
							java.util.Date StartDateUtil =  df.parse(StartDateString); 
							java.sql.Date StartDate = new java.sql.Date(StartDateUtil.getTime());
							String DueDateString = jsonObject.getString("DueDate");
							java.util.Date DueDateUtil =  df.parse(DueDateString);
							java.sql.Date DueDate = new java.sql.Date(DueDateUtil.getTime());
							String Completion = jsonObject.getString("Completion");
							String Status = jsonObject.getString("Status");
							Calendar CalNow = Calendar.getInstance();
				        	Date DateNow = new Date(CalNow.getTimeInMillis());

							projectdatasource.createProject(ID, Name,Description,StartDate,DueDate,Completion,DateNow,Status);

						}

		        	    ActiveProjects fragment = (ActiveProjects) getSupportFragmentManager().findFragmentByTag(
		                        "android:switcher:"+R.id.projectListPager+":0");
		        	    CompletedProjects fragment2 = (CompletedProjects) getSupportFragmentManager().findFragmentByTag(
		                        "android:switcher:"+R.id.projectListPager+":1");
		        	    if(fragment != null) 
		        	      {
		        	         if(fragment.getView() != null) 
		        	         {

		        	            fragment.fillData(); 
		        	         }
		        	      }

		        	    if(fragment2 != null) 
			      	      {
			      	         if(fragment2.getView() != null) 
			      	         {
			      	            fragment2.fillData(); 
			      	         }
			      	      }
		        	    
		        	    /*
						 * 
						 * Sycn Down All User
						 * 
						 */
		        	    
		        	    JSONArray jsonArrayDSUser = json.getJSONArray("userList");

						for (int i = 0; i < jsonArrayDSUser.length(); i++) {

								JSONObject DwUserjsonObject = jsonArrayDSUser.getJSONObject(i);
								String ID = DwUserjsonObject.getString("uId");
								String User_Username = DwUserjsonObject.getString("uUsername");
								String Username = DwUserjsonObject.getString("uName");
								String Phonenumber = DwUserjsonObject.getString("uPhone");

								Calendar CalNow = Calendar.getInstance();
					        	Date DateNow = new Date(CalNow.getTimeInMillis());

								userdatasource.createUser(ID, User_Username, Username, Phonenumber, User_Username, DateNow);
								
							}
						
						/*
						 * 
						 * Sycn Down All UserProject
						 * 
						 */
						
						JSONArray jsonArrayDSUserProject = json.getJSONArray("userProjectList");
						for (int i = 0; i < jsonArrayDSUserProject.length(); i++) {

								JSONObject userprojectObject = jsonArrayDSUserProject.getJSONObject(i);
								String upid = userprojectObject.getString("Id");
								String upufidstring = userprojectObject.getString("uId");
								int upufid = Integer.parseInt(upufidstring);
								String uppfidstring = userprojectObject.getString("pId");
								int uppfid = Integer.parseInt(uppfidstring);
								String role = userprojectObject.getString("role");
								String status = userprojectObject.getString("status");
								Calendar CalNow = Calendar.getInstance();
					        	Date DateNow = new Date(CalNow.getTimeInMillis());

								userprojectdatasource.createUser_Project(upid, upufid, uppfid, role, DateNow, status);

							}
						
						/*
						 * 
						 * Sycn Down All UserTask
						 * 
						 */
						
						JSONArray jsonArrayDSTask = json.getJSONArray("taskUserList");
						for (int g = 0; g < jsonArrayDSTask.length(); g++) {

								JSONObject DwUserTaskObject = jsonArrayDSTask.getJSONObject(g);
								
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
						
						
						/*
						 * 
						 * Sycn Down All Task
						 * 
						 */
						
						JSONArray jsonArrayDSUserTask = json.getJSONArray("taskList");
						for (int f = 0; f < jsonArrayDSUserTask.length(); f++) {

								JSONObject DwTaskObject = jsonArrayDSUserTask.getJSONObject(f);
								
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
		        	    

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}

	    }


	@Override
	public void onBackPressed() {	
		syncupalldata();
		exitAppConfirmation();
	}

	/** Move to ChangePassword activity*/
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
	        	
	        	
	        	 userprojectdatasource.deleteAllUserProject();
	        	 usertaskdatasource.deleteAllUserTask();
	        	 userdatasource.deleteAllUser();
	        	 taskdatasource.deleteAllTask();
	        	 projectdatasource.deleteAllProject();
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
	
	private void syncupalldata(){
		InternetCheck internet = new InternetCheck();
		boolean internetOn = internet.internetOn(MainActivity.this);
		if (internetOn) {
		
		//implement syncuptask here
		JSONParserSend syncuptask = new JSONParserSend();
		syncuptask.setContext(MainActivity.this);
		
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
			syncupusertask.setContext(MainActivity.this);
    		
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

    			
		}
		else {
			
	    	internet.NetworkError(MainActivity.this);
			
		}
	}
}