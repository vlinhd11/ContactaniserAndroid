package csse3005.contactaniser.activities;

import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_ProjectDataSource;
import csse3005.contactaniser.datasource.User_TaskDataSource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User;
import csse3005.contactaniser.models.User_Project;
import csse3005.contactaniserapp.R;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateTaskActivity extends Activity {

	private long projectid;
	private int userid;
	private int year;
	private int month;
	private int day;
	private long taskid;
	private String tasknamestring;
	private String taskdescriptionstring;
	
	private Button btnChangeDate;	
	private View focusView;
	private EditText taskname;
	private EditText taskdescription;
	private Spinner taskcategory;
	private int taskcategoryindex;
	private SeekBar taskimportance;
	private int taskimportanceindex;
	private Button taskcreatebutton;	
	
	private TaskDataSource taskdatabase;
	private String datestring;
	private Button p1_button;
	private int duedateday;
	private int duedatemonth;
	private int duedateyear;
	static final int DATE_DIALOG_ID = 999;
	
	private User_ProjectDataSource userprojectdatasource;
	private UserDataSource userdatasource;
	private User_TaskDataSource usertaskdatasource;
	private ListView listviewmembercreate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_task);
		
		setCurrentDateOnView();
		addListenerOnButton();
		
		taskdatabase = new TaskDataSource(this);
		taskdatabase.open();
		
		userdatasource = new UserDataSource(this);
		userdatasource.open();
		
		userprojectdatasource = new User_ProjectDataSource(this);
		userprojectdatasource.open();
		
		usertaskdatasource = new User_TaskDataSource(this);
		usertaskdatasource.open();
		
		projectid = getIntent().getExtras().getLong("projectid");
		//Log.i("taskid", String.valueOf(taskid));
		listviewmembercreate = (ListView) findViewById(R.id.listMemberCreate);
		taskname = (EditText) findViewById(R.id.task_name);
		taskdescription = (EditText) findViewById(R.id.task_description);
		taskcategory = (Spinner) findViewById(R.id.spinner);
		taskimportance = (SeekBar) findViewById(R.id.seekbar);
		taskcreatebutton = (Button) findViewById(R.id.create_task_button);
		p1_button = (Button)findViewById(R.id.btnChangeDate);
		
		ArrayList<User_Project> values = userprojectdatasource.getAllUserbyProjectId(projectid);
        ArrayList<User> userlistcreate = new ArrayList<User>();
        for (int i=0; i<values.size(); i++){
        	
        	User_Project  userproject = values.get(i);
            long userid = userproject.getUPUid();
            Cursor c = userdatasource.fetchUserById(userid);
            
            c.moveToFirst();
			while (!c.isAfterLast()) {
				User user = cursorToUser(c);
				userlistcreate.add(user);
			    c.moveToNext();
			}
			// Make sure to close the cursor
			c.close();
            
         }


        
        final MyCustomAdapter adapter = new MyCustomAdapter(this,
                R.layout.member_checkbox,  userlistcreate);
        
        int height = userlistcreate.size();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(230, (40*height));
        layoutParams.gravity=Gravity.CENTER;
        listviewmembercreate.setLayoutParams(layoutParams);
        listviewmembercreate.setAdapter(adapter);
        
		
		taskcreatebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            
            	setResult(RESULT_OK);
            	
            	if (taskname.getText().toString().equals("")) {
        			taskname.setError(getString(R.string.error_field_required));
        			focusView = taskname;
        			focusView.requestFocus();
        			 
        		} 
            	
            	else {
        			
        			//if the name is not null wh, get the values from the
        			//layout attributes and saved values from existing edit
            		taskid = System.currentTimeMillis();
            		String taskidstring = String.valueOf(taskid);
            		
            		projectid = getIntent().getExtras().getLong("projectid");
            		userid = getIntent().getExtras().getInt("userid");
            		//Log.i("useridmasukpascreate", String.valueOf(userid));
            		tasknamestring = taskname.getText().toString();
            		taskdescriptionstring = 
            				taskdescription.getText().toString();
            		taskcategoryindex = 
            				taskcategory.getSelectedItemPosition();
            		taskimportanceindex = taskimportance.getProgress();
            		
            		Calendar cal = Calendar.getInstance();
            		int calnow = (int) cal.getTimeInMillis();
            		Date datenow = new Date(calnow);
            		
            		//Get Date set
            		datestring = p1_button.getText().toString();
            		
            		String[] daymonthyear = datestring.split("/");
            		duedateday = Integer.parseInt(daymonthyear[0]);
            		duedatemonth = Integer.parseInt(daymonthyear[1]);
            		duedateyear = Integer.parseInt(daymonthyear[2]);
            		Calendar c1 = Calendar.getInstance();
            		c1.set(duedateyear, duedatemonth, duedateday);
            		long duedateint = c1.getTimeInMillis();
            		Date dateSet = new Date(duedateint);
            	
            		
            		ArrayList<User> userLists = adapter.userList;
            	    for(int i=0;i<userLists.size();i++){
            	     User user = userLists.get(i);
            	     if(user.isSelected()){
            	    	 long usertaskid = System.currentTimeMillis();
            	    	 String usertaskidstring = String.valueOf(usertaskid);
            	    	 usertaskdatasource.createUser_Task(usertaskidstring, user.getUserid(), taskid, datenow);
            	    	 
            	      
            	     }
            	    }
            		
            	    long usertaskidself = System.currentTimeMillis();
            	    String usertaskidselfstring = String.valueOf(usertaskidself);
            	    usertaskdatasource.createUser_Task(usertaskidselfstring,userid , taskid, datenow);
            		taskdatabase.createTask(taskidstring, projectid,
            				tasknamestring, taskdescriptionstring,
            				taskimportanceindex, dateSet, 0, datenow,
            				taskcategoryindex);
            		
            		taskdatabase.close();
            		finish();
            	}
            }

        });
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_create_task, menu);
		return true;
	}
	
	// display current date
		public void setCurrentDateOnView() {
			
			final Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
			p1_button = (Button)findViewById(R.id.btnChangeDate);
			p1_button.setText(Integer.toString(day)
					+ "/" + (Integer.toString(month+1)) + "/" + 
					Integer.toString(year));
	 
		}
	 
		public void addListenerOnButton() {
	 
			btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
	 
			btnChangeDate.setOnClickListener(new OnClickListener() {
	 
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
	 
					showDialog(DATE_DIALOG_ID);
	 
				}
	 
			});
	 
		}
	 
		@Override
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case DATE_DIALOG_ID:
			   // set date picker as current date
			   return new DatePickerDialog(this, datePickerListener, 
	                         year, month,day);
			}
			return null;
		}
	 
		private DatePickerDialog.OnDateSetListener datePickerListener 
	                = new DatePickerDialog.OnDateSetListener() {
	 
			// when dialog box is closed, below method will be called.
			public void onDateSet(DatePicker view, int selectedYear,
					int selectedMonth, int selectedDay) {
				year = selectedYear;
				month = selectedMonth;
				day = selectedDay;
				
				p1_button = (Button)findViewById(R.id.btnChangeDate);
				p1_button.setText(Integer.toString(day)
						+ "/" + (Integer.toString(month+1)) + "/" + 
						Integer.toString(year));
	 
			}
		};
		
		private User cursorToUser(Cursor cursor) {
			User user = new User();
			user.setUserid(cursor.getInt(0));
			user.setUser_UserName(cursor.getString(1));
			user.setUserName(cursor.getString(2));
			user.setUserPhoneNumber(cursor.getInt(3));
			user.setUserEmail(cursor.getString(4));
			Date lu = Date.valueOf(cursor.getString(5));
			user.setUserLastUpdate(lu);
			user.setSelected(false);
			
			return user;
		}
		
		private class MyCustomAdapter extends ArrayAdapter<User> {
			 
			  private ArrayList<User> userList;
			 
			  public MyCustomAdapter(Context context, int textViewResourceId, 
			    ArrayList<User> countryList) {
			   super(context, textViewResourceId, countryList);
			   this.userList = new ArrayList<User>();
			   this.userList.addAll(countryList);
			  }
			 
			  private class ViewHolder {
			  
			   CheckBox membercheck;
			  }
			 
			  @Override
			  public View getView(int position, View convertView, ViewGroup parent) {
			 
			   ViewHolder holder = null;
			   Log.v("ConvertView", String.valueOf(position));
			 
			   if (convertView == null) {
			   LayoutInflater vi = (LayoutInflater)getSystemService(
			     Context.LAYOUT_INFLATER_SERVICE);
			   convertView = vi.inflate(R.layout.member_checkbox, null);
			 
			   holder = new ViewHolder();
			   holder.membercheck = (CheckBox) convertView.findViewById(R.id.memberlistadd);
			   convertView.setTag(holder);
			   holder.membercheck.setOnClickListener( new View.OnClickListener() {  
				     public void onClick(View v) {  
				      CheckBox cb = (CheckBox) v ;  
				      User user = (User) cb.getTag();
				      user.setSelected(cb.isChecked());
				     }  
				    });  
				   }
			    
			    
			   else {
			    holder = (ViewHolder) convertView.getTag();
			   }
			 
			   User users = userList.get(position);
			   holder.membercheck.setText(users.getUserName());
			   holder.membercheck.setChecked(users.isSelected());
			   holder.membercheck.setTag(users);
			 
			   return convertView;
			 
			  }
			 
			 }
		
		

}
