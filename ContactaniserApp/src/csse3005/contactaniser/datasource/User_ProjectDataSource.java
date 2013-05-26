package csse3005.contactaniser.datasource;
import java.sql.Date;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User_Project;

/**
 * Class to manage User_Project table
 */
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
				
				/**
				 * Create and Update User_Project
				 * @param upid ID
				 * @param upufid User ID Foreign Key 
				 * @param uppfid Project ID Foreign Key
				 * @param role Role
				 * @param uplastupdate
				 * @param status Status 
				 * @return newProject
				 */
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

				/**
				 * delete User_Project
				 * @param user_project
				 */
				public void deleteUser_Project(User_Project user_project) {
					long id = user_project.getUPUid();
					database.delete(MySQLHelper.TABLE_USER_PROJECT, MySQLHelper.COLUMN_USERPROJECTUSERFID
					    + " = " + id, null);
				}
				/**
				 * Delete all User_Project value
				 */
				public void deleteAllUserProject() {
					database.delete(MySQLHelper.TABLE_USER_PROJECT, null, null);
				}

				/**
				 * Get all User_Project
				 * @return User_Project
				 */
				public ArrayList<User_Project> getAllUser_Project() {
					ArrayList<User_Project> User_Projects = new ArrayList<User_Project>();

					//Retrieve all tasks with the tid and pid given
					Cursor cursor = database.query(MySQLHelper.TABLE_USER_PROJECT,
					    allColumns, MySQLHelper.COLUMN_USERPROJECTSTATUS + " =0", null, null, null, null);

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
				
				/**
				 * Get All User_Project by User Id
				 * @param uid User Id
				 * @return User_Project
				 */
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
				
				/**
				 * Get all User_Project by Project Id
				 * @param pid Project id
				 * @return User_Project
				 */
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

				/**
				 * Cursor to pass user_project value
				 * @param cursor of User_Project Table
				 * @return user_project
				 */
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
				
				/**
				 * Cursor to pass User_Project value by Id
				 * @param rowId User_Project Id
				 * @return cursor of the User_Project 
				 * @throws SQLException throws if there are no User_Project
				 */
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
