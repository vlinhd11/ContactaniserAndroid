package csse3005.contactaniser.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import csse3005.contactaniserapp.R;

public class EmailActivity extends Activity {
	
	Button emailButton;
	EditText textTo;
	EditText textSubject;
	EditText textMessage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
 
		emailButton = (Button) findViewById(R.id.buttonEmail);
		textTo = (EditText) findViewById(R.id.editTextTo);
		textSubject = (EditText) findViewById(R.id.editTextSubject);
		textMessage = (EditText) findViewById(R.id.editTextMessage);
 
		emailButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
			
			if (internetOn()) {
			  String to = textTo.getText().toString();
			  String subject = textSubject.getText().toString();
			  String message = textMessage.getText().toString();
 
			  Intent email = new Intent(Intent.ACTION_SEND);
			  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
			  //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
			  //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
			  email.putExtra(Intent.EXTRA_SUBJECT, subject);
			  email.putExtra(Intent.EXTRA_TEXT, message);
 
			  //need this to prompts email client only
			  email.setType("message/rfc822");
 
			  startActivity(Intent.createChooser(email, "Choose an Email client :"));
 
			}
			else Toast.makeText(getApplicationContext(), "No Internet Connection Aavailable", Toast.LENGTH_LONG).show();
			} 
			
		});
	}
	
	private boolean internetOn() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		android.net.NetworkInfo datac = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if ((wifi != null & datac != null) && (wifi.isConnected() | datac.isConnected())) {
			return true;
		}else{
            //no connection 
            return false;
        }
	}
}
