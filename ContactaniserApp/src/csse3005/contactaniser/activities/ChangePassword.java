package csse3005.contactaniser.activities;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.example.contactaniserapp.R;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ChangePassword extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		findViewById(R.id.txtOldPwd).requestFocus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void changePassword(View view) {
		EditText txtOldPwd = (EditText) findViewById(R.id.txtOldPwd);
		EditText txtNewPwd = (EditText) findViewById(R.id.txtNewPwd);
		EditText txtConfPwd = (EditText) findViewById(R.id.txtConfirmPwd);
		
		txtOldPwd.setError(null);
		txtNewPwd.setError(null);
		txtConfPwd.setError(null);
		View focusView = null;
		boolean cancel = false;
		
		
		if (txtConfPwd.getText().toString().equals("")) {
			txtConfPwd.setError(getText(R.string.error_field_required));
			cancel = true;
			focusView = txtConfPwd;
		}
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
		
//		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
//		String oldPwdEncrypted = encryptor.encryptPassword(txtOldPwd.getText().toString());
//		String newPwdEncrypted = encryptor.encryptPassword(txtNewPwd.getText().toString());
//		
//		// TODO: some function to retrieve encrypted password? or do that at start
//		String serverPwd = SOMETHING;
//		
//		if (!serverPwd.equals(oldPwdEncrypted)) {
//			txtOldPwd.setError(getText(R.string.error_incorrect_password));
//			txtOldPwd.requestFocus();
//			return;
//		}
		
		// TODO: some function to send off new hashed password
		// error check here

//		InfoDialog feedback = new InfoDialog();
//		feedback.dialogMessage = "Password sucessfully changed.";
//		feedback.show(getFragmentManager(), WINDOW_SERVICE);
		
		new AlertDialog.Builder(this)
	    .setTitle(R.string.success)
	    .setMessage(R.string.password_change_success)
	    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
//	        	exitChangePassword();
	        	finish();
	        }
	     })
	     .show();
	}
	
//	private void exitChangePassword() {
//		startActivity(new Intent(this, MainActivity.class));
//	}

}
