package csse3005.contactaniser.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import csse3005.contactaniser.library.InternetCheck;

public class EmailActivity extends Activity {
	
	private String useremail;
	private String msg;
	
	// UI references.
	Button emailButton;
	EditText textTo;
	EditText textSubject;
	EditText textMessage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		// retrieve user email and email message from received intent
		useremail = getIntent().getExtras().getString("email");
		msg = getIntent().getExtras().getString("message");
		
		// check for available Internet connection
		InternetCheck internet = new InternetCheck();
		boolean internetOn = internet.internetOn(this);
		if (internetOn) {
			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[]{ useremail});
			email.putExtra(Intent.EXTRA_SUBJECT, "Protivity");
			email.putExtra(Intent.EXTRA_TEXT, msg);
			//need this to prompts email client
			email.setType("message/rfc822");
			startActivity(Intent.createChooser(email, "Choose an Email client :"));
			finish();
		} else {
			// display dialog to inform user about network error
			internet.NetworkError(this);
		}
	} 
}