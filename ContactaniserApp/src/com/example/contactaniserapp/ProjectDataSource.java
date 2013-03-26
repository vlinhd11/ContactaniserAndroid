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
	private String[] allColumns = { MySQLHelper.COLUMN_PROJECTID, 
			MySQLHelper.COLUMN_PROJECTNAME,
			MySQLHelper.COLUMN_PROJECTDESCRIPTION,
			MySQLHelper.COLUMN_PROJECTSTARTDATE,
			MySQLHelper.COLUMN_PROJECTDUEDATE};

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
	
	public Project createProject(String name, String description, String startdate, String duedate) {
		ContentValues values = new ContentValues();
		values.put(MySQLHelper.COLUMN_PROJECTNAME, name);
		values.put(MySQLHelper.COLUMN_PROJECTDESCRIPTION, description);
		values.put(MySQLHelper.COLUMN_PROJECTSTARTDATE, startdate);
		values.put(MySQLHelper.COLUMN_PROJECTDUEDATE, duedate);
		
		
	    long insertId = database.insert(MySQLHelper.TABLE_PROJECTS, null,
	        values);
	    Cursor cursor = database.query(MySQLHelper.TABLE_PROJECTS,
	        allColumns, MySQLHelper.COLUMN_PROJECTID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Project newProject = cursorToProject(cursor);
	    cursor.close();
	    return newProject;
	}
	
	public void deleteProject(Project project) {
		long id = project.getProjectid();
		database.delete(MySQLHelper.TABLE_PROJECTS, MySQLHelper.COLUMN_PROJECTID
		    + " = " + id, null);
	}


	public List<Project> getAllProjects() {
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
		project.setProjectid(cursor.getInt(0));
		project.setProjectName(cursor.getString(1));
		project.setProjectDescription(cursor.getString(2));
		project.setProjectStartDate(cursor.getString(3));
		project.setProjectDueDate(cursor.getString(4));	
		return project;
	}
	
	public Project updateProject(long rowId, String name, String description, String startdate, String duedate) {
		ContentValues values = new ContentValues();
		values.put(MySQLHelper.COLUMN_PROJECTNAME, name);
		values.put(MySQLHelper.COLUMN_PROJECTDESCRIPTION, description);
		values.put(MySQLHelper.COLUMN_PROJECTSTARTDATE, startdate);
		values.put(MySQLHelper.COLUMN_PROJECTDUEDATE, duedate);
		
	    long insertId = database.update(MySQLHelper.TABLE_PROJECTS, values, MySQLHelper.COLUMN_PROJECTID + "=" + rowId,null);
	    Cursor cursor = database.query(MySQLHelper.TABLE_PROJECTS,
	        allColumns, MySQLHelper.COLUMN_PROJECTID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Project newProject = cursorToProject(cursor);
	    cursor.close();
	    return newProject;
    }
}
