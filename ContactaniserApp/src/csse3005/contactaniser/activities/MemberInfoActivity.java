package csse3005.contactaniser.activities;

import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_ProjectDataSource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniserapp.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MemberInfoActivity extends Activity {
	
	private static TextView userName;
	private static TextView userRole;
	private static TextView userPhone;
	private static TextView userEmail;
	String phonenumber;
	String phoneemail;
	private UserDataSource userdatasource;
	private User_ProjectDataSource userprojectdatasource;
	long ProjectId;
	long UserId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_info);
		
		userdatasource = new UserDataSource(this);
		userdatasource.open();
		
		userprojectdatasource = new User_ProjectDataSource(this);
		userprojectdatasource.open();
		
		userName = (TextView) findViewById(R.id.nametextView);
		userRole = (TextView) findViewById(R.id.roletextView);
		userPhone = (TextView) findViewById(R.id.phonetextView);
		userEmail = (TextView) findViewById(R.id.emailtextView);
		
		ProjectId = getIntent().getExtras().getLong("projectid");
		UserId = getIntent().getExtras().getLong("userid");
		
		Cursor user = userdatasource.fetchUserById(UserId);
		Cursor role = userprojectdatasource.fetchUserProjectById(UserId, ProjectId);
		
		phonenumber = user.getString(user.getColumnIndexOrThrow(MySQLHelper.COLUMN_USERPHONENUMBER));
		phoneemail = user.getString(user.getColumnIndexOrThrow(MySQLHelper.COLUMN_USEREMAIL));
		
		userName.setText(user.getString(user.getColumnIndexOrThrow(MySQLHelper.COLUMN_USERNAME)));
		userRole.setText(role.getString(role.getColumnIndexOrThrow(MySQLHelper.COLUMN_ROLE)));
		
		userPhone.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View arg) {
			Intent pIntent = new Intent(getApplicationContext(),PhoneActivity.class);
			pIntent.putExtra("userphone",phonenumber);
			startActivity(pIntent);

			}
		});
		
		userEmail.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View arg) {
			Intent pIntent = new Intent(getApplicationContext(),EmailActivity.class);
			pIntent.putExtra("useremail",phoneemail);
			startActivity(pIntent);
				
				
			}
		});
		
	}
	
	
	
}
