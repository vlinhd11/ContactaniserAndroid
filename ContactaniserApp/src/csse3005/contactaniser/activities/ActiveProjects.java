package csse3005.contactaniser.activities;

import java.util.List;

import csse3005.contactaniser.datasource.ProjectDataSource;
import csse3005.contactaniser.models.Project;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActiveProjects extends ListFragment {
	
//	boolean ProjectActive;
//	
//	public ActiveProjects(boolean active) {
//		ProjectActive = active;
//	}

	private ProjectDataSource projectdatasource;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		projectdatasource = new ProjectDataSource(getActivity());
        projectdatasource.open();
		
        List<Project> values = projectdatasource.getAllProjects(0);
		/** Creating array adapter to set data in listview */
//       
        /** Setting the array adapter to the listview */
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(getActivity(),
                android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);


		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
    public void onStart() {
            super.onStart();
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		String itemtext = l.getItemAtPosition(position).toString(); 
		
		Intent pIntent = new Intent(getActivity(), TaskList.class);
		pIntent.putExtra("projName", itemtext);
		startActivity(pIntent);
		
	}
	
}
