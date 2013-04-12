package csse3005.contactaniser.activities;

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
	
    // TODO: replace this dummy array with info from database
    String projectList[] = new String[]{
            "Active Project one",
            "Active Project two",
            "Active Project three",
            "Active Project four",
            "Active Project five"
    };

    	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		/** Creating array adapter to set data in listview */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, projectList);

        /** Setting the array adapter to the listview */
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
