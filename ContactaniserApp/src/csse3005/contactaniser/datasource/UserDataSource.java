package csse3005.contactaniser.datasource;

import java.util.ArrayList;
import java.util.List;

import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDataSource {
	// Database fields
		private SQLiteDatabase database;
		private MySQLHelper dbHelper;
		private String[] allColumns = { MySQLHelper.COLUMN_USERID, 
				MySQLHelper.COLUMN_USER_USERNAME,
				MySQLHelper.COLUMN_USERNAME,
				MySQLHelper.COLUMN_USERPHONENUMBER,
				MySQLHelper.COLUMN_USEREMAIL,
				};

		public UserDataSource(Context context) {
			dbHelper = new MySQLHelper(context);
		}
		
		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}
		
		public void close() {
			dbHelper.close();
		}
		
		public User createUser(String user_username, String username, int phonenumber, String email, String password) {
			ContentValues values = new ContentValues();
			values.put(MySQLHelper.COLUMN_USER_USERNAME, user_username);
			values.put(MySQLHelper.COLUMN_USERNAME, username);
			values.put(MySQLHelper.COLUMN_USERPHONENUMBER, phonenumber);
			values.put(MySQLHelper.COLUMN_USEREMAIL, email);
		    long insertId = database.insert(MySQLHelper.TABLE_USER, null,
		        values);
		    Cursor cursor = database.query(MySQLHelper.TABLE_USER,
		        allColumns, MySQLHelper.COLUMN_USERID + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    User newUser = cursorToUser(cursor);
		    cursor.close();
		    return newUser;
		}

		public void deleteUser(User user) {
			long id = user.getUserid();
			database.delete(MySQLHelper.TABLE_USER, MySQLHelper.COLUMN_USERID
			    + " = " + id, null);
		}


		public List<User> getAllUser(String pid) {
			List<User> users = new ArrayList<User>();

			//Retrieve all tasks with the pid given
			Cursor cursor = database.query(MySQLHelper.TABLE_USER,
			    allColumns, null, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				User user = cursorToUser(cursor);
			    users.add(user);
			    cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return users;
		}

		private User cursorToUser(Cursor cursor) {
			User user = new User();
			user.setUserid(cursor.getInt(0));
			user.setUser_UserName(cursor.getString(1));
			user.setUserName(cursor.getString(2));
			user.setUserPhoneNumber(cursor.getInt(3));
			user.setUserEmail(cursor.getString(4));
			
			return user;
		}
}
