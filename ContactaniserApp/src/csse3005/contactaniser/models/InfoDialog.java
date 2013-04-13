package csse3005.contactaniser.models;

import csse3005.contactaniserapp.R;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
//said it needed that ^^ but it really shouldn't - using wrong version of something? There's no way we need the latest API just for a dialog box.
public class InfoDialog extends DialogFragment {
	
	String dialogMessage;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogMessage)
               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // nothing, this class is just for easy display of feedback.
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
