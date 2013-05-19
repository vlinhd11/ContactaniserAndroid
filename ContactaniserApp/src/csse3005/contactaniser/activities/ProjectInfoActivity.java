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
import csse3005.contactaniser.models.User_Project;
import csse3005.contactaniser.models.User;
import csse3005.contactaniserapp.R;

public class ProjectInfoActivity extends Fragment {
	
	private static TextView projectName;
	private static TextView projectDescription;
	private static TextView projectStartDate;
	private static TextView projectDueDate;
	private static Button CompleteButton;
	
	long mRowId;
	
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
		
		
		// TODO Auto-generated method stub
		projectName = (TextView) getActivity().findViewById(R.id.textView2);
		projectDescription = (TextView) getActivity().findViewById(R.id.textView4);
		projectStartDate = (TextView) getActivity().findViewById(R.id.textView6);
		projectDueDate = (TextView) getActivity().findViewById(R.id.textView8);
		CompleteButton = (Button) getActivity().findViewById(R.id.buttonProjectComplete);
		
		mRowId = getActivity().getIntent().getExtras().getLong("projId");
		final String mRowIdString = String.valueOf(mRowId);
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        final Cursor project = DatabaseHelper.fetchProjectById(mRowId);
        ProjectName = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTNAME));
        ProjectDescription = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTDESCRIPTION));
        ProjectStartDate = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTSTARTDATE));
        
		
		Calendar CalNow = Calendar.getInstance();
    	final Date DateNow = new Date(CalNow.getTimeInMillis());
        
        
        projectName.setText(ProjectName);
        projectDescription.setText(ProjectDescription);
        projectStartDate.setText(ProjectStartDate);
        projectDueDate.setText(ProjectDueDate);
        
        listviewmember = (ListView) getActivity().findViewById(R.id.listMember);
        
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
        
        CompleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
				try {
					java.util.Date StartDateUtil;
					StartDateUtil = df.parse(ProjectStartDate);
					java.sql.Date StartDate = new java.sql.Date(StartDateUtil.getTime());
	                ProjectDueDate = project.getString(project.getColumnIndexOrThrow(MySQLHelper.COLUMN_PROJECTDUEDATE));
	                java.util.Date DueDateUtil =  df.parse(ProjectDueDate); 
	        		java.sql.Date DueDate = new java.sql.Date(DueDateUtil.getTime());
	                DatabaseHelper.createProject(mRowIdString, ProjectName, ProjectDescription, StartDate, DueDate, String.valueOf(1), DateNow);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
        		
            }
        });
        
        
        super.onStart();
	}
	
	private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setUserid(cursor.getInt(0));
		user.setUser_UserName(cursor.getString(1));
		user.setUserName(cursor.getString(2));
		user.setUserPhoneNumber(cursor.getInt(3));
		user.setUserEmail(cursor.getString(4));
		Date lu = Date.valueOf(cursor.getString(5));
		user.setUserLastUpdate(lu);
		
		return user;
	}
	
	

}
