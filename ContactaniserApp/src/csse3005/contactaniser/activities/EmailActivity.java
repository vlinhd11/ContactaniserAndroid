package csse3005.contactaniser.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import csse3005.contactaniser.library.InternetCheck;
import csse3005.contactaniserapp.R;

public class EmailActivity extends Activity {
	
	String useremail;
	Button emailButton;
	EditText textTo;
	EditText textSubject;
	EditText textMessage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		useremail = getIntent().getExtras().getString("email");
			
			InternetCheck internet = new InternetCheck();
			boolean internetOn = internet.internetOn(this);
			if (internetOn) { 
			  Intent email = new Intent(Intent.ACTION_SEND);
			  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ useremail});
			  
			  //need this to prompts email client only
			  email.setType("message/rfc822");
			  startActivity(Intent.createChooser(email, "Choose an Email client :"));
			}
			else {
				new AlertDialog.Builder(this)
			    .setTitle(R.string.network_error)
			    .setMessage(R.string.network_error_message)
			    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
			     .show();
			}
	} 
}
