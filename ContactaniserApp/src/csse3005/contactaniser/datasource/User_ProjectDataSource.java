package csse3005.contactaniser.datasource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User_Project;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
						MySQLHelper.COLUMN_USERPROJECTPROJECTFID, MySQLHelper.COLUMN_ROLE, MySQLHelper.COLUMN_USERPROJECTLASTUPDATE};
				

				public User_ProjectDataSource(Context context) {
					dbHelper = new MySQLHelper(context);
				}
				
				public void open() throws SQLException {
					database = dbHelper.getWritableDatabase();
				}
				
				public void close() {
					dbHelper.close();
				}
				
				public User_Project createUser_Project(int upufid, int uppfid, String role, Date uplastupdate) {
					ContentValues values = new ContentValues();
					values.put(MySQLHelper.COLUMN_USERPROJECTUSERFID, upufid); 
					values.put(MySQLHelper.COLUMN_USERPROJECTPROJECTFID, uppfid);
					values.put(MySQLHelper.COLUMN_ROLE,role);
					values.put(MySQLHelper.COLUMN_USERPROJECTLASTUPDATE, uplastupdate.toString());
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

				public void deleteUser_Project(User_Project user_project) {
					long id = user_project.getUPUid();
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
					Date lu = Date.valueOf(cursor.getString(3));
					user_project.setUserProjectLastUpdate(lu);
				
					return user_project;
				}
}
