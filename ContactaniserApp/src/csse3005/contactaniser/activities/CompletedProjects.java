package csse3005.contactaniser.activities;

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
	
    // TODO: replace this dummy array with info from database
    String projectList[] = new String[]{
            "Finished Project one",
            "Finished Project two",
            "Finished Project three",
            "Finished Project four",
    };

    	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		/** Creating array adapter to set data in listview */
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_multiple_choice, android_versions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, projectList);

        /** Setting the array adapter to the listview */
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