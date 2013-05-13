package csse3005.contactaniser.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import csse3005.contactaniserapp.R;
 
public class PhoneActivity extends Activity {
	
	final Context context = this;
	private String phonenumber;
	private Button callButton;
	private Button smsButton;
	private EditText textPhoneNo;
	private EditText textSMS;
	private String phoneNo;
	
	public void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_function);
 
		callButton = (Button) findViewById(R.id.buttonCall);
		smsButton = (Button) findViewById(R.id.buttonSend);
		textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
		textSMS = (EditText) findViewById(R.id.editTextSMS);
		
		phonenumber = getIntent().getExtras().getString("userphone");
		textPhoneNo.setText(phonenumber);
		// add PhoneStateListener
		PhoneCallListener phoneListener = new PhoneCallListener();
		TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
		
		// add Call button listener
		callButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg) {
				phoneNo = textPhoneNo.getText().toString();
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+phoneNo));
				startActivity(callIntent);
			}
		});
		
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
	
	
	//monitor phone call activities
		private class PhoneCallListener extends PhoneStateListener {
	 
			private boolean isPhoneCalling = false;
			String LOG_TAG = "LOGGING 123";
	 
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
	 
				if (TelephonyManager.CALL_STATE_RINGING == state) {
					// phone ringing
					Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
				}
	 
				if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
					// active
					Log.i(LOG_TAG, "OFFHOOK");
	 
					isPhoneCalling = true;
				}
	 
				if (TelephonyManager.CALL_STATE_IDLE == state) {
					// run when class initial and phone call ended, 
					// need detect flag from CALL_STATE_OFFHOOK
					Log.i(LOG_TAG, "IDLE");
	 
					if (isPhoneCalling) {
	 
						Log.i(LOG_TAG, "restart app");
	 
						// restart app
//						Intent i = getBaseContext().getPackageManager()
//							.getLaunchIntentForPackage(
//								getBaseContext().getPackageName());
//						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						startActivity(i);
						
						// return to previous activity
						Intent i = new Intent(PhoneActivity.this, PhoneActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).listen(this, LISTEN_NONE);
						startActivity(i);
	 
						isPhoneCalling = false;
					}
				}
			}
		}
}
