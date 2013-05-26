package csse3005.contactaniser.activities;

import java.sql.Date;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_TaskDataSource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User;
import csse3005.contactaniser.models.User_Task;
import csse3005.contactaniserapp.R;

/**
 * Activity to display Completed Task Information
 */

public class TaskActivityComplete extends Activity {

	private static TextView tasknametextview;
	private static TextView taskdescriptiontextview;
	private static TextView taskcategorytextview;
	private static TextView taskimportancetextview;
	private static TextView taskduedatetextview;

	String taskname;
	String taskdescription;
	String taskcategory;
	int taskcategoryindex;
	String taskimportance;
	int taskimportanceindex;
	String taskduedate;
	
	private TaskDataSource taskdatasource;
	private UserDataSource userdatasource;
	private User_TaskDataSource usertaskdatasource;
	
	private ListView listviewmember;
	ArrayList<User> userview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_completed);
		
		//Open Database
		taskdatasource = new TaskDataSource(this);
		taskdatasource.open();
		
		userdatasource = new UserDataSource(this);
		userdatasource.open();
		
		usertaskdatasource = new User_TaskDataSource(this);
		usertaskdatasource.open();
		
		//Initiate TextView
		tasknametextview = (TextView) findViewById(R.id.tasktextViewName);
		taskdescriptiontextview = (TextView) findViewById(R.id.tasktextViewDescription);
		taskcategorytextview = (TextView) findViewById(R.id.tasktextCategory);
		taskimportancetextview = (TextView) findViewById(R.id.tasktextImportance);
		taskduedatetextview = (TextView) findViewById (R.id.tasktextDueDate);

		//Get the value from previous activity
		long mrowtaskid = getIntent().getExtras().getLong("taskid");
		long mrowuserid = getIntent().getIntExtra("userid", 0);
		
		//Set the cursor
		Cursor task = taskdatasource.fetchTaskById(mrowtaskid);
		
		//Get the value from database
		taskname = task.getString(task.getColumnIndexOrThrow(MySQLHelper.COLUMN_TASKNAME));
		taskdescription = task.getString(task.getColumnIndexOrThrow(MySQLHelper.COLUMN_TASKDESCRIPTION));
		taskcategoryindex = task.getInt(task.getColumnIndexOrThrow(MySQLHelper.COLUMN_TASKCATEGORY));
		taskimportanceindex = task.getInt(task.getColumnIndexOrThrow(MySQLHelper.COLUMN_TASKIMPORTANCELEVEL));
		taskduedate = task.getString(task.getColumnIndexOrThrow(MySQLHelper.COLUMN_TASKDUEDATE));
		
		//Change the value of index to actual value in String
		if (taskimportanceindex == 0)
		{
			taskimportance = "Low";
		}
		if (taskimportanceindex == 1)
		{
			taskimportance = "Medium";
		}
		if (taskimportanceindex == 2)
		{
			taskimportance = "High";
		}
		
		if (taskcategoryindex == 0)
		{
			taskcategory = "TODO";
		}
		if (taskcategoryindex == 1)
		{
			taskcategory = "Meeting";
		}
		if (taskcategoryindex == 2)
		{
			taskcategory = "Contact";
		}
		if (taskcategoryindex == 3)
		{
			taskcategory = "Other";
		}
		
		tasknametextview.setText(taskname);
		taskdescriptiontextview.setText(taskdescription);
		taskcategorytextview.setText(taskcategory);
		taskimportancetextview.setText(taskimportance);
		taskduedatetextview.setText(String.valueOf(taskduedate));
		
		listviewmember = (ListView) findViewById(R.id.tasklistMember);
		
		//Get User_Task value
		ArrayList<User_Task> values = usertaskdatasource.getAllUserbyTaskId(mrowtaskid);
		//Set the Member TextView gone if there are no other member
		if (values.size()==1)
		{
			View b = findViewById(R.id.memberlabel);
        	b.setVisibility(View.GONE);
		}
		//Convert User_Task to User value by by UserId from User_Task
		ArrayList<User> userlist = new ArrayList<User>();
	        for (int i=0; i<values.size(); i++){
	        	
	        	User_Task  usertask = values.get(i);
	            long userid = usertask.getUTUid();
	            if (mrowuserid != userid){
	            Cursor c = userdatasource.fetchUserById(userid);

	            c.moveToFirst();
				while (!c.isAfterLast()) {
					User user = cursorToUser(c);
					userlist.add(user);
				    c.moveToNext();
				}
				// Make sure to close the cursor
				c.close();
	            
	            }
	            
	         }
	        
	        
	        
	        final ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
	                R.layout.member_row, R.id.memberLable, userlist);
	        listviewmember.setAdapter(adapter);
	        
	        listviewmember.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					Intent pIntent = new Intent(TaskActivityComplete.this, MemberInfoActivity.class);
					pIntent.putExtra("taskid", getIntent().getExtras().getLong("taskid"));
					pIntent.putExtra("userid", adapter.getItem(position).getUserid());
					pIntent.putExtra("projectid", getIntent().getExtras().getLong("projectid"));
					startActivity(pIntent);
				}
			});
	        setTitle("Task: " + taskname);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task, menu);
		return true;
	}
	
	/** Cursor to pass User value
	 * @param cursor of User Database
	 * 
	 * @return User Object
	 *  
	 */
	private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setUserid(cursor.getInt(0));
		user.setUser_UserName(cursor.getString(1));
		user.setUserName(cursor.getString(2));
		user.setUserPhoneNumber(cursor.getString(3));
		user.setUserEmail(cursor.getString(4));
		Date lu = Date.valueOf(cursor.getString(5));
		user.setUserLastUpdate(lu);
		
		return user;
	}	

}
