package csse3005.contactaniser.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import csse3005.contactaniserapp.R;
 
public class PhoneActivity extends Activity {
	
	final Context context = this;
	private String phonenumber;
	private String username;
	private Button smsButton;
	private TextView contactName;
	private TextView textPhoneNo;
	private EditText textSMS;
	private String phoneNo;
	
	public void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_function);
 
		smsButton = (Button) findViewById(R.id.buttonSend);
		contactName = (TextView) findViewById(R.id.editContactName);
		textPhoneNo = (TextView) findViewById(R.id.editTextPhoneNo);
		textSMS = (EditText) findViewById(R.id.editTextSMS);
		
		phonenumber = getIntent().getExtras().getString("phonenumber");
		username = getIntent().getExtras().getString("username");
		textPhoneNo.setText(phonenumber);
		contactName.setText(username);
		textSMS.requestFocus();
		
		// add SMS button listener
		smsButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
 
			  phoneNo = textPhoneNo.getText().toString();
			  String sms = textSMS.getText().toString();
 
			  try {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(phoneNo, null, sms, null, null);
				Toast.makeText(getApplicationContext(), "SMS Sent!",
							Toast.LENGTH_LONG).show();
			  } catch (Exception e) {
				Toast.makeText(getApplicationContext(),
					"SMS faild, please try again later!",
					Toast.LENGTH_LONG).show();
				e.printStackTrace();
			  }
 
			}
		});
	}
}
