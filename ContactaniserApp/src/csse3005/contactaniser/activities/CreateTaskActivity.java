package csse3005.contactaniser.activities;

import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniserapp.R;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import java.sql.Date;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateTaskActivity extends Activity {


	private Button btnChangeDate;
	
	
	private long projectid;
	private int year;
	private int month;
	private int day;
	private long taskid;
	private String tasknamestring;
	private String taskdescriptionstring;
	private EditText taskname;
	private EditText taskdescription;
	private Spinner taskcategory;
	private int taskcategoryindex;
	private SeekBar taskimportance;
	private int taskimportanceindex;
	private Button taskcreatebutton;
	
	private TaskDataSource taskdatabase;
	
	
 
	static final int DATE_DIALOG_ID = 999;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_task);
		
		setCurrentDateOnView();
		addListenerOnButton();
		
		taskdatabase = new TaskDataSource(this);
		taskdatabase.open();
		
		
		//Log.i("taskid", String.valueOf(taskid));
		
		taskname = (EditText) findViewById(R.id.task_name);
		taskdescription = (EditText) findViewById(R.id.task_description);
		taskcategory = (Spinner) findViewById(R.id.spinner);
		taskimportance = (SeekBar) findViewById(R.id.seekbar);
		taskcreatebutton = (Button) findViewById(R.id.create_task_button);
		
		taskcreatebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            
            	setResult(RESULT_OK);
            	
            	if (taskname.getText().toString().equals(""))
        		{
        			 Toast.makeText(CreateTaskActivity.this, 
                     "You must fill the caption", Toast.LENGTH_LONG).show();
        			 
        		}//if the caption is not null when the button click, get the values from the layout attributes and saved values from existing edit
            	else
            	{
            		
            		taskid = System.currentTimeMillis();
            		String taskidstring = String.valueOf(taskid);
            		Log.i("taskid", taskidstring);
            		projectid = getIntent().getExtras().getLong("projectid");

            		tasknamestring = taskname.getText().toString();
            		taskdescriptionstring = taskdescription.getText().toString();
            		taskcategoryindex = taskcategory.getSelectedItemPosition();
            		taskimportanceindex = taskimportance.getProgress();
            		
            		Calendar cal = Calendar.getInstance();
            		int calnow = (int) cal.getTimeInMillis();
            		Date datenow = new Date(calnow);
            		
            		//Get Date set
            		Calendar c1 = Calendar.getInstance();
            		c1.set(year, month, day);
            		int calDateSet = (int) cal.getTimeInMillis();
            		Date dateSet = new Date(calDateSet);
            		
            		taskdatabase.createTask(taskidstring, projectid, tasknamestring, taskdescriptionstring, taskimportanceindex, dateSet, 0, datenow, taskcategoryindex);
            		
            		taskdatabase.close();
            		finish();
            	}
            	
        		
                
            }

        });
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_create_task, menu);
		return true;
	}
	
	// display current date
		public void setCurrentDateOnView() {
			
			final Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
			Button p1_button = (Button)findViewById(R.id.btnChangeDate);
			p1_button.setText(Integer.toString(day)
					+ "/" + (Integer.toString(month+1)) + "/" + 
					Integer.toString(year));
	 
		}
	 
		public void addListenerOnButton() {
	 
			btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
	 
			btnChangeDate.setOnClickListener(new OnClickListener() {
	 
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
	 
			}
		};

}
