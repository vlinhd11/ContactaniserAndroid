package csse3005.contactaniser.activities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniser.datasource.UserDataSource;
import csse3005.contactaniser.datasource.User_ProjectDataSource;
import csse3005.contactaniser.datasource.User_TaskDataSource;
import csse3005.contactaniser.library.MeasureHeight;
import csse3005.contactaniser.models.User;
import csse3005.contactaniser.models.User_Project;
import csse3005.contactaniserapp.R;

/**
 * Activity to create new Task
 */

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
		
		
		//Open all database source
		taskdatabase = new TaskDataSource(this);
		taskdatabase.open();
		
		userdatasource = new UserDataSource(this);
		userdatasource.open();
		
		userprojectdatasource = new User_ProjectDataSource(this);
		userprojectdatasource.open();
		
		usertaskdatasource = new User_TaskDataSource(this);
		usertaskdatasource.open();
		
		// Get all the layout value
		projectid = getIntent().getExtras().getLong("projectid");
		listviewmembercreate = (ListView) findViewById(R.id.listMemberCreate);
		taskname = (EditText) findViewById(R.id.task_name);
		taskdescription = (EditText) findViewById(R.id.task_description);
		taskcategory = (Spinner) findViewById(R.id.spinner);
		taskimportance = (SeekBar) findViewById(R.id.seekbar);
		taskcreatebutton = (Button) findViewById(R.id.create_task_button);
		p1_button = (Button)findViewById(R.id.btnChangeDate);
		
		//Convert the User_Project value to User value by UserId from User_Project
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


        // Set the User list to MyCustomAdapter
        final MyCustomAdapter adapter = new MyCustomAdapter(this,
                R.layout.member_checkbox,  userlistcreate);
        
        listviewmembercreate.setAdapter(adapter);
        MeasureHeight.setListViewHeightBasedOnChildren(listviewmembercreate);
        
		taskcreatebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            
            	setResult(RESULT_OK);
            	
            	//Get DueDate Instance
        		datestring = p1_button.getText().toString();
        		String[] daymonthyear = datestring.split("/");
        		duedateday = Integer.parseInt(daymonthyear[0]);
        		duedatemonth = Integer.parseInt(daymonthyear[1]) - 1;
        		duedateyear = Integer.parseInt(daymonthyear[2]);
        		Calendar c1 = Calendar.getInstance();
        		c1.set(duedateyear, duedatemonth, duedateday);
        		long duedateint = c1.getTimeInMillis();
        		Date dateSet = new Date(duedateint);
        		
        		//Get Current Date
        		Calendar cal = Calendar.getInstance();
        		Date datenow = new Date(cal.getTimeInMillis());
            	
        		//Call error if TaskName TextView is Empty
            	if (taskname.getText().toString().equals("")) {
        			taskname.setError(getString(R.string.error_field_required));
        			focusView = taskname;
        			focusView.requestFocus();
        			 
        		} 
            	
            	//Call error toast if DueDate is before current date
            	if(dateSet.before(datenow))
            	{
            		Toast.makeText(CreateTaskActivity.this, "Due Date Invalid", Toast.LENGTH_LONG).show();
            	}
            		
            	
            	else {
        			
            		//Set Task ID from time current time in millis
            		taskid = System.currentTimeMillis();
            		String taskidstring = String.valueOf(taskid);
            		
            		//Get value from previous activity
            		projectid = getIntent().getExtras().getLong("projectid");
            		userid = getIntent().getExtras().getInt("userid");
            		
            		//Get all value from the Android widget
            		tasknamestring = taskname.getText().toString();
            		taskdescriptionstring = 
            				taskdescription.getText().toString();
            		taskcategoryindex = 
            				taskcategory.getSelectedItemPosition();
            		taskimportanceindex = taskimportance.getProgress();
            		
            		//Get user value from adapter
            		ArrayList<User> userLists = adapter.userList;
            	    for(int i=0;i<userLists.size();i++){
            	     User user = userLists.get(i);
            	     
            	     //Save User Task if the User in ListView is selected
            	     if(user.isSelected()){
            	    	 long usertaskid = System.currentTimeMillis();
            	    	 String usertaskidstring = String.valueOf(usertaskid);
            	    	 usertaskdatasource.createUser_Task(usertaskidstring, user.getUserid(), taskid, datenow, 0);

            	     }
            	     
            	     else
            	     {
            	    	 long usertaskid = System.currentTimeMillis();
            	    	 String usertaskidstring = String.valueOf(usertaskid);
            	    	 usertaskdatasource.createUser_Task(usertaskidstring, user.getUserid(), taskid, datenow, 1);
            	     }
            	    }
            		
            	    //Get another time in millis for the user who login to save in User Task
            	    long usertaskidself = System.currentTimeMillis();
            	    String usertaskidselfstring = String.valueOf(usertaskidself);
            	    usertaskdatasource.createUser_Task(usertaskidselfstring,userid , taskid, datenow, 0);
            		
            	    //Save task value to database
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
	
		/** display current date */
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
		
		/** Add new Button Listener */
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
			user.setSelected(false);
			
			return user;
		}
		
		/**
		 * Class to customize current ArrayAdapter to have CheckBox
		 */
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
		
		


