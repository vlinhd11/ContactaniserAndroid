package csse3005.contactaniser.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import csse3005.contactaniser.library.MCrypt;
import csse3005.contactaniserapp.R;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mUsername;
	private String mPassword;
	private int userID;

	// UI references.
	private EditText mUsernameView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private View focusView;
	private TextView mLoginStatusMessageView;

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// TODO: test code, comment below line out
//		startActivity(new Intent(this, MainActivity.class));
		setupLogin();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUsernameView.setError(null);
		mPasswordView.setError(null);
		
		// Store values at the time of the login attempt.
		mUsername = mUsernameView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		
		
		
		
		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		}
		
		//check for valid length of password
		if (mPassword.length() != 0 && mPassword.length() < 6) {
			mPasswordView.setError("Min. of 6 characters required");
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid username.
		if (TextUtils.isEmpty(mUsername)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} else {
		// Check for a vaild email.
		if (!validEmail(mUsername)) {
			mUsernameView.setError(getString(R.string.invalid_email));
			focusView = mUsernameView;
			cancel = true;
			}		
		}
		
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			
			if (internetOn()) {
				mAuthTask = new UserLoginTask();
				mAuthTask.execute((Void) null);
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
				showProgress(false);
			}
		}
	}	
	
	
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
				boolean pwMatch = authPass();
		return pwMatch;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				ContinueToProjects();
				finish();
			} else {
				mPasswordView.setError(getString(R.string.error_incorrect_password));
				mPasswordView.setText(null);
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
	
	private void setupLogin() {
		// Set up the login form.
		mUsername = getIntent().getStringExtra(EXTRA_EMAIL);
		mUsernameView = (EditText) findViewById(R.id.username);
		mUsernameView.setText(mUsername);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
							@Override
							public boolean onEditorAction(TextView textView, int id,
									KeyEvent keyEvent) {
								if (id == R.id.login || id == EditorInfo.IME_NULL) {
									attemptLogin();
									return true;
								}
								return false;
							}
						});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								attemptLogin();
							}
						});
		
//		findViewById(R.id.change_password).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				ContinueToChangePassword();
//			}
//		});
			
		mUsernameView.requestFocus();
	}
	
	
	private boolean authPass() {
		
		String eUsername = null;
		String ePassword = null;
		
		// attempt authentication against a network service.
		HttpPost httpRequest = new HttpPost("http://triple11.com/BlueTeam/android/login_decrypt.php");
		
		MCrypt mcrypt = new MCrypt();
		try {
			eUsername = MCrypt.bytesToHex( mcrypt.encrypt(mUsername));
			ePassword = MCrypt.bytesToHex( mcrypt.encrypt(mPassword));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
    	List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
    	nvp.add(new BasicNameValuePair("username", eUsername));
    	nvp.add(new BasicNameValuePair("password", ePassword));
    	
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
                	if (json.get("Result").equals("Success")) 
                		{
                			setUserID(json.getInt("UserID"));
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
	
	private void ContinueToProjects() {
		// if the login is successful go to the next activity
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("username", mUsername);
		i.putExtra("userID", userID);
		startActivity(i);
	}
	
	private boolean validEmail(String email) {
	    Pattern pattern = Patterns.EMAIL_ADDRESS;
	    return pattern.matcher(email).matches();
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
	
	private void setUserID(int userID){
		this.userID = userID;
	}
}