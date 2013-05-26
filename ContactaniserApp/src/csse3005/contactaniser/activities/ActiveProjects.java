package csse3005.contactaniser.activities;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import csse3005.contactaniser.datasource.ProjectDataSource;
import csse3005.contactaniser.models.Project;
import csse3005.contactaniserapp.R;
/**
 * Fragment Class to list all Active Project and put it to ListFragment
 */
public class ActiveProjects extends ListFragment {

	private ProjectDataSource projectdatasource;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		//Open database
		projectdatasource = new ProjectDataSource(getActivity());
        projectdatasource.open();
        
        View view = inflater.inflate(R.layout.project_list_active, null);
        return view;
	}
	
	
	/** populate Project List */
	public void fillData() {
		
        List<Project> values = projectdatasource.getAllProjects(0);  
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(getActivity(),
                R.layout.active_row, R.id.label, values);
        setListAdapter(adapter);
    }
	
	@Override
    public void onStart() {
        super.onStart();
		fillData();
    }

	/** Save the value of Project Name, Project Id, and User Id to next Activity */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String itemtext = l.getItemAtPosition(position).toString(); 
		Project itemId = (Project) getListAdapter().getItem(position);
		Intent pIntent = new Intent(getActivity(), ProjectActivity.class);
		pIntent.putExtra("projName", itemtext);
		pIntent.putExtra("projId", itemId.getProjectid());
		pIntent.putExtra("userid", getActivity().getIntent().getIntExtra("userID", 0));
		
		startActivity(pIntent);
		
	}

	/** Fill all data when back to the activity */
	@Override
	public void onResume() {
		super.onResume();
		fillData();
	}

	
}
