package csse3005.contactaniser.connection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import csse3005.contactaniserapp.R;

public class Sync extends Activity {
	
	String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync);
		
		Intent intent = getIntent();
		username = intent.getExtras().getString("username");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sync, menu);
		return true;
	}
	
	public void downSync(View view) {
		Intent i = new Intent(this, DownSync.class);
		i.putExtra("username", username);
		startActivity(i);
	}
	
	public void upSync(View view) {
		
	}
	
	public void addSamples(View view) {
		
	}
}
