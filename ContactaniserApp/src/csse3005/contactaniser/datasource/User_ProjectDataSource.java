package csse3005.contactaniser.datasource;

import java.util.ArrayList;
import java.util.List;

import csse3005.contactaniser.models.Logs;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User_Project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class User_ProjectDataSource {
	// Database fields
				private SQLiteDatabase database;
				private MySQLHelper dbHelper;
				private String[] allColumns = { MySQLHelper.COLUMN_USERPROJECTUSERFID, 
						MySQLHelper.COLUMN_USERPROJECTPROJECTFID, MySQLHelper.COLUMN_ROLE};
				

				public User_ProjectDataSource(Context context) {
					dbHelper = new MySQLHelper(context);
				}
				
				public void open() throws SQLException {
					database = dbHelper.getWritableDatabase();
				}
				
				public void close() {
					dbHelper.close();
				}
				
				public User_Project createUser_Project(int utufid, int uttfid, String role) {
					ContentValues values = new ContentValues();
					values.put(MySQLHelper.COLUMN_USERPROJECTUSERFID, utufid); 
					values.put(MySQLHelper.COLUMN_USERPROJECTPROJECTFID, uttfid);
					values.put(MySQLHelper.COLUMN_ROLE,role);
				    long insertId = database.insert(MySQLHelper.TABLE_USER_PROJECT, null,
					        values);
				    Cursor cursor = database.query(MySQLHelper.TABLE_USER_PROJECT,
				        allColumns, MySQLHelper.COLUMN_USERPROJECTUSERFID + " = " + insertId, null,
				        null, null, null);
				   
				    cursor.moveToFirst();
				    User_Project newUser_Project = cursorToUser_Project(cursor);
				    cursor.close();
				    return newUser_Project;
				}

				public void deleteUser_Task(Logs logs) {
					long id = logs.getLogid();
					database.delete(MySQLHelper.TABLE_USER_PROJECT, MySQLHelper.COLUMN_USERPROJECTUSERFID
					    + " = " + id, null);
				}


				public List<User_Project> getAllUser_Project(String uid, String pid) {
					List<User_Project> User_Projects = new ArrayList<User_Project>();

					//Retrieve all tasks with the tid and pid given
					Cursor cursor = database.query(MySQLHelper.TABLE_USER_PROJECT,
					    allColumns, MySQLHelper.COLUMN_USERPROJECTUSERFID + " = " + uid + " AND " + MySQLHelper.COLUMN_USERPROJECTPROJECTFID + " = " + pid, null, null, null, null);

					cursor.moveToFirst();
					while (!cursor.isAfterLast()) {
						User_Project User_Project = cursorToUser_Project(cursor);
					    User_Projects.add(User_Project);
					    cursor.moveToNext();
					}
					// Make sure to close the cursor
					cursor.close();
					return User_Projects;
				}

				private User_Project cursorToUser_Project(Cursor cursor) {
					User_Project user_project = new User_Project();
					user_project.setUPUid(cursor.getInt(0));
					user_project.setUPPid(cursor.getInt(1));
					user_project.setUserProjectRole(cursor.getString(2));
				
					return user_project;
				}
}
