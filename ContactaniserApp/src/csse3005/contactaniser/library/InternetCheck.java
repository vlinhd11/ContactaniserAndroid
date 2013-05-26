package csse3005.contactaniser.library;

import csse3005.contactaniserapp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;

/** Class to check internet connectivity */
public class InternetCheck {
	
	public boolean internetOn(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		android.net.NetworkInfo datac = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if ((wifi != null & datac != null) && (wifi.isConnected() | datac.isConnected())) {
			return true;
		}else{
            //no connection 
            return false;
        }
	}
	
	public void NetworkError(Activity activity) {
		new AlertDialog.Builder(activity)
	    .setTitle(R.string.network_error)
	    .setMessage(R.string.network_error_message)
	    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	     .show();
	}
}
