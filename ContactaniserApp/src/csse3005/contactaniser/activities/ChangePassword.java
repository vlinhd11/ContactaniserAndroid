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
import android.widget.Toast;
import csse3005.contactaniser.library.InternetCheck;
import csse3005.contactaniser.library.PasswordValidator;
import csse3005.contactaniserapp.R;

public class ChangePassword extends Activity {
	private String username;
	private int userID;
	private EditText txtOldPwd;
	private EditText txtNewPwd;
	private EditText txtConfPwd;
	private ChangePasswordTask changePwdTask = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		findViewById(R.id.txtOldPwd).requestFocus();
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
		
		// check for empty pwd
		if (txtConfPwd.getText().toString().equals("")) {
			txtConfPwd.setError(getText(R.string.error_field_required));
			cancel = true;
			focusView = txtConfPwd;
		}
		
		PasswordValidator check = new PasswordValidator();
		if (!check.validate(txtNewPwd.getText().toString())) {
			txtNewPwd.setError(getText(R.string.string_requirement));
			cancel = true;
			focusView = txtNewPwd;
		}
		
		
		// check for empty pwd
		if (txtNewPwd.getText().toString().equals("")) {
			txtNewPwd.setError(getText(R.string.error_field_required));
			cancel = true;
			focusView = txtNewPwd;
		}
		
		
		if (txtOldPwd.getText().toString().equals("")) {
			txtOldPwd.setError(getText(R.string.error_field_required));
			cancel = true;
			focusView = txtOldPwd;
		}
		
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
		
		if (cancel) {
			focusView.requestFocus();
			return;
		}
		else {
			InternetCheck internet = new InternetCheck();
			boolean internetOn = internet.internetOn(this);
			if (!internetOn) {
				new AlertDialog.Builder(this)
			    .setTitle(R.string.network_error)
			    .setMessage(R.string.network_error_message)
			    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
			     .show();
				return;
			}
			
			changePwdTask = new ChangePasswordTask();
			changePwdTask.execute((Void) null);
		}
	}
		
	private boolean changeNewPwd() {
		
		// attempt authentication against a network service.
		HttpPost httpRequest = new HttpPost("http://protivity.triple11.com/android/changePassword.php");
    	List<NameValuePair> nvp = new ArrayList<NameValuePair>(4);
    	
    	nvp.add(new BasicNameValuePair("id", Integer.toString(userID)));
    	nvp.add(new BasicNameValuePair("username", username));
    	nvp.add(new BasicNameValuePair("password", txtOldPwd.getText().toString()));
    	nvp.add(new BasicNameValuePair("newPassword", txtNewPwd.getText().toString()));
    	
    	try
        {
            httpRequest.setEntity(new UrlEncodedFormEntity(nvp));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 200)
            {
                String strResult = EntityUtils.toString(httpResponse.getEntity());
                JSONObject json;
                try {
                	json = new JSONObject(strResult);
                	if (json.get("Result").equals("Success")) {
                		return true;
                	}
                } catch (JSONException e) {
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
				Toast.makeText(getApplicationContext(), "Success, Password changed", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "Failed, Incorrect Password", Toast.LENGTH_LONG).show();

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