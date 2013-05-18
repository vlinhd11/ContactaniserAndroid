package csse3005.contactaniser.library;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

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
}
