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
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import csse3005.contactaniser.library.PasswordValidator;
import csse3005.contactaniserapp.R;

public class ChangePassword extends Activity {
	private EditText username;
	private EditText txtOldPwd;
	private EditText txtNewPwd;
	private EditText txtConfPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		findViewById(R.id.username).requestFocus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void changePassword(View view) {
		username = (EditText) findViewById(R.id.username);
		txtOldPwd = (EditText) findViewById(R.id.txtOldPwd);
		txtNewPwd = (EditText) findViewById(R.id.txtNewPwd);
		txtConfPwd = (EditText) findViewById(R.id.txtConfirmPwd);
		
		username.setError(null);
		txtOldPwd.setError(null);
		txtNewPwd.setError(null);
		txtConfPwd.setError(null);
		View focusView = null;
		boolean cancel = false;
		
		
		// check for empty username
		if (username.getText().toString().equals("")) {
			username.setError(getText(R.string.error_field_required));
			cancel = true;
			focusView = username;
		}
		
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
		
		findViewById(R.id.btnChange).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				changeNewPwd();
				Toast.makeText(getApplicationContext(), "Test connection to the PHP Server", Toast.LENGTH_LONG).show();
			}
		});
	}
	
//	private void exitChangePassword() {
//		startActivity(new Intent(this, MainActivity.class));
//	}
	
	private void changeNewPwd() {
		// attempt authentication against a network service.
		HttpPost httpRequest = new HttpPost("http://triple11.com/BlueTeam/android/change_password.php");
    	List<NameValuePair> nvp = new ArrayList<NameValuePair>(3);
    	nvp.add(new BasicNameValuePair("username", "blueteam"));
    	nvp.add(new BasicNameValuePair("oldpwd", txtOldPwd.getText().toString()));
    	nvp.add(new BasicNameValuePair("newpwd", txtNewPwd.getText().toString()));
    	
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
                		new AlertDialog.Builder(this)
        			    .setTitle(R.string.password_change_success)
        			    .setMessage(R.string.password_change_success)
        			    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
        			        public void onClick(DialogInterface dialog, int which) { 
        			            // do nothing
        			        }
        			     })
        			     .show();
                	}
                	else
                	{
                		new AlertDialog.Builder(this)
        			    .setTitle(R.string.password_change_success)
        			    .setMessage(R.string.change_password_fail)
        			    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
        			        public void onClick(DialogInterface dialog, int which) { 
        			            // do nothing
        			        }
        			     })
        			     .show();
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
	}
}