package csse3005.contactaniser.connection;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import csse3005.contactaniserapp.R;

import csse3005.contactaniser.datasource.TaskDataSource;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class DownSync extends Activity {
	String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_down_sync);
		
		Intent intent = getIntent();
		username = intent.getExtras().getString("username");
	}
	
	/**
	 * When the activity starts make a call to the down sync cloud page
	 * to gather all the data from a particular date.
	 */
	protected void onStart() {
		super.onStart();
		DownSycnTask dsTask = new DownSycnTask();
		dsTask.setContext(this);
		
		// get the last time the device synced.
		LastUpdatedDataSource luds = new LastUpdatedDataSource(this);
		luds.open();
		LastUpdated lu = luds.getLatest();
		luds.close();
    	
		// create the http post and add the parameters
    	HttpPost httpPost = new HttpPost("syncDown.php");
    	List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
    	nvp.add(new BasicNameValuePair("UserID", username));  // --> Field Name to be confirmed
    	nvp.add(new BasicNameValuePair("LastUpdate", lu.toString()));
    	try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvp));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	dsTask.setHttpPost(httpPost);
    	dsTask.setUsername(username);
    	dsTask.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.down_sync, menu);
		return true;
	}
	
	private class DownSycnTask extends JSONTask {

		@Override
		public void processJSON(JSONObject json) {
			try {
				// if the JSON comes back successfully
				if (json.get("Result").equals("Success")) {
					
					// get the names that are in the JSON return
					JSONArray names = json.names();
					int i;
					for (i = 0; i < names.length(); i++) {
						if (names.getString(i).equals("task")) {
							
							// if the JSON object contains a task update get the information about the task that needs to be updated
							
//							JSONObject task = json.getJSONObject("Task");
//							String firstName = task.getString("FirstName");							
							
							// open the task dao and update/insert the task information
							TaskDataSource tds = new TaskDataSource(context);
							tds.open();
//							tds.createTask();
							tds.close();
							
							// update the text view to show that task has been updated.
							TextView tvtask = (TextView) findViewById(R.id.task);
							tvtask.setText("updated task");
							tvtask.invalidate();
						}
					}
					
					// put the JSON data in the edit text field
					EditText etJson = (EditText) findViewById(R.id.json);
					etJson.setText(json.toString());
					etJson.invalidate();
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
    	
    }

}
