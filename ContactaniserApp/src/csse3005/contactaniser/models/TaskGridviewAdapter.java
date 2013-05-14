package csse3005.contactaniser.models;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import csse3005.contactaniserapp.R;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class TaskGridviewAdapter extends BaseAdapter {
	private ArrayList<Task> taskList;
	private ArrayList<TaskTile> tileList;
	private Activity parentActivity;
	
	private final int TILE_BLANK = 0;
	private final int TILE_SINGLE = 1;
	private final int TILE_LEFT = 2;
	private final int TILE_RIGHT = 3;
	private final int TILE_TOPLEFT = 4;
	private final int TILE_TOPRIGHT = 5;
	private final int TILE_BOTTOMLEFT = 6;
	private final int TILE_BOTTOMRIGHT = 7;
	
	public TaskGridviewAdapter(Activity parentActivity, ArrayList<Task> taskList, boolean byDate) {
		super();
		this.taskList = taskList;
		this.tileList = new ArrayList<TaskTile>();
		this.parentActivity = parentActivity; // TODO: may need to be replaced with 'fragment'
		createTaskTiles(byDate);
	}
	
	@Override
	public int getCount() {
		return tileList.size();
	}

	@Override
	public TaskTile getItem(int position) {
		return tileList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO needed beyond this stub?
		return 0;
	}

	public static class ViewHolder {
		public TextView txtViewTitle;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder view;
		LayoutInflater inflator = parentActivity.getLayoutInflater();
		
		if(convertView==null) {
			view = new ViewHolder();
			
			Drawable tileBackground = null;
			
			switch (tileList.get(position).getType()) {
			case TILE_SINGLE:
				// tile type:
				convertView = inflator.inflate(R.layout.task_grid_name, null);
				// boarders:
				tileBackground = parent.getResources().getDrawable(R.drawable.tile_border_full);
				break;
			case TILE_LEFT:
				convertView = inflator.inflate(R.layout.task_grid_name, null);
				tileBackground = parent.getResources().getDrawable(R.drawable.tile_border_left);
				break;
			case TILE_RIGHT:
				convertView = inflator.inflate(R.layout.task_grid_date, null);
				tileBackground = parent.getResources().getDrawable(R.drawable.tile_border_right);
				break;
			case TILE_TOPLEFT:
				convertView = inflator.inflate(R.layout.task_grid_name, null);
				tileBackground = parent.getResources().getDrawable(R.drawable.tile_border_topleft);
				break;
			case TILE_TOPRIGHT:
				convertView = inflator.inflate(R.layout.task_grid_date, null);
				tileBackground = parent.getResources().getDrawable(R.drawable.tile_border_topright);
				break;
			case TILE_BOTTOMLEFT:
				convertView = inflator.inflate(R.layout.task_grid_description, null);
				tileBackground = parent.getResources().getDrawable(R.drawable.tile_border_bottomleft);
				break;
			case TILE_BOTTOMRIGHT:
				convertView = inflator.inflate(R.layout.task_grid_blank, null); // TODO: maybe change
				tileBackground = parent.getResources().getDrawable(R.drawable.tile_border_bottomright);
				break;
			}
			
			// colour:
			tileBackground.setColorFilter(tileList.get(position).getColor(), PorterDuff.Mode.DARKEN);
			// apply background to grid element
			convertView.setBackground(tileBackground);
			
			view.txtViewTitle = (TextView) convertView.findViewById(R.id.TileText);
			
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}
		
		view.txtViewTitle.setText(tileList.get(position).getText());
		
		return convertView;
	}
	
	/** populates tile list */
    public void createTaskTiles(boolean byDate) {
    	if (!byDate) {
			ArrayList<Task> p0 = new ArrayList<Task>();
			ArrayList<Task> p1 = new ArrayList<Task>();
			ArrayList<Task> p2 = new ArrayList<Task>();
	    	for (int i=0; i< taskList.size(); i++) {
	    		switch (taskList.get(i).getTaskImportanceLevel()) {
	    		case 0:
	    			p0.add(taskList.get(i));
	    			break;
	    		case 1:
	    			p1.add(taskList.get(i));
	    			break;
	    		case 2:
	    			p2.add(taskList.get(i));
	    			break;
	    		}
	    	}
	    	
	    	// TODO: double check this is the correct order (lowest importance num first)
	    	taskList.clear();
	    	taskList.addAll(p2);
	    	taskList.addAll(p1);
	    	taskList.addAll(p0);
	    }
    	
    	boolean replaceBlanks = false;
    	
    	for (int i=0; i < taskList.size(); i++) {
    		switch (calcTaskImportance(taskList.get(i), byDate)) {
    		case 2:
    			addTile(taskList.get(i).getTaskid(), catToCol(taskList.get(i).getTaskCategory()), TILE_TOPLEFT, taskList.get(i).getTaskName(), replaceBlanks);
    			addTile(taskList.get(i).getTaskid(), catToCol(taskList.get(i).getTaskCategory()), TILE_TOPRIGHT, "Due: " + DateFormat.getInstance().format(taskList.get(i).getTaskDueDate()), replaceBlanks);
    			
    			addTile(0, 0, TILE_BLANK, null, replaceBlanks);
    			addTile(0, 0, TILE_BLANK, null, replaceBlanks);
    			
    			addTile(taskList.get(i).getTaskid(), catToCol(taskList.get(i).getTaskCategory()), TILE_BOTTOMLEFT, taskList.get(i).getTaskDescription(), replaceBlanks);
    			addTile(taskList.get(i).getTaskid(), catToCol(taskList.get(i).getTaskCategory()), TILE_BOTTOMRIGHT, "", replaceBlanks);
    			
    			replaceBlanks = !replaceBlanks;
    			break;
    		case 1:
    			addTile(taskList.get(i).getTaskid(), catToCol(taskList.get(i).getTaskCategory()), TILE_LEFT, taskList.get(i).getTaskName(), true);
    			addTile(taskList.get(i).getTaskid(), catToCol(taskList.get(i).getTaskCategory()), TILE_RIGHT, "Due: " + DateFormat.getInstance().format(taskList.get(i).getTaskDueDate()), true);
    			break;
    		case 0:
    			addTile(taskList.get(i).getTaskid(), catToCol(taskList.get(i).getTaskCategory()), TILE_SINGLE, taskList.get(i).getTaskName(), true);
    			break;
    		}
    		
    	}
    	
    	if (tileList.get(tileList.size()-1).getType() == TILE_BLANK) tileList.remove(tileList.size()-1);
    }
    
    /** adds tile, either by filling in the oldest blank tile or adding to the end */
    private void addTile(long tID, int tColor, int tType, String tText, boolean replaceBlanks) {
    	if (tType == TILE_BLANK) {
    		tileList.add(new TaskTile(tID, tColor, tType, tText));
    	} else {
    		if (replaceBlanks) {
		    	for (int i=0; i<tileList.size(); i++) {
		    		if (tileList.get(i).getType() == TILE_BLANK) {
		    			tileList.set(i, new TaskTile(tID, tColor, tType, tText));
		    			return;
		    		}
		    	}
		    	tileList.add(new TaskTile(tID, tColor, tType, tText));
    		} else {
    			tileList.add(new TaskTile(tID, tColor, tType, tText));
    		}
    	}
    }
    
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
    
    private int catToCol(int tCat) {
    	switch (tCat) {
    	case 0:
    		return Color.RED;
    	case 1:
    		return Color.CYAN;
    	case 2:
    		return Color.GREEN;
    	case 3:
    		return Color.YELLOW;
    	default:
    		return Color.WHITE;
    	}
    }

}
