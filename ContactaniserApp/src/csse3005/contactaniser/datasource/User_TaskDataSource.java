package csse3005.contactaniser.datasource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User_Project;
import csse3005.contactaniser.models.User_Task;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class User_TaskDataSource {

	// Database fields
			private SQLiteDatabase database;
			private MySQLHelper dbHelper;
			private String[] allColumns = { MySQLHelper.COLUMN_USERTASKID,
					MySQLHelper.COLUMN_USERTASKUSERFID, 
					MySQLHelper.COLUMN_USERTASKTASKFID, MySQLHelper.COLUMN_USERTASKLASTUPDATE};
			

			public User_TaskDataSource(Context context) {
				dbHelper = new MySQLHelper(context);
			}
			
			public void open() throws SQLException {
				database = dbHelper.getWritableDatabase();
			}
			
			public void close() {
				dbHelper.close();
			}
			
			public User_Task createUser_Task(String usertaskid, long user_task, long task_task, Date lastupdate) {
				ContentValues values = new ContentValues();
				values.put(MySQLHelper.COLUMN_USERTASKUSERFID,user_task);
				values.put(MySQLHelper.COLUMN_USERTASKTASKFID, task_task);
				values.put(MySQLHelper.COLUMN_USERTASKLASTUPDATE, lastupdate.toString());
				
				String[] str = {usertaskid};
				
				int affectedRows = database.update(MySQLHelper.TABLE_USER_TASK,
						values, MySQLHelper.COLUMN_USERTASKID + " = ?",
						str);
				
				if (affectedRows == 1)
				{
					Cursor cursor = database.query(MySQLHelper.TABLE_USER_TASK,
					        allColumns, MySQLHelper.COLUMN_USERTASKID + " = " + usertaskid, null,
					        null, null, null);
					    cursor.moveToFirst();
					    User_Task newUser_Task = cursorToUser_Task(cursor);
					    cursor.close();
					    return newUser_Task;
			    
				}
				else
				{
					values.put(MySQLHelper.COLUMN_USERTASKID, usertaskid);
					long insertId = database.insert(MySQLHelper.TABLE_USER_TASK, null,
					        values);
					    Cursor cursor = database.query(MySQLHelper.TABLE_USER_TASK,
					        allColumns, MySQLHelper.COLUMN_USERTASKID + " = " + insertId, null,
					        null, null, null);
					    cursor.moveToFirst();
					    User_Task newUser_Task = cursorToUser_Task(cursor);
					    cursor.close();
					    return newUser_Task;
				}
			}

			public void deleteUser_Task(User_Task user_task) {
				long id = user_task.getUTUid();
				database.delete(MySQLHelper.TABLE_USER_TASK, MySQLHelper.COLUMN_USERTASKUSERFID
				    + " = " + id, null);
			}
			
			public void deleteAllUserTask() {
				database.delete(MySQLHelper.TABLE_USER_TASK, null, null);
			}
			
			public void deleteUserTaskbyUserId(long uid)
			{
				database.delete(MySQLHelper.TABLE_USER_TASK, MySQLHelper.COLUMN_USERTASKUSERFID
						+ " = " + uid, null);
			}


			public List<User_Task> getAllUser_Task(String uid, String tid) {
				List<User_Task> User_Tasks = new ArrayList<User_Task>();

				//Retrieve all tasks with the tid and uid given
				Cursor cursor = database.query(MySQLHelper.TABLE_LOGS,
				    allColumns, MySQLHelper.COLUMN_USERTASKUSERFID + " = " + uid + " AND " + MySQLHelper.COLUMN_USERTASKTASKFID + " = " + tid, null, null, null, null);

				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					User_Task User_Task = cursorToUser_Task(cursor);
				    User_Tasks.add(User_Task);
				    cursor.moveToNext();
				}
				// Make sure to close the cursor
				cursor.close();
				return User_Tasks;
			}
			
			public ArrayList<User_Task> getAllUserbyTaskId(long tid) {
				
				ArrayList<User_Task> User_Tasks = new ArrayList<User_Task>();
				//Retrieve all tasks with the tid and pid given
				Cursor cursor = database.query(MySQLHelper.TABLE_USER_TASK,
				    allColumns, MySQLHelper.COLUMN_USERTASKTASKFID + " = " + tid, null, null, null, null);
				

				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					User_Task User_Task = cursorToUser_Task(cursor);
				    User_Tasks.add(User_Task);
				    cursor.moveToNext();
				}
				// Make sure to close the cursor
				cursor.close();
				return User_Tasks;
			}

			private User_Task cursorToUser_Task(Cursor cursor) {
				User_Task user_task = new User_Task();
				user_task.setUTid(cursor.getLong(0));
				user_task.setUTUid(cursor.getInt(1));
				user_task.setUTTid(cursor.getInt(2));
				Date lu = Date.valueOf(cursor.getString(3));
				user_task.setUTLastUpdate(lu);
				return user_task;
			}
}
