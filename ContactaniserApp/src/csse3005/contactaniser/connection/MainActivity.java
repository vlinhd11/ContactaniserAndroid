package csse3005.contactaniser.connection;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import csse3005.contactaniserapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    public void login(View view) {
    	EditText etUser = (EditText) findViewById(R.id.username);
    	EditText etPass = (EditText) findViewById(R.id.password);
    	
    	// check that the user has entered data in the username and password fields
    	if (isEmpty(etUser) || isEmpty(etPass)) {
    		Toast.makeText(this, "Please enter a username and password to continue", Toast.LENGTH_SHORT).show();
    	    return;
    	}
    	
    	LoginTask loginTask = new LoginTask();
    	loginTask.setContext(this);
    	
    	// create a post and create the parameters for it
    	HttpPost httpPost = new HttpPost("/login.php");
    	List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
    	nvp.add(new BasicNameValuePair("username", etUser.getText().toString()));
    	nvp.add(new BasicNameValuePair("password", etPass.getText().toString()));
    	try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvp));
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
    	loginTask.setHttpPost(httpPost);
    	
    	loginTask.setUsername(etUser.getText().toString());
    	
    	// run the http post request in the background
    	loginTask.execute();
    }
    
    /*
     * Check to see if the EditText is empty, return true if empty
     */
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
    
    private class LoginTask extends JSONTask {

		@Override
		public void processJSON(JSONObject json) {
			try {
				if (json.get("Result").equals("Fail")) {
					
					// if the login is not successful display a messages saying so
					Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_SHORT).show();
					
				} else if (json.get("Result").equals("Success")) {
					
					// if the login is successful go to the next activity
					Intent i = new Intent(context, Sync.class);
					i.putExtra("username", username);
					startActivity(i);
				}
				
				
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		}
    	
    }
    
}
