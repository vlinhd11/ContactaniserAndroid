package csse3005.contactaniser.activities;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import csse3005.contactaniser.datasource.ProjectDataSource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.Project;

public class ActiveProjects extends ListFragment {

	private ProjectDataSource projectdatasource;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		projectdatasource = new ProjectDataSource(getActivity());
        projectdatasource.open();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	private void fillData() {
		/** Creating array adapter to set data in listview */
        List<Project> values = projectdatasource.getAllProjects(0);  
        /** Setting the array adapter to the listview */
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(getActivity(),
                android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
    }
	
	@Override
    public void onStart() {
        super.onStart();
		fillData();
		genDummy();
    }

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String itemtext = l.getItemAtPosition(position).toString(); 
		Project itemId = (Project) getListAdapter().getItem(position);
		Intent pIntent = new Intent(getActivity(), ProjectActivity.class);
		pIntent.putExtra("projName", itemtext);
		pIntent.putExtra("projId", itemId.getProjectid());
		startActivity(pIntent);
		
	}

	@Override
	public void onResume() {
		super.onResume();
		fillData();
	}
	
	private void genDummy() {
		Calendar CalNow = Calendar.getInstance();
		Date DateNow = new Date(CalNow.getTimeInMillis());
		projectdatasource.createProject("Active Project 1", "Active Project 1", DateNow, DateNow, "0", DateNow);
		projectdatasource.createProject("Active Project 2", "Active Project 2", DateNow, DateNow, "0", DateNow);
		projectdatasource.createProject("Active Project 3", "Active Project 3", DateNow, DateNow, "0", DateNow);
		projectdatasource.createProject("Active Project 4", "Active Project 4", DateNow, DateNow, "0", DateNow);
	}
	
	
}
