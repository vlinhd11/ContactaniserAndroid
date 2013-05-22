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

public class CompletedTasks extends ListFragment {

	private TaskDataSource taskdatabase;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		taskdatabase = new TaskDataSource(getActivity());
		taskdatabase.open();
//		setEmptyText("No Tasks");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	private void fillData() {
		/** Creating array adapter to set data in listview */
		long pid = getActivity().getIntent().getExtras().getLong("projId");
		ArrayList<Task> values = taskdatabase.getAllTasks(pid, 1);
        /** Setting the array adapter to the listview */
        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
	
	@Override
    public void onStart() {
            super.onStart();
            setEmptyText("No completed tasks");
            fillData();
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String itemtext = l.getItemAtPosition(position).toString(); 
		Task itemId = (Task) getListAdapter().getItem(position);
		Intent pIntent = new Intent(getActivity(), TaskActivity.class);
		pIntent.putExtra("taskid", itemId.getTaskid());
		pIntent.putExtra("projectid", getActivity().getIntent().getExtras().getLong("projId"));
		pIntent.putExtra("userid", getActivity().getIntent().getIntExtra("userid", 0));
		startActivity(pIntent);
		
	}

	@Override
	public void onResume() {
		super.onResume();
		fillData();
	}
	
	
	
}