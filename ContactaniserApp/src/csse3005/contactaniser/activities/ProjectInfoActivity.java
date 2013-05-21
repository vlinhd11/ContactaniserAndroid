package csse3005.contactaniser.activities;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import csse3005.contactaniser.datasource.ProjectDataSource;
import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_ProjectDataSource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.Project;
import csse3005.contactaniser.models.User_Project;
import csse3005.contactaniser.models.User;
import csse3005.contactaniserapp.R;

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

		DatabaseHelper = new ProjectDataSource(getActivity());
		DatabaseHelper.open();

		userprojectdatasource = new User_ProjectDataSource(getActivity());
		userprojectdatasource.open();

		userdatasource = new UserDataSource(getActivity());
		userdatasource.open();

		projectName = (TextView) getActivity().findViewById(R.id.textViewProjectName);
		projectDescription = (TextView) getActivity().findViewById(R.id.textViewProjectBrief);
		projectStartDate = (TextView) getActivity().findViewById(R.id.textViewProjectDateCreated);
		projectDueDate = (TextView) getActivity().findViewById(R.id.textViewProjectDueDate);


		mRowId = getActivity().getIntent().getExtras().getLong("projId");
		mrowuserid = getActivity().getIntent().getIntExtra("userid", 0);


        Cursor project = DatabaseHelper.fetchProjectById(mRowId);
        ProjectName = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTNAME));
        ProjectDescription = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTDESCRIPTION));
        ProjectStartDate = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTSTARTDATE));
        ProjectDueDate = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTDUEDATE));
        
        projectName.setText(ProjectName);
        projectDescription.setText(ProjectDescription);
        projectStartDate.setText(ProjectStartDate);
        projectDueDate.setText(ProjectDueDate);
        
        listviewmember = (ListView) getActivity().findViewById(R.id.listMemberProject);
        
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
        
        final ArrayAdapter<User> adapter = new ArrayAdapter<User>(getActivity(),
                R.layout.member_row, R.id.memberLable, userlist);
        
        //final ArrayAdapter<User_Project> adapter = new ArrayAdapter<User_Project>(getActivity(),
          //      R.layout.member_row, R.id.memberLable, values);
        listviewmember.setAdapter(adapter);
        
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