package com.example.contactaniserapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProjectDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLHelper dbHelper;
	private String[] allColumns = { MySQLHelper.COLUMN_PID, 
			MySQLHelper.COLUMN_STARTDATE,
			MySQLHelper.COLUMN_PROJECTDUEDATE,};

	public ProjectDataSource(Context context) {
		dbHelper = new MySQLHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}

	//TODO add create project
	
	public void deleteProject(Project project) {
		String id = project.getPid();
		database.delete(MySQLHelper.TABLE_PROJECTS, MySQLHelper.COLUMN_PID
		    + " = " + id, null);
	}


	public List<Project> getAllProjects(String pid) {
		List<Project> projects = new ArrayList<Project>();

		//Retrieve all project
		Cursor cursor = database.query(MySQLHelper.TABLE_PROJECTS,
		    allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Project project = cursorToProject(cursor);
		    projects.add(project);
		    cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return projects;
	}

	private Project cursorToProject(Cursor cursor) {
		Project project = new Project();
		project.setPid(cursor.getString(0));
		project.setStartDate(cursor.getString(1));
		project.setDueDate(cursor.getString(2));	
		return project;
	}
}
