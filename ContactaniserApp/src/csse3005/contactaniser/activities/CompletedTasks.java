package csse3005.contactaniser.activities;

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

//	private ProjectDataSource projectdatasource;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
//		projectdatasource = new ProjectDataSource(getActivity());
//        projectdatasource.open();

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	private void fillData() {
		/** Creating array adapter to set data in listview */
//        List<Project> values = projectdatasource.getAllProjects(0);
		String values[] = new String[]{
	            "Completed Task one",
	            "Completed Task two",
	            "Completed Task three",
	            "Completed Task four"
	    };
		
        /** Setting the array adapter to the listview */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
    }
	
	@Override
    public void onStart() {
            super.onStart();
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String itemtext = l.getItemAtPosition(position).toString(); 
		
		Intent taskIntent = new Intent(getActivity(), TaskActivity.class);
		taskIntent.putExtra("taskName", itemtext);
		startActivity(taskIntent);
		
	}

	@Override
	public void onResume() {
		fillData();
		super.onResume();
	}
	
	
	
}