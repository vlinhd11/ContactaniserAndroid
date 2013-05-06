package csse3005.contactaniser.activities;

import csse3005.contactaniserapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class TaskList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);
		
		// Create text view
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText("Selected: " + getIntent().getStringExtra("projName"));
		
		// Set text view as the activity layout
		setContentView(textView);
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_list, menu);
		return true;
	}

}
