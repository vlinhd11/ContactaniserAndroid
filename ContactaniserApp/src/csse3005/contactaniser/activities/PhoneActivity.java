package csse3005.contactaniser.activities;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class PhoneActivity extends Activity {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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


//public class PhoneActivity extends Activity {
//	
//	final Context context = this;
//	private String phonenumber;
//	private String username;
//	private String sms;
//	private Button smsButton;
//	private TextView contactName;
//	private TextView textPhoneNo;
//	private EditText textSMS;
//	private String phoneNo;
	
//	public void onCreate(Bundle savedInstanceState) {
// 
//		super.onCreate(savedInstanceState);
////		setContentView(R.layout.phone_function);
// 
////		smsButton = (Button) findViewById(R.id.buttonSend);
////		contactName = (TextView) findViewById(R.id.editContactName);
////		textPhoneNo = (TextView) findViewById(R.id.editTextPhoneNo);
////		textSMS = (EditText) findViewById(R.id.editTextSMS);
//		
//		phonenumber = getIntent().getExtras().getString("phonenumber");
////		username = getIntent().getExtras().getString("username");
//		sms = getIntent().getExtras().getString("message");
////		textPhoneNo.setText(phonenumber);
////		contactName.setText(username);
////		textSMS.setText(sms);
////		textSMS.requestFocus();
//		
//		// add SMS button listener
////		smsButton.setOnClickListener(new OnClickListener() {
//// 
////			@Override
////			public void onClick(View v) {
//// 
////			  phoneNo = textPhoneNo.getText().toString();
////			  String sms = textSMS.getText().toString();
//// 
//			  try {
//				SmsManager smsManager = SmsManager.getDefault();
//				smsManager.sendTextMessage(phoneNo, null, sms, null, null);
//				Toast.makeText(getApplicationContext(), "Message Delivered",
//							Toast.LENGTH_LONG).show();
//			  } catch (Exception e) {
//				Toast.makeText(getApplicationContext(),
//					"Sending message failed, please try again later",
//					Toast.LENGTH_LONG).show();
//				e.printStackTrace();
//			  }
// 
//			}
////		});
////	}
//}
