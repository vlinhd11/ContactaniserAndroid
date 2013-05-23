package csse3005.contactaniser.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 
 * @author Jason
 * 
 * JSON abstract class for going to the cloud to get JSON response
 *
 */
public abstract class JSONParser extends AsyncTask<String, Void, String> {

	private ProgressDialog progressDialog;
	protected Context context;
	private HttpPost httpPost;
	protected int userid;
	
	public void setUserid(int userid) {
		this.userid = userid;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public void setHttpPost(HttpPost httpPost) {
		this.httpPost = httpPost;
	}

	/**
	 * display the progress dialog
	 */
	@Override    
    protected void onPreExecute() 
    {       
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Sync ...");
        progressDialog.show();
    }
	
	/**
	 * go to website and get the JSON
	 */
	@Override
	protected String doInBackground(String... arg) {
		String line = "";
		String ret = "";
		
		// Create http client
		HttpClient client = new DefaultHttpClient();
		
		try {
			// Get the response from the website
			HttpResponse response = client.execute(httpPost);
			
			// Check the status of the response
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			
			if (statusCode == 200) { //OK
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				
				while ((line = rd.readLine()) != null) {
					ret += line;
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * once it is finished dismiss the dialog and process the JSON
	 */
	@Override
	protected void onPostExecute(String result) {
		JSONObject json;
		try {
			// turn the result into a JSONObject
			json = new JSONObject(result);
			processJSON(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		progressDialog.dismiss();
	}
	
	public abstract void processJSON(JSONObject json);
}
