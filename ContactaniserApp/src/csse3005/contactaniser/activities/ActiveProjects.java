package csse3005.contactaniser.activities;

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
import csse3005.contactaniser.models.Project;
import csse3005.contactaniserapp.R;

public class ActiveProjects extends ListFragment {

	private ProjectDataSource projectdatasource;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		projectdatasource = new ProjectDataSource(getActivity());
        projectdatasource.open();
        
        View view = inflater.inflate(R.layout.project_list_active, null);
        return view;
	}
	
	
	public void fillData() {
		/** Creating array adapter to set data in listview */
        List<Project> values = projectdatasource.getAllProjects(0);  
        /** Setting the array adapter to the listview using the custom xml layout */
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(getActivity(),
                R.layout.active_row, R.id.label, values);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
    }
	
	@Override
    public void onStart() {
        super.onStart();
//        setEmptyText("No active projects");
		fillData();
    }

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

	@Override
	public void onResume() {
		super.onResume();
		fillData();
	}
	
	
	
	
	
	
	
}
