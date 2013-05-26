package csse3005.contactaniser.activities;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_TaskDataSource;
import csse3005.contactaniser.models.Task;
import csse3005.contactaniser.models.User;
import csse3005.contactaniser.models.User_Task;
import csse3005.contactaniserapp.R;

public class CompletedTasks extends Fragment {
	private boolean byDate = false;
	private TaskDataSource taskdatasource;
	
	private UserDataSource userdatasource;
	private User_TaskDataSource usertaskdatasource;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		taskdatasource = new TaskDataSource(getActivity());
		taskdatasource.open();
		
		userdatasource = new UserDataSource(getActivity());
		userdatasource.open();
		
		usertaskdatasource = new User_TaskDataSource(getActivity());
		usertaskdatasource.open();
		
		return inflater.inflate(R.layout.grid_layout_completed, container, false);
	}
	
	@Override
	public void onDestroy() {
		taskdatasource.close();
		userdatasource.close();
		usertaskdatasource.close();
		super.onDestroy();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		fillTaskData();
	}

	/** Converts a Date object into a string to display on task tiles.
	 * Returns 'TODAY', 'TOMORROW', or 'dd/mm/yyyy' depending on date. */
	private String stringDate(Date inDate) {
		Calendar tDate = Calendar.getInstance();
		tDate.setTime(inDate);
		Calendar today = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_YEAR, 1);
		
		if (tDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) && tDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
			return "TODAY";
		} else if (tDate.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR) && tDate.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR)) {
			return "TOMORROW";
		} else {
			return new SimpleDateFormat("d/M/yyyy", Locale.UK).format(inDate);
		}
	}
	
	/** populates tiles */
	public void fillTaskData() {
		// retrieve tasks
		ArrayList<Task> rawList = taskdatasource.getAllTasks(getActivity().getIntent().getLongExtra("projId", -1), 1);
		
		GridLayout taskGrid = (GridLayout) getActivity().findViewById(R.id.task_tile_grid_completed);
		taskGrid.removeAllViews();

		// get screen dimensions
		Point screenSize = new Point();
		getActivity().getWindowManager().getDefaultDisplay().getSize(screenSize);
		int halfScreenWidthX = (screenSize.x - 4) / 2;
		
		// make needed objects
		RelativeLayout taskTile;
		GridLayout.LayoutParams tileParams;
		RelativeLayout.LayoutParams nameParams;
		RelativeLayout.LayoutParams dueDateParams;
		RelativeLayout.LayoutParams memberParams;
		TextView taskName;
		TextView taskDueDate;
		TextView taskMembers;
		TextView noTasks = (TextView) getActivity().findViewById(R.id.no_completed_tasks_text);
		
		// Add 'no tasks' text if needed
		if (rawList.size() == 0) {
			noTasks.setVisibility(View.VISIBLE);
			return;
		} else {
			noTasks.setVisibility(View.GONE);
		}
		
		ArrayList<Task> taskList = orderTasks(rawList, byDate);
		
		for (int i=0; i < taskList.size(); i++) {
			
			// tile properties
			taskTile = new RelativeLayout(getActivity());
			tileParams = new GridLayout.LayoutParams();
			taskTile.setBackgroundResource(catToCol(taskList.get(i).getTaskCategory()));
			taskTile.setTag(taskList.get(i).getTaskid());
			
			// add task name
			taskName = new TextView(getActivity());
			nameParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			taskName.setText(taskList.get(i).getTaskName());
			taskName.setTextSize(20);
			taskName.setEllipsize(TextUtils.TruncateAt.END);
			taskName.setTextColor(getResources().getColor(R.color.task_tile_text_color));
			nameParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			nameParams.setMargins(50, 0, 50, 0);
			
			// add task due date
			taskDueDate = new TextView(getActivity());
			dueDateParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			taskDueDate.setText(stringDate(taskList.get(i).getTaskDueDate()));
			taskDueDate.setTextSize(14);
			taskDueDate.setTextColor(getResources().getColor(R.color.task_tile_text_color));
			dueDateParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			dueDateParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			dueDateParams.setMargins(15, 10, 0, 0);
			
			// add task members
			taskMembers = new TextView(getActivity());
			memberParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			taskMembers.setText(grabUserInitialsByTaskID(taskList.get(i).getTaskid()));
			taskMembers.setTextSize(14);
			taskMembers.setTextColor(getResources().getColor(R.color.task_tile_text_color));
			memberParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			memberParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
			memberParams.setMargins(0, 0, 15, 10);
			
			// set tile size & background based on priority
			tileParams.height = 150;// R.dimen.task_tile_p2_height;
	        tileParams.width = halfScreenWidthX*2;
	        tileParams.columnSpec = GridLayout.spec(0, 2);
	        taskName.setMaxLines(1);
			
			taskTile.addView(taskName, nameParams);
			taskTile.addView(taskDueDate, dueDateParams);
			taskTile.addView(taskMembers, memberParams);
			
			// start task view activity on click
			taskTile.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent pIntent = new Intent(getActivity(), TaskActivityComplete.class);
					pIntent.putExtra("taskid", (Long) view.getTag());
					pIntent.putExtra("projectid", getActivity().getIntent().getExtras().getLong("projId"));
					pIntent.putExtra("userid", getActivity().getIntent().getIntExtra("userid", 0));
					startActivity(pIntent);
				}
			});
	        taskGrid.addView(taskTile, tileParams);
		}
	}
	
	/** returns task list ordered by date or task property */
	private ArrayList<Task> orderTasks(ArrayList<Task> inTaskList, boolean byDate) {
		ArrayList<Task> p0 = new ArrayList<Task>();
		ArrayList<Task> p1 = new ArrayList<Task>();
		ArrayList<Task> p2 = new ArrayList<Task>();
		
    	for (int i=0; i < inTaskList.size(); i++) {
    		switch (calcTaskImportance(inTaskList.get(i), byDate)) {
    		case 0:
    			p0.add(inTaskList.get(i));
    			break;
    		case 1:
    			p1.add(inTaskList.get(i));
    			break;
    		case 2:
    			p2.add(inTaskList.get(i));
    			break;
    		}
    	}
    	
    	p2.addAll(p1);
    	p2.addAll(p0);
    	return p2;
	}
	
	/** calculates task priority based on date if needed */
	private int calcTaskImportance(Task inTask, boolean byDate) {
    	if (byDate) {
    		Calendar tDate = Calendar.getInstance();
    		tDate.setTime(inTask.getTaskDueDate());
    		
    		Calendar today = Calendar.getInstance();
    		
    		Calendar weekFromNow = Calendar.getInstance();
    		weekFromNow.add(Calendar.DAY_OF_YEAR, 7);
    		
    		if ((tDate.get(Calendar.YEAR) < today.get(Calendar.YEAR)) || 
    				(tDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) && tDate.get(Calendar.DAY_OF_YEAR) <= today.get(Calendar.DAY_OF_YEAR))) {
    			return 2;
    		} else if (tDate.get(Calendar.YEAR) < weekFromNow.get(Calendar.YEAR) || 
    				(tDate.get(Calendar.YEAR) == weekFromNow.get(Calendar.YEAR) && tDate.get(Calendar.DAY_OF_YEAR) < weekFromNow.get(Calendar.DAY_OF_YEAR))) {
    			return 1;
    		} else {
    			return 0;
    		}
    	} else {
    		return inTask.getTaskImportanceLevel();
    	}
    }
	
	/** A supposedly tempory method to assign tile backgrounds (namely colour) based on task type.
	 * This would be replaced by more complete colour-control functionality if we had time. */
	private int catToCol(int tCat) {
    	switch (tCat) {
    	case 0:
    		return R.drawable.button_background_1;
    	case 1:
    		return R.drawable.button_background_2;
    	case 2:
    		return R.drawable.button_background_3;
    	case 3:
    		return R.drawable.button_background_4;
    	default:
    		return R.drawable.button_background_5;
    	}
    }
	
	/** returns a string of the initials of members of a given task ID formatted "AB CD EF" */
	private String grabUserInitialsByTaskID(long tID) {
		ArrayList<User> userList = grabUsersByTaskID(tID);
		String[] splitName;
		StringBuilder initList = new StringBuilder();
		
		for (int i=0; i< userList.size(); i++) {
			if (initList.length()!=0) initList.append(" ");
//			initList.append(userList.get(i).getUserName());
			splitName = userList.get(i).getUserName().split(" ");
			for (int x=0; x < splitName.length; x++) {
				initList.append(splitName[x].substring(0, 1).toUpperCase(Locale.US));
			}
		}
		
		return initList.toString();
	}
	
	/** returns ArrayList of users by task ID. 
	 * DISOBAYS ARCHITECTURE: BELONGS IN ANOTHER CLASS */
	private ArrayList<User> grabUsersByTaskID(long tID) {
	
		ArrayList<User_Task> values = usertaskdatasource.getAllUserbyTaskId(tID);
		ArrayList<User> userlist = new ArrayList<User>();
		
        for (int i=0; i<values.size(); i++){
        	
        	User_Task  usertask = values.get(i);
            long userid = usertask.getUTUid();
            if (tID != userid){
            Cursor c = userdatasource.fetchUserById(userid);

            c.moveToFirst();
			while (!c.isAfterLast()) {
				User user = cursorToUser(c);
				userlist.add(user);
			    c.moveToNext();
			}
			c.close();
            
            }
         }
        
        return userlist;
	}
	
	/** needed for grabUsersByTaskID */
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
	
	public boolean getByDate() {
		return byDate;
	}
	
	public void setByDate(boolean set) {
		byDate = set;
	}
	
}