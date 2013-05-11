package csse3005.contactaniser.activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import csse3005.contactaniser.datasource.TaskDataSource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniserapp.R;

public class TaskActivity extends Activity {

	private TaskDataSource taskdatasource;
	String taskname;
	String taskdescription;
	int taskcategory;
	int taskimportance;
	String taskduedate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		taskdatasource = new TaskDataSource(this);
		taskdatasource.open();
		
		long mrwoid = getIntent().getExtras().getLong("taskid");
		Cursor task = taskdatasource.fetchTaskById(mrwoid);
		
		
		// Create text view
		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setText("Name: " + getIntent().getStringExtra("taskName") + "\nDescription: " + 
		task.getString(task.getColumnIndexOrThrow(MySQLHelper.COLUMN_TASKDESCRIPTION)) + "\nCategory :" +
				task.getInt(task.getColumnIndexOrThrow(MySQLHelper.COLUMN_TASKCATEGORY)) + "\nImportance :" +
		task.getInt(task.getColumnIndexOrThrow(MySQLHelper.COLUMN_TASKIMPORTANCELEVEL)) + "\nDue Date :" +
				task.getString(task.getColumnIndexOrThrow(MySQLHelper.COLUMN_TASKDUEDATE)));
		
		// Set text view as the activity layout
		setContentView(textView);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task, menu);
		return true;
	}

}
