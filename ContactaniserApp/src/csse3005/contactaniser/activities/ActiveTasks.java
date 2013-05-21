package csse3005.contactaniser.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniser.models.Task;
import csse3005.contactaniserapp.R;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ActiveTasks extends Fragment {
	private boolean byDate = false;
	private TaskDataSource DatabaseHelper;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.grid_layout, container, false);
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		fillTaskData();
	}


	public void fillTaskData() {
		// retrieve tasks
		DatabaseHelper = new TaskDataSource(getActivity());
		DatabaseHelper.open();
		ArrayList<Task> rawList = DatabaseHelper.getAllTasks(getActivity().getIntent().getLongExtra("projId", -1), 0);
		DatabaseHelper.close();
		
		GridLayout taskGrid = (GridLayout) getActivity().findViewById(R.id.task_tile_grid);
		taskGrid.removeAllViews();

		// get screen dimensions
		Point screenSize = new Point();
		getActivity().getWindowManager().getDefaultDisplay().getSize(screenSize);
		int halfScreenWidthX = (screenSize.x - 4) / 2;
		
		// make needed objects
		Button taskButton;
		GridLayout.LayoutParams param;
		int currentTaskImportance;
		
		// Add 'no tasks' text if needed
		if (rawList.size() == 0) {
			TextView noTasks = new TextView(getActivity());
			noTasks.setText("No Tasks");
			param = new GridLayout.LayoutParams();
			param.columnSpec = GridLayout.spec(0, 2);
//			param.rowSpec = GridLayout.spec(0, 5);
			param.setGravity(Gravity.CENTER);
			noTasks.setTextSize(20);
			noTasks.setLayoutParams(param);
			taskGrid.addView(noTasks);
			return;
		}
		
		ArrayList<Task> taskList = orderTasks(rawList, byDate);
		
		for (int i=0; i < taskList.size(); i++) {
			// get task importance by date or property
			
			// button properties
			taskButton = new Button(getActivity());
			taskButton.setText(taskList.get(i).getTaskName());
			taskButton.setTag(taskList.get(i).getTaskid());
			taskButton.setTextColor(getResources().getColor(R.color.task_tile_text_color));
			taskButton.setBackgroundResource(catToCol(taskList.get(i).getTaskCategory()));
			
			param = new GridLayout.LayoutParams();
			
			// set tile size & background based on priority
			switch (calcTaskImportance(taskList.get(i), byDate)) {
			case 2:
				param.height = 200;// R.dimen.task_tile_p2_height;
		        param.width = halfScreenWidthX*2;
		        param.columnSpec = GridLayout.spec(0, 2);
				break;
			case 1:
				param.height = halfScreenWidthX;
		        param.width = halfScreenWidthX;
		        param.rowSpec = GridLayout.spec(1, 2);
				break;
			case 0:
				param.height = halfScreenWidthX/2;
		        param.width = halfScreenWidthX;
				break;
			}
			// apply parameters
			taskButton.setLayoutParams (param);
			
			// start task view activity on click
			taskButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent pIntent = new Intent(getActivity(), TaskActivity.class);
					pIntent.putExtra("taskid", (Long) view.getTag());
					pIntent.putExtra("projectid", getActivity().getIntent().getExtras().getLong("projId"));
					pIntent.putExtra("userid", getActivity().getIntent().getIntExtra("userid", 0));
					startActivity(pIntent);
				}
			});
	        taskGrid.addView(taskButton);
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
//    		tDate.set(Calendar.MONTH, tDate.get(Calendar.MONTH - 1));
    		
    		Calendar today = Calendar.getInstance();
    		
    		Calendar weekFromNow = Calendar.getInstance();
    		weekFromNow.add(Calendar.DAY_OF_YEAR, 7);
    		
    		System.out.println("Task " + inTask.getTaskName() + " detail:");
    		System.out.println("Task date  : " + tDate.getTime().toLocaleString());
    		System.out.println("Task year  : " + tDate.get(Calendar.YEAR));
    		System.out.println("Task month : " + tDate.get(Calendar.MONTH));
    		System.out.println("Task DOY  : " + tDate.get(Calendar.DAY_OF_YEAR));
    		System.out.println("----");
    		System.out.println("Today date : " + today.getTime().toLocaleString());
    		System.out.println("Today year : " + today.get(Calendar.YEAR));
    		System.out.println("Today month: " + today.get(Calendar.MONTH));
    		System.out.println("Today DOY  : " + today.get(Calendar.DAY_OF_YEAR));
    		System.out.println("----");
    		System.out.println("Week date  : " + weekFromNow.getTime().toLocaleString());
    		System.out.println("Week year  : " + weekFromNow.get(Calendar.YEAR));
    		System.out.println("Week month : " + weekFromNow.get(Calendar.MONTH));
    		System.out.println("Week DOY   : " + weekFromNow.get(Calendar.DAY_OF_YEAR));
    		System.out.println("----");
    		
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
	
	public boolean getByDate() {
		return byDate;
	}
	
	public void setByDate(boolean set) {
		byDate = set;
	}
	
}