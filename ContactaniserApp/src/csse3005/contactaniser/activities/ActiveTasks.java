package csse3005.contactaniser.activities;

import java.util.ArrayList;

import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniser.models.Task;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import csse3005.contactaniser.datasource.ProjectDataSource;

public class ActiveTasks extends ListFragment {

	private TaskDataSource taskdatabase;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		taskdatabase = new TaskDataSource(getActivity());
		taskdatabase.open();

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	private void fillData() {
		/** Creating array adapter to set data in listview */
		long pid = getActivity().getIntent().getExtras().getLong("projId");
//        List<Project> values = projectdatasource.getAllProjects(0);
		ArrayList<Task> values = taskdatabase.getAllTasks(pid, 0);
        /** Setting the array adapter to the listview */
        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(getActivity(),
                android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
    }
	
	@Override
    public void onStart() {
            super.onStart();
            fillData();
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String itemtext = l.getItemAtPosition(position).toString(); 
		Task itemId = (Task) getListAdapter().getItem(position);
		Intent taskIntent = new Intent(getActivity(), TaskActivity.class);
		taskIntent.putExtra("taskName", itemtext);
		taskIntent.putExtra("taskid", itemId.getTaskid());
		startActivity(taskIntent);
		
	}

	@Override
	public void onResume() {
		
		super.onResume();
		fillData();
	}
	
	
	
}