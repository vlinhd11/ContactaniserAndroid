package csse3005.contactaniser.activities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.text.SimpleDateFormat;
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
		RelativeLayout taskTile;
		GridLayout.LayoutParams tileParams;
		RelativeLayout.LayoutParams textParams;
		TextView taskText;
		TextView noTasks = (TextView) getActivity().findViewById(R.id.no_tasks_text);
		
		// Add 'no tasks' text if needed
		if (rawList.size() == 0) {
			noTasks.setVisibility(View.VISIBLE);
			return;
		} else {
			noTasks.setVisibility(View.GONE);
		}
		
		boolean p1Right = false;
		ArrayList<Task> taskList = orderTasks(rawList, byDate);
		
		for (int i=0; i < taskList.size(); i++) {
			
			// tile properties
			taskTile = new RelativeLayout(getActivity());
			tileParams = new GridLayout.LayoutParams();
			taskTile.setBackgroundResource(catToCol(taskList.get(i).getTaskCategory()));
			taskTile.setTag(taskList.get(i).getTaskid());
			
			taskText = new TextView(getActivity());
			textParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			taskText.setText(taskList.get(i).getTaskName());
			taskText.setTextSize(20);
			taskText.setTextColor(getResources().getColor(R.color.task_tile_text_color));
			textParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			taskTile.addView(taskText, textParams);
			
			taskText = new TextView(getActivity());
			textParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			taskText.setText(stringDate(taskList.get(i).getTaskDueDate()));
			taskText.setTextSize(14);
			taskText.setTextColor(getResources().getColor(R.color.task_tile_text_color));
			textParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			textParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			textParams.setMargins(15, 10, 0, 0);
			taskTile.addView(taskText, textParams);
			
			
			// set tile size & background based on priority
			switch (calcTaskImportance(taskList.get(i), byDate)) {
			case 2:
				tileParams.height = 200;// R.dimen.task_tile_p2_height;
		        tileParams.width = halfScreenWidthX*2;
		        tileParams.columnSpec = GridLayout.spec(0, 2);
				break;
			case 1:
				tileParams.height = halfScreenWidthX;
		        tileParams.width = halfScreenWidthX;
		        
		        tileParams.rowSpec = GridLayout.spec(p1Right ? 1 : 0, 2);
		        
		        p1Right = !p1Right;
				break;
			case 0:
				tileParams.height = halfScreenWidthX/2;
		        tileParams.width = halfScreenWidthX;
				break;
			}
			
			// start task view activity on click
			taskTile.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent pIntent = new Intent(getActivity(), TaskActivity.class);
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
	
	public boolean getByDate() {
		return byDate;
	}
	
	public void setByDate(boolean set) {
		byDate = set;
	}
	
}