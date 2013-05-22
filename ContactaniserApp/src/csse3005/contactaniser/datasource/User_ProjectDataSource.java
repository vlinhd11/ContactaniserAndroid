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
				private String[] allColumns = { MySQLHelper.COLUMN_USERPROJECTID, MySQLHelper.COLUMN_USERPROJECTUSERFID, 
						MySQLHelper.COLUMN_USERPROJECTPROJECTFID, MySQLHelper.COLUMN_ROLE, MySQLHelper.COLUMN_USERPROJECTLASTUPDATE, MySQLHelper.COLUMN_USERPROJECTSTATUS};
				

				public User_ProjectDataSource(Context context) {
					dbHelper = new MySQLHelper(context);
				}
				
				public void open() throws SQLException {
					database = dbHelper.getWritableDatabase();
				}
				
				public void close() {
					dbHelper.close();
				}
				
				public User_Project createUser_Project(String upid, int upufid, int uppfid, String role, Date uplastupdate, String status) {
					ContentValues values = new ContentValues();
					values.put(MySQLHelper.COLUMN_USERPROJECTUSERFID, upufid); 
					values.put(MySQLHelper.COLUMN_USERPROJECTPROJECTFID, uppfid);
					values.put(MySQLHelper.COLUMN_ROLE,role);
					values.put(MySQLHelper.COLUMN_USERPROJECTLASTUPDATE, uplastupdate.toString());
					values.put(MySQLHelper.COLUMN_USERPROJECTSTATUS, status);
					
					String[] str = {upid};
					int affectedRows = database.update(MySQLHelper.TABLE_USER_PROJECT,
							values, MySQLHelper.COLUMN_USERPROJECTID + " = ?",
							str);
					
					if (affectedRows == 1)
					{
						 Cursor cursor = database.query(MySQLHelper.TABLE_USER_PROJECT,
							        allColumns, MySQLHelper.COLUMN_USERPROJECTID + " = " + upid, null,
							        null, null, null);
							   
							    cursor.moveToFirst();
							    User_Project newUser_Project = cursorToUser_Project(cursor);
							    cursor.close();
							    return newUser_Project;
					}
					else
					{
					values.put(MySQLHelper.COLUMN_USERPROJECTID, upid);
				    long insertId = database.insert(MySQLHelper.TABLE_USER_PROJECT, null,
					        values);
				    Cursor cursor = database.query(MySQLHelper.TABLE_USER_PROJECT,
				        allColumns, MySQLHelper.COLUMN_USERPROJECTID + " = " + insertId, null,
				        null, null, null);
				   
				    cursor.moveToFirst();
				    User_Project newUser_Project = cursorToUser_Project(cursor);
				    cursor.close();
				    return newUser_Project;
					}
				}

				public void deleteUser_Project(User_Project user_project) {
					long id = user_project.getUPUid();
					database.delete(MySQLHelper.TABLE_USER_PROJECT, MySQLHelper.COLUMN_USERPROJECTUSERFID
					    + " = " + id, null);
				}
				
				public void deleteAllUserProject() {
					database.delete(MySQLHelper.TABLE_USER_PROJECT, null, null);
				}


				public List<User_Project> getAllUser_Project(String uid, String pid) {
					List<User_Project> User_Projects = new ArrayList<User_Project>();

					//Retrieve all tasks with the tid and pid given
					Cursor cursor = database.query(MySQLHelper.TABLE_USER_PROJECT,
					    allColumns, MySQLHelper.COLUMN_USERPROJECTUSERFID + " = " + uid + " AND " + MySQLHelper.COLUMN_USERPROJECTPROJECTFID + " = " + pid + " AND " + MySQLHelper.COLUMN_USERPROJECTSTATUS + " =0", null, null, null, null);

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
				
				public ArrayList<User_Project> getAllProjectbyUserId(long uid) {
					ArrayList<User_Project> User_Projects = new ArrayList<User_Project>();

					//Retrieve all tasks with the tid and pid given
					Cursor cursor = database.query(MySQLHelper.TABLE_USER_PROJECT,
					    allColumns, MySQLHelper.COLUMN_USERPROJECTUSERFID + " = " + uid + " AND " + MySQLHelper.COLUMN_USERPROJECTSTATUS + " =0", null, null, null, null);
					

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
				
				public ArrayList<User_Project> getAllUserbyProjectId(long pid) {
					
					ArrayList<User_Project> User_Projects = new ArrayList<User_Project>();
					//Retrieve all tasks with the tid and pid given
					Cursor cursor = database.query(MySQLHelper.TABLE_USER_PROJECT,
					    allColumns, MySQLHelper.COLUMN_USERPROJECTPROJECTFID + " = " + pid + " AND " + MySQLHelper.COLUMN_USERPROJECTSTATUS + " =0", null, null, null, null);
					

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
					user_project.setUPid(cursor.getLong(0));
					user_project.setUPUid(cursor.getInt(1));
					user_project.setUPPid(cursor.getInt(2));
					user_project.setUserProjectRole(cursor.getString(3));
					Date lu = Date.valueOf(cursor.getString(4));
					user_project.setUserProjectLastUpdate(lu);
					user_project.setUserProjectStatus(cursor.getString(5));
				
					return user_project;
				}
				
				public Cursor fetchUserProjectById(long upuid, long uppid) throws SQLException {

			        Cursor mCursor =

			            database.query(true,MySQLHelper.TABLE_USER_PROJECT , allColumns, MySQLHelper.COLUMN_USERPROJECTUSERFID + "=" + upuid + " AND " + MySQLHelper.COLUMN_USERPROJECTPROJECTFID + "=" + uppid, null,
			                    null, null, null, null);
			        if (mCursor != null) {
			            mCursor.moveToFirst();
			        }
			        return mCursor;

			    }
				
				
}
