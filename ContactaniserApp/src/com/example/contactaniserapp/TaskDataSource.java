package com.example.contactaniserapp;

import java.util.ArrayList;
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
	private String[] allColumns = { MySQLHelper.COLUMN_TID, 
			MySQLHelper.COLUMN_TASKPID,
			MySQLHelper.COLUMN_TASKNAME,
			MySQLHelper.COLUMN_DESCRIPTION,
			MySQLHelper.COLUMN_CREATOR,
			MySQLHelper.COLUMN_TASKDUEDATE,
			MySQLHelper.COLUMN_IMPORTANCELEVEL};

	public TaskDataSource(Context context) {
		dbHelper = new MySQLHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Task createTask(String task) {
		ContentValues values = new ContentValues();
		values.put(MySQLHelper.COLUMN_TASKNAME,task);
	    long insertId = database.insert(MySQLHelper.TABLE_TASKS, null,
	        values);
	    Cursor cursor = database.query(MySQLHelper.TABLE_TASKS,
	        allColumns, MySQLHelper.COLUMN_TID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Task newTask = cursorToTask(cursor);
	    cursor.close();
	    return newTask;
	}

	public void deleteTask(Task task) {
		long id = task.getTid();
		database.delete(MySQLHelper.TABLE_TASKS, MySQLHelper.COLUMN_TID
		    + " = " + id, null);
	}


	public List<Task> getAllTasks(String pid) {
		List<Task> tasks = new ArrayList<Task>();

		//Retrieve all tasks with the pid given
		Cursor cursor = database.query(MySQLHelper.TABLE_TASKS,
		    allColumns, MySQLHelper.COLUMN_TASKPID + " = " + pid, null, null, null, null);

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
		task.setTid(cursor.getLong(0));
		task.setPid(cursor.getLong(1));
		task.setTaskName(cursor.getString(2));
		task.setDescription(cursor.getString(3));
		task.setCreator(cursor.getString(4));
		task.setDueDate(cursor.getString(5));
		task.setPid(cursor.getInt(6));
		return task;
	}
	
}
