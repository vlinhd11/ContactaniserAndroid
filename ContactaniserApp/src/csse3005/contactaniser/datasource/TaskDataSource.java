package csse3005.contactaniser.datasource;
import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.Task;

import java.util.ArrayList;
import java.sql.Date;

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
			MySQLHelper.COLUMN_TASKCOMPLETION,
			MySQLHelper.COLUMN_TASKLASTUPDATE,
			MySQLHelper.COLUMN_TASKCATEGORY};

	public TaskDataSource(Context context) {
		dbHelper = new MySQLHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Task createTask(String taskid, long projectfid, String name, String description, int importancelevel, Date duedate, int completion, Date lastupdate , int category) {
		ContentValues values = new ContentValues();
		
		values.put(MySQLHelper.COLUMN_TASKPROJECTFID, projectfid);
		values.put(MySQLHelper.COLUMN_TASKNAME, name);
		values.put(MySQLHelper.COLUMN_TASKDESCRIPTION, description);
		values.put(MySQLHelper.COLUMN_TASKIMPORTANCELEVEL, importancelevel);
		values.put(MySQLHelper.COLUMN_TASKDUEDATE, duedate.toString());
		values.put(MySQLHelper.COLUMN_TASKCOMPLETION, completion);
		values.put(MySQLHelper.COLUMN_TASKLASTUPDATE, lastupdate.toString());
		values.put(MySQLHelper.COLUMN_TASKCATEGORY, category);
		
		String[] str = {taskid};
		int affectedRows = database.update(MySQLHelper.TABLE_TASKS,
				values, MySQLHelper.COLUMN_TASKID + " = ?",
				str);
		
		if (affectedRows == 1) {
			Cursor cursor = database.query(MySQLHelper.TABLE_TASKS,
			        allColumns, MySQLHelper.COLUMN_TASKID + " = " + taskid, null,
			        null, null, null);
			    cursor.moveToFirst();
			    Task newTask = cursorToTask(cursor);
			    cursor.close();
			    return newTask;
		}
		else
		{
		values.put(MySQLHelper.COLUMN_TASKID, taskid);
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
	}

	public void deleteTask(Task task) {
		long id = task.getTaskid();
		database.delete(MySQLHelper.TABLE_TASKS, MySQLHelper.COLUMN_TASKID
		    + " = " + id, null);
	}


	public ArrayList<Task> getAllTasks(long pid, int completion) {
		ArrayList<Task> tasks = new ArrayList<Task>();

		//Retrieve all tasks with the pid given
		Cursor cursor = database.query(MySQLHelper.TABLE_TASKS,
		    allColumns, MySQLHelper.COLUMN_TASKPROJECTFID + " = " + pid + " AND " + MySQLHelper.COLUMN_TASKCOMPLETION + " = " + completion, null, null, null , MySQLHelper.COLUMN_TASKDUEDATE + " ASC", null);
		
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
		task.setTaskid(cursor.getLong(0));
		task.setTaskProjectid(cursor.getLong(1));
		task.setTaskName(cursor.getString(2));
		task.setTaskDescription(cursor.getString(3));
		task.setTaskImportanceLevel(cursor.getInt(4));
		Date dd = Date.valueOf(cursor.getString(5));
		task.setTaskCompletion(cursor.getString(6));
		Date lu = Date.valueOf(cursor.getString(7));
		task.setTaskDueDate(dd);
		task.setTaskLastUpdate(lu);
		task.setTaskCategory(cursor.getInt(8));
		
		return task;
	}
	
	public Cursor fetchTaskById(long rowId) throws SQLException {

        Cursor mCursor =

            database.query(true,MySQLHelper.TABLE_TASKS , allColumns, MySQLHelper.COLUMN_TASKID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
	
}
