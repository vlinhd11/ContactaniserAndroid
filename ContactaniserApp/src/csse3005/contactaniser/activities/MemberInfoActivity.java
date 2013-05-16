package csse3005.contactaniser.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_ProjectDataSource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniserapp.R;

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
	
	ImageButton emailButton;
	ImageButton smsButton;
	ImageButton callButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_info);
		addListenerOnButton();
		
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
		
//		userPhone.setOnClickListener(new OnClickListener(){
//		@Override
//		public void onClick(View arg) {
//			Intent pIntent = new Intent(getApplicationContext(),PhoneActivity.class);
//			pIntent.putExtra("userphone",phonenumber);
//			startActivity(pIntent);
//
//			}
//		});
		
//		userEmail.setOnClickListener(new OnClickListener(){
//		@Override
//		public void onClick(View arg) {
//			Intent pIntent = new Intent(getApplicationContext(),EmailActivity.class);
//			pIntent.putExtra("useremail",phoneemail);
//			startActivity(pIntent);
//				
//				
//			}
//		});
		
	}
	
	public void addListenerOnButton() {
		 
		emailButton = (ImageButton) findViewById(R.id.emailButton);
		smsButton = (ImageButton) findViewById(R.id.smsButton);
		callButton = (ImageButton) findViewById(R.id.callButton);
 
		emailButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				ContinueToEmail();
			}
		});
		
		smsButton.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				ContinueToSms();
			}
		});
		
		callButton.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				PhoneCall();
			}
		});
 
	}
	
	private void PhoneCall() {
		Intent i = new Intent(this, CallActivity.class);
		i.putExtra("phonenumber",phonenumber);
		startActivity(i);
	}
	
	private void ContinueToEmail() {
		Intent i = new Intent(this, EmailActivity.class);
		i.putExtra("email", phoneemail);
		startActivity(i);
	}
	
	private void ContinueToSms() {
		Intent i = new Intent(this, PhoneActivity.class);
		i.putExtra("phonenumber",phonenumber);
		startActivity(i);
	}
}
