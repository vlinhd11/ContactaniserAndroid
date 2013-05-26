package csse3005.contactaniser.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import csse3005.contactaniser.library.InternetCheck;
import csse3005.contactaniser.library.MCrypt;
import csse3005.contactaniser.library.PasswordValidator;
import csse3005.contactaniserapp.R;

/** Class to change password */
public class ChangePassword extends Activity {
	
	private String username;
	private int userID;
	
	// UI references.
	private EditText txtOldPwd;
	private EditText txtNewPwd;
	private EditText txtConfPwd;
	
	private ChangePasswordTask changePwdTask = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// set layout
		setContentView(R.layout.activity_change_password);
		findViewById(R.id.txtOldPwd).requestFocus();
		
		// get user name and userID from Intent received
		Intent receivedIntent = getIntent();
        username = receivedIntent.getStringExtra("username");
        userID = receivedIntent.getIntExtra("userID", 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void changePassword(View view) {
		
		txtOldPwd = (EditText) findViewById(R.id.txtOldPwd);
		txtNewPwd = (EditText) findViewById(R.id.txtNewPwd);
		txtConfPwd = (EditText) findViewById(R.id.txtConfirmPwd);
		
		txtOldPwd.setError(null);
		txtNewPwd.setError(null);
		txtConfPwd.setError(null);
		View focusView = null;
		boolean cancel = false;
		
		// check for empty password on confirm password field
		if (txtConfPwd.getText().toString().equals("")) {
			txtConfPwd.setError(getText(R.string.error_field_required));
			cancel = true;
			focusView = txtConfPwd;
		}
		
		// validate password
		PasswordValidator check = new PasswordValidator();
		if (!check.validate(txtNewPwd.getText().toString())) {
			txtNewPwd.setError(getText(R.string.string_requirement));
			cancel = true;
			focusView = txtNewPwd;
		}
		
		
		// check for empty password on new password field
		if (txtNewPwd.getText().toString().equals("")) {
			txtNewPwd.setError(getText(R.string.error_field_required));
			cancel = true;
			focusView = txtNewPwd;
		}
		
		// check for empty password on old password field
		if (txtOldPwd.getText().toString().equals("")) {
			txtOldPwd.setError(getText(R.string.error_field_required));
			cancel = true;
			focusView = txtOldPwd;
		}
		
		// check if new password identical to current password 
		if (!cancel) {
			if (!txtNewPwd.getText().toString().equals(txtConfPwd.getText().toString())) {
				txtConfPwd.setError(getText(R.string.password_no_match));
				cancel = true;
				focusView = txtConfPwd;
			} else if (txtNewPwd.getText().toString().equals(txtOldPwd.getText().toString())) {
				txtNewPwd.setError(getText(R.string.password_not_new));
				cancel = true;
				focusView = txtNewPwd;
			}
		}
		
		// start connection process if no validation error
		if (cancel) {
			focusView.requestFocus();
			return;
		}
		else {
			
			// check for available Internet connection
			InternetCheck internet = new InternetCheck();
			boolean internetOn = internet.internetOn(this);
			if (!internetOn) {
				// display dialog to inform user about network error
				internet.NetworkError(this);
				return;
			}
			
			// Start to change password
			changePwdTask = new ChangePasswordTask();
			changePwdTask.execute((Void) null);
		}
	}
		
	private boolean changeNewPwd() {
		
		String eUsername = null; // encrypted user name
		String eOldPwd = null; // encrypted old password
		String eNewPwd = null; // encrypted new password
		String eID = null; // encrypted user ID
		
		// attempt authentication against a network service.
		HttpPost httpRequest = new HttpPost("http://protivity.triple11.com/android/changePassword_secure.php");
		
		// add encryption to user name, old password, new password
		MCrypt mcrypt = new MCrypt();
		try {
			eID = MCrypt.bytesToHex( mcrypt.encrypt(Integer.toString(userID)));
			eUsername = MCrypt.bytesToHex( mcrypt.encrypt(username));
			eOldPwd = MCrypt.bytesToHex( mcrypt.encrypt(txtOldPwd.getText().toString()));
			eNewPwd = MCrypt.bytesToHex( mcrypt.encrypt(txtNewPwd.getText().toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// add the encrypted data to name value pair to be encoded in JSON
    	List<NameValuePair> nvp = new ArrayList<NameValuePair>(4);
    	nvp.add(new BasicNameValuePair("id", eID));
    	nvp.add(new BasicNameValuePair("username", eUsername));
    	nvp.add(new BasicNameValuePair("password", eOldPwd));
    	nvp.add(new BasicNameValuePair("newPassword", eNewPwd));
    	
    	try
        {
            httpRequest.setEntity(new UrlEncodedFormEntity(nvp));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 200)
            {
                String strResult = EntityUtils.toString(httpResponse.getEntity());
                JSONObject json;
                try {
                	// JSON object to store the connection result 
                	json = new JSONObject(strResult);
                	// remove any unnecessary data 
                	String dResult = new String (mcrypt.decrypt(json.getString("Result"))).trim();
                	
                	if (dResult.equals("Success")) {
                		return true;
                	}
                } catch (JSONException e) {
        			e.printStackTrace();
        		} catch (Exception e) {
					e.printStackTrace();
				}
            }
        } catch (ClientProtocolException e){
        	e.printStackTrace();
        } catch (IOException e){
        	e.printStackTrace();
        }
    	return false;
	}
	
	private void showPwdChangeDialog() {
		new AlertDialog.Builder(this)
	    .setTitle(R.string.success)
	    .setMessage(R.string.change_password_success)
	    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            finish();
	        }
	     })
	     .show();
	}
	
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class ChangePasswordTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
				boolean pwChange = changeNewPwd();
		return pwChange;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			changePwdTask = null;
			
			if (success) {
				// inform user about password been changed
				showPwdChangeDialog();
			} else {
				// request for valid password
				txtOldPwd.setError(getString(R.string.error_incorrect_password));
				txtOldPwd.setText(null);
				txtOldPwd.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			changePwdTask = null;
		}
	}
}