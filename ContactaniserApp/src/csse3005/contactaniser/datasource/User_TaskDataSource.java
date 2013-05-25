package csse3005.contactaniser.datasource;
import java.sql.Date;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.User_Task;

public class User_TaskDataSource {

	// Database fields
			private SQLiteDatabase database;
			private MySQLHelper dbHelper;
			private String[] allColumns = { MySQLHelper.COLUMN_USERTASKID,
					MySQLHelper.COLUMN_USERTASKUSERFID, 
					MySQLHelper.COLUMN_USERTASKTASKFID, MySQLHelper.COLUMN_USERTASKLASTUPDATE, MySQLHelper.COLUMN_USERTASKSTATUS};
			

			public User_TaskDataSource(Context context) {
				dbHelper = new MySQLHelper(context);
			}
			
			public void open() throws SQLException {
				database = dbHelper.getWritableDatabase();
			}
			
			public void close() {
				dbHelper.close();
			}
			
			public User_Task createUser_Task(String usertaskid, long user_task, long task_task, Date lastupdate, int status) {
				ContentValues values = new ContentValues();
				values.put(MySQLHelper.COLUMN_USERTASKUSERFID,user_task);
				values.put(MySQLHelper.COLUMN_USERTASKTASKFID, task_task);
				values.put(MySQLHelper.COLUMN_USERTASKLASTUPDATE, lastupdate.toString());
				values.put(MySQLHelper.COLUMN_USERTASKSTATUS, status);
				
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
			
			public void deleteUserTaskbyUserIdTaskId(long uid, long tid)
			{
				database.delete(MySQLHelper.TABLE_USER_TASK, MySQLHelper.COLUMN_USERTASKUSERFID
						+ " = " + uid + " AND " + MySQLHelper.COLUMN_USERTASKTASKFID + " = " + tid, null);
			}


			public ArrayList<User_Task> getAllUser_Task() {
				ArrayList<User_Task> User_Tasks = new ArrayList<User_Task>();

				//Retrieve all tasks with the tid and uid given
				Cursor cursor = database.query(MySQLHelper.TABLE_USER_TASK,
				    allColumns, null, null, null, null, null);

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
				    allColumns, MySQLHelper.COLUMN_USERTASKTASKFID + " = " + tid + " AND " + MySQLHelper.COLUMN_USERTASKSTATUS + " =0", null, null, null, null);
				

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
			
				public ArrayList<User_Task> getAllTaskbyUserId(long uid) {
				
				ArrayList<User_Task> User_Tasks = new ArrayList<User_Task>();
				//Retrieve all tasks with the tid and pid given
				Cursor cursor = database.query(MySQLHelper.TABLE_USER_TASK,
				    allColumns, MySQLHelper.COLUMN_USERTASKSTATUS + " = " + uid + " AND " + MySQLHelper.COLUMN_USERTASKSTATUS + " =0", null, null, null, null);
				

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
				user_task.setUTUid(cursor.getLong(1));
				user_task.setUTTid(cursor.getLong(2));
				Date lu = Date.valueOf(cursor.getString(3));
				user_task.setUTLastUpdate(lu);
				user_task.setUTStatus(cursor.getInt(4));
				return user_task;
			}
			
			public Cursor fetchUserTaskByUserIdTaskId(long taskid,long userid) throws SQLException {

		        Cursor mCursor =

		            database.query(true,MySQLHelper.TABLE_USER_TASK , allColumns, MySQLHelper.COLUMN_USERTASKTASKFID + "=" + taskid
		            		+ " AND " + MySQLHelper.COLUMN_USERTASKUSERFID + "=" + userid , null,
		                    null, null, null, null);
		        if (mCursor != null) {
		            mCursor.moveToFirst();
		        }
		        return mCursor;

		    }
}
