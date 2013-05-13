package csse3005.contactaniser.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import csse3005.contactaniser.datasource.ProjectDataSource;
import csse3005.contactaniser.datasource.User_ProjectDataSource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User_Project;
import csse3005.contactaniserapp.R;

public class ProjectInfoActivity extends Fragment {
	
	private static TextView projectName;
	private static TextView projectDescription;
	private static TextView projectStartDate;
	private static TextView projectDueDate;
	
	long mRowId;
	String ProjectName;
	String ProjectDescription;
	String ProjectStartDate;
	String ProjectDueDate;
	private ProjectDataSource DatabaseHelper;
	private User_ProjectDataSource userprojectdatasource;
	private ListView listviewmember;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			   Bundle savedInstanceState) {
		 
		  View InfoFragmentView = inflater.inflate(R.layout.activity_project_info, container, false);
		
		return InfoFragmentView;
	}
	
	@Override
	public void onStart() {
		
		DatabaseHelper = new ProjectDataSource(getActivity());
		DatabaseHelper.open();
		
		userprojectdatasource = new User_ProjectDataSource(getActivity());
		userprojectdatasource.open();
		
		
		// TODO Auto-generated method stub
		projectName = (TextView) getActivity().findViewById(R.id.textView2);
		projectDescription = (TextView) getActivity().findViewById(R.id.textView4);
		projectStartDate = (TextView) getActivity().findViewById(R.id.textView6);
		projectDueDate = (TextView) getActivity().findViewById(R.id.textView8);
		
		mRowId = getActivity().getIntent().getExtras().getLong("projId");
		
        Cursor project = DatabaseHelper.fetchProjectById(mRowId);
        ProjectName = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTNAME));
        ProjectDescription = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTDESCRIPTION));
        ProjectStartDate = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTSTARTDATE));
        ProjectDueDate = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTDUEDATE));
        
        
        projectName.setText(ProjectName);
        projectDescription.setText(ProjectDescription);
        projectStartDate.setText(ProjectStartDate);
        projectDueDate.setText(ProjectDueDate);
        
        listviewmember = (ListView) getActivity().findViewById(R.id.listMember);
        
        ArrayList<User_Project> values = userprojectdatasource.getAllUserbyProjectId(mRowId);
      
        final ArrayAdapter<User_Project> adapter = new ArrayAdapter<User_Project>(getActivity(),
                R.layout.active_row, R.id.label, values);
        listviewmember.setAdapter(adapter);
        
        listviewmember.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent pIntent = new Intent(getActivity(), MemberInfoActivity.class);
				pIntent.putExtra("projectid", adapter.getItem(position).getUPPid());
				pIntent.putExtra("userid", adapter.getItem(position).getUPUid());
				startActivity(pIntent);
			}
		});
        super.onStart();
	}
	
	

}
