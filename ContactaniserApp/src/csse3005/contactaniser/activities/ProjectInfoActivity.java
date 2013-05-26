package csse3005.contactaniser.activities;

import java.sql.Date;
import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import csse3005.contactaniser.datasource.ProjectDataSource;
import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_ProjectDataSource;
import csse3005.contactaniser.library.MeasureHeight;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User;
import csse3005.contactaniser.models.User_Project;
import csse3005.contactaniserapp.R;

/**
 * Fragment to display Project Information
 */

public class ProjectInfoActivity extends Fragment {

	private static TextView projectName;
	private static TextView projectDescription;
	private static TextView projectStartDate;
	private static TextView projectDueDate;


	long mRowId;
	long mrowuserid;
	String ProjectName;
	String ProjectDescription;
	String ProjectStartDate;
	String ProjectDueDate;
	private ProjectDataSource DatabaseHelper;
	private User_ProjectDataSource userprojectdatasource;
	private UserDataSource userdatasource;
	private ListView listviewmember;
	ArrayList<User> usertest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			   Bundle savedInstanceState) {

		  View InfoFragmentView = inflater.inflate(R.layout.activity_project_info, container, false);

		return InfoFragmentView;
	}

	@Override
	public void onStart() {

		//Open database
		DatabaseHelper = new ProjectDataSource(getActivity());
		DatabaseHelper.open();

		userprojectdatasource = new User_ProjectDataSource(getActivity());
		userprojectdatasource.open();

		userdatasource = new UserDataSource(getActivity());
		userdatasource.open();

		//Get the widget value
		projectName = (TextView) getActivity().findViewById(R.id.textViewProjectName);
		projectDescription = (TextView) getActivity().findViewById(R.id.textViewProjectBrief);
		projectStartDate = (TextView) getActivity().findViewById(R.id.textViewProjectDateCreated);
		projectDueDate = (TextView) getActivity().findViewById(R.id.textViewProjectDueDate);

		//Get the value from previous activity
		mRowId = getActivity().getIntent().getExtras().getLong("projId");
		mrowuserid = getActivity().getIntent().getIntExtra("userid", 0);

		//Get the cursor of current project that opened
        Cursor project = DatabaseHelper.fetchProjectById(mRowId);
        ProjectName = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTNAME));
        ProjectDescription = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTDESCRIPTION));
        ProjectStartDate = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTSTARTDATE));
        ProjectDueDate = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTDUEDATE));
        
        //Set the value
        projectName.setText(ProjectName);
        projectDescription.setText(ProjectDescription);
        projectStartDate.setText(ProjectStartDate);
        projectDueDate.setText(ProjectDueDate);
        
        listviewmember = (ListView) getActivity().findViewById(R.id.listMemberProject);
        
        //Conver User_Project value to User
        ArrayList<User_Project> values = userprojectdatasource.getAllUserbyProjectId(mRowId);
        ArrayList<User> userlist = new ArrayList<User>();
        for (int i=0; i<values.size(); i++){
        	
        	User_Project  userproject = values.get(i);
            long userid = userproject.getUPUid();
   

            Cursor c = userdatasource.fetchUserById(userid);

            c.moveToFirst();
			while (!c.isAfterLast()) {
				User user = cursorToUser(c);
				userlist.add(user);
			    c.moveToNext();
			}
			// Make sure to close the cursor
			c.close();
            
            
         }
        
        //Set the User value in adapter
        final ArrayAdapter<User> adapter = new ArrayAdapter<User>(getActivity(),
                R.layout.member_row, R.id.memberLable, userlist);

        listviewmember.setAdapter(adapter);
        MeasureHeight.setListViewHeightBasedOnChildren(listviewmember);
        
        listviewmember.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent pIntent = new Intent(getActivity(), MemberInfoActivity.class);
				pIntent.putExtra("projectid", mRowId);
				pIntent.putExtra("userid", adapter.getItem(position).getUserid());
				startActivity(pIntent);
			}
		});
        
        
        super.onStart();
	}
	/** Cursor to pass User value
	 * @param cursor of User Database
	 * 
	 * @return User Object
	 *  
	 */
	private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setUserid(cursor.getInt(0));
		user.setUser_UserName(cursor.getString(1));
		user.setUserName(cursor.getString(2));
		user.setUserPhoneNumber(cursor.getString(3));
		user.setUserEmail(cursor.getString(4));
		Date lu = Date.valueOf(cursor.getString(5));
		user.setUserLastUpdate(lu);

		return user;
	}



}