package csse3005.contactaniser.activities;

import java.util.List;

import csse3005.contactaniser.datasource.ProjectDataSource;
import csse3005.contactaniser.models.Project;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CompletedProjects extends ListFragment{
	
	private ProjectDataSource projectdatasource;

    	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		

		/** Open the Project's Data Source */
		projectdatasource = new ProjectDataSource(getActivity());
        projectdatasource.open();
		
        /** Creating array adapter to set data in listview */
        List<Project> values = projectdatasource.getAllProjects(1);
		       
        /** Setting the array adapter to the listview */
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(getActivity(),
                android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
            
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
    public void onStart() {
            super.onStart();

            /** Setting the multiselect choice mode for the listview */
//            getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
//		String itemtext = getListView().getItemAtPosition(position).toString();
		String itemtext = l.getItemAtPosition(position).toString() + " | id: " + id; 
		
		new AlertDialog.Builder(getActivity())
	    .setMessage(itemtext)
	    .setPositiveButton("WOOO!", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        }
	     })
	     .show();
		
	}
	
}