package csse3005.contactaniser.activities;

import csse3005.contactaniserapp.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CreateTaskActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_task);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_create_task, menu);
		return true;
	}

}
