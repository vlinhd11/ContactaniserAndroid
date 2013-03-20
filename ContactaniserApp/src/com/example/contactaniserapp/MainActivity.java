package com.example.contactaniserapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		if (!credentials_stored) {
			 startActivity(new Intent(this, LoginActivity.class));
//		}
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		// populate project list/s here
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_change_password:
	        	openPasswordActivity();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void openPasswordActivity() {
		startActivity(new Intent(this, ChangePassword.class));
	}

}
