package csse3005.contactaniser.datasource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User;

import java.sql.Date;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * The class to manage User Table
 */

public class UserDataSource {
	// Database fields
		private SQLiteDatabase database;
		private MySQLHelper dbHelper;
		private String[] allColumns = { MySQLHelper.COLUMN_USERID, 
				MySQLHelper.COLUMN_USER_USERNAME,
				MySQLHelper.COLUMN_USERNAME,
				MySQLHelper.COLUMN_USERPHONENUMBER,
				MySQLHelper.COLUMN_USEREMAIL,
				MySQLHelper.COLUMN_USERLASTUPDATE
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
		
		/** Create and Update user 
		 * @param userid User ID
		 * @param user_username User Name
		 * @param username Name
		 * @param phonenumber Phone Number
		 * @param email Email
		 * @param lastupdate Last Update
		 * 
		 * @return User object
		 */
		public User createUser(String userid, String user_username, String username, String phonenumber, String email, Date lastupdate) {
			ContentValues values = new ContentValues();
			values.put(MySQLHelper.COLUMN_USER_USERNAME, user_username);
			values.put(MySQLHelper.COLUMN_USERNAME, username);
			values.put(MySQLHelper.COLUMN_USERPHONENUMBER, phonenumber);
			values.put(MySQLHelper.COLUMN_USEREMAIL, email);
			values.put(MySQLHelper.COLUMN_USERLASTUPDATE, lastupdate.toString());
			
			String[] str = {userid};
			
			int affectedRows = database.update(MySQLHelper.TABLE_USER,
					values, MySQLHelper.COLUMN_USERID + " = ?",
					str);
			
			if (affectedRows == 1) {
				Cursor cursor = database.query(MySQLHelper.TABLE_USER,
				        allColumns, MySQLHelper.COLUMN_USERID + " = " + userid, null,
				        null, null, null);
				    cursor.moveToFirst();
				    User newUser = cursorToUser(cursor);
				    cursor.close();
				    return newUser;
			}
			else
			{
			values.put(MySQLHelper.COLUMN_USERID, userid);
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
		}

		/** Delete User*/
		public void deleteUser(User user) {
			long id = user.getUserid();
			database.delete(MySQLHelper.TABLE_USER, MySQLHelper.COLUMN_USERID
			    + " = " + id, null);
		}
		
		/** Delete all user */
		public void deleteAllUser() {
			database.delete(MySQLHelper.TABLE_USER, null, null);
		}

		/** List all user */
		public ArrayList<User> getAllUser() {
			ArrayList<User> users = new ArrayList<User>();

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

		/** Cursor to pass User value
		 * @param cursor of User Table
		 * @return User
		 */
		private User cursorToUser(Cursor cursor) {
			User user = new User();
			user.setUserid(cursor.getInt(0));
			user.setUser_UserName(cursor.getString(1));
			user.setUserName(cursor.getString(2));
			user.setUserPhoneNumber(cursor.getString(3));
			user.setUserEmail(cursor.getString(4));
			Date lu = Date.valueOf(cursor.getString(5));
			user.setUserLastUpdate(lu);
			
			return user;
		}
		/** 
		 * Cursor to pass User value
		 * 
		 * @param rowId the UserId that the value want to pass
		 * 
		 * @return mCursor cursor of the User
		 *  
		 *  @throws SQLException if cannot find User
		 */
		public Cursor fetchUserById(long rowId) throws SQLException {

	        Cursor mCursor =

	            database.query(true,MySQLHelper.TABLE_USER , allColumns, MySQLHelper.COLUMN_USERID + "=" + rowId, null,
	                    null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;

	    }
}
