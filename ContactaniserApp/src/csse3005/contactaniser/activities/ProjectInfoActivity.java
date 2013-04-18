package csse3005.contactaniser.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import csse3005.contactaniserapp.R;

public class ProjectInfoActivity extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			   Bundle savedInstanceState) {
		 
		  View InfoFragmentView = inflater.inflate(R.layout.activity_project_info, container, false);
		
		return InfoFragmentView;
	}

}
