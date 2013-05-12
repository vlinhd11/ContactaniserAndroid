package csse3005.contactaniser.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniser.models.Task;
import csse3005.contactaniser.models.TaskGridviewAdapter;
import csse3005.contactaniserapp.R;

public class ActiveTasks extends Fragment {
    /** Called when the activity is first created. */
	
	private TaskGridviewAdapter mAdapter;
	private ArrayList<Task> taskList;
	private boolean byDate = false;
	private TaskDataSource DatabaseHelper;
	
	private GridView gridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.task_grid, container, false);
	}
	
	@Override
	public void onStart() {
		
		DatabaseHelper = new TaskDataSource(getActivity());
		DatabaseHelper.open();
		taskList = DatabaseHelper.getAllTasks(getActivity().getIntent().getLongExtra("projId", -1), 0);
		DatabaseHelper.close();
		
        gridView = (GridView) getActivity().findViewById(R.id.task_grid_view);
        
        if (taskList.size() == 0) {
        	TextView noTasks = (TextView) getActivity().findViewById(R.id.txtNoTasks);
        	gridView.setVisibility(View.GONE);
        	noTasks.setVisibility(View.VISIBLE);
        } else {
	        mAdapter = new TaskGridviewAdapter(getActivity(), taskList, byDate);
	        gridView.setAdapter(mAdapter);
	        
	        // Implement On Item click listener
	        gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					Intent pIntent = new Intent(getActivity(), TaskActivity.class);
					pIntent.putExtra("taskID", mAdapter.getItem(position).getTaskID());
					startActivity(pIntent);
				}
			});
        }
        super.onStart();
	}
	
}