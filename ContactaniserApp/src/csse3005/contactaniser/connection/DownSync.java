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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import csse3005.contactaniserapp.R;

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
    	HttpPost httpPost = new HttpPost("http://triple11.com/BlueTeam/android/syncDown.php");
    	List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
    	nvp.add(new BasicNameValuePair("ID", username));
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
						if (names.getString(i).equals("")) {
							
							// if the JSON object contains a update get the information
							// about that needs to be updated
//							JSONObject staff = json.getJSONObject("Staff");
//							String firstName = staff.getString("FirstName");
//							String lastName = staff.getString("LastName");
//							String phone = staff.getString("Phone");
//							String email = staff.getString("Email");
//							
//							// open the staff dao and update/insert the staff information
//							StaffDataSource sds = new StaffDataSource(context);
//							sds.open();
//							sds.createStaff(username, firstName, lastName, phone, email);
//							sds.close();
							
//							// update the text view to show that staff has been updated.
//							TextView tvStaff = (TextView) findViewById(R.id.staff);
//							tvStaff.setText("updated staff");
//							tvStaff.invalidate();
						}
					}
					
					// put the JSON data in the edit text field
					EditText etJson = (EditText) findViewById(R.id.json);
					etJson.setText(json.toString());
					etJson.invalidate();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
