package csse3005.contactaniser.datasource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.Task;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TaskDataSource { 
	// Database fields
	private SQLiteDatabase database;
	private MySQLHelper dbHelper;
	private String[] allColumns = { MySQLHelper.COLUMN_TASKID, 
			MySQLHelper.COLUMN_TASKPROJECTFID,
			MySQLHelper.COLUMN_TASKNAME,
			MySQLHelper.COLUMN_TASKDESCRIPTION,
			MySQLHelper.COLUMN_TASKIMPORTANCELEVEL,
			MySQLHelper.COLUMN_TASKDUEDATE,
			MySQLHelper.COLUMN_TASKLASTUPDATE};

	public TaskDataSource(Context context) {
		dbHelper = new MySQLHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Task createTask(int projectfid, String name, String description, int importancelevel, Date duedate, Date completion, Date lastupdate) {
		ContentValues values = new ContentValues();
		values.put(MySQLHelper.COLUMN_TASKPROJECTFID, projectfid);
		values.put(MySQLHelper.COLUMN_TASKNAME, name);
		values.put(MySQLHelper.COLUMN_TASKDESCRIPTION, description);
		values.put(MySQLHelper.COLUMN_TASKIMPORTANCELEVEL, importancelevel);
		values.put(MySQLHelper.COLUMN_TASKDUEDATE, duedate.toString());
		values.put(MySQLHelper.COLUMN_TASKCOMPLETION, completion.toString());
		values.put(MySQLHelper.COLUMN_TASKLASTUPDATE, lastupdate.toString());
	    long insertId = database.insert(MySQLHelper.TABLE_TASKS, null,
	        values);
	    Cursor cursor = database.query(MySQLHelper.TABLE_TASKS,
	        allColumns, MySQLHelper.COLUMN_TASKID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Task newTask = cursorToTask(cursor);
	    cursor.close();
	    return newTask;
	}

	public void deleteTask(Task task) {
		long id = task.getTaskid();
		database.delete(MySQLHelper.TABLE_TASKS, MySQLHelper.COLUMN_TASKID
		    + " = " + id, null);
	}


	public List<Task> getAllTasks(String pid) {
		List<Task> tasks = new ArrayList<Task>();

		//Retrieve all tasks with the pid given
		Cursor cursor = database.query(MySQLHelper.TABLE_TASKS,
		    allColumns, MySQLHelper.COLUMN_TASKPROJECTFID + " = " + pid, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Task task = cursorToTask(cursor);
		    tasks.add(task);
		    cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return tasks;
	}

	private Task cursorToTask(Cursor cursor) {
		Task task = new Task();
		task.setTaskid(cursor.getInt(0));
		task.setTaskProjectid(cursor.getInt(1));
		task.setTaskName(cursor.getString(2));
		task.setTaskDescription(cursor.getString(3));
		task.setTaskImportanceLevel(cursor.getInt(4));
		Date dd = Date.valueOf(cursor.getString(5));
		task.setTaskCompletion(cursor.getInt(6));
		Date lu = Date.valueOf(cursor.getString(7));
		task.setTaskDueDate(dd);
		
		task.setTaskLastUpdate(lu);
		
		return task;
	}
	
	public Task updateTask(long rowId, int projectfid, String name, String description, int importancelevel, Date duedate, int completion, Date lastupdate) {
		ContentValues values = new ContentValues();
		values.put(MySQLHelper.COLUMN_TASKPROJECTFID, projectfid);
		values.put(MySQLHelper.COLUMN_TASKNAME, name);
		values.put(MySQLHelper.COLUMN_TASKDESCRIPTION, description);
		values.put(MySQLHelper.COLUMN_TASKIMPORTANCELEVEL, importancelevel);
		values.put(MySQLHelper.COLUMN_TASKDUEDATE, duedate.toString());
		values.put(MySQLHelper.COLUMN_TASKCOMPLETION, completion);
		values.put(MySQLHelper.COLUMN_TASKLASTUPDATE, lastupdate.toString());
		long insertId = database.update(MySQLHelper.TABLE_TASKS, values, MySQLHelper.COLUMN_TASKID + "=" + rowId,null);
	    Cursor cursor = database.query(MySQLHelper.TABLE_TASKS,
	        allColumns, MySQLHelper.COLUMN_TASKID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Task newTask = cursorToTask(cursor);
	    cursor.close();
	    return newTask;
    }
	
}
