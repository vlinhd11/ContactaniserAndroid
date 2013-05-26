package csse3005.contactaniser.activities;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class PhoneActivity extends Activity {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		// get phone number and SMS from Intent received
		String phoneNo = getIntent().getExtras().getString("phonenumber");
		String sms = getIntent().getExtras().getString("message");
		
		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNo, null, sms, null, null);
			Toast.makeText(getApplicationContext(), "Message Delivered",Toast.LENGTH_LONG).show();
			finish();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
			"Delivery failed, please try again later",
			Toast.LENGTH_LONG).show();
			e.printStackTrace();
			}
	}	
}