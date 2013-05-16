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
	
	String useremail;
	Button emailButton;
	EditText textTo;
	EditText textSubject;
	EditText textMessage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		useremail = getIntent().getExtras().getString("email");

			if (internetOn()) { 
			  Intent email = new Intent(Intent.ACTION_SEND);
			  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ useremail});
			  
			  //need this to prompts email client only
			  email.setType("message/rfc822");
			  startActivity(Intent.createChooser(email, "Choose an Email client :"));
			}
			else Toast.makeText(getApplicationContext(), "No Internet Connection Aavailable", Toast.LENGTH_LONG).show();
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
