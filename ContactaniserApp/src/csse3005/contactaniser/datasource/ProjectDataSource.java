package csse3005.contactaniser.datasource;

import csse3005.contactaniser.models.MySQLHelper;
import csse3005.contactaniser.models.Project;

import java.util.ArrayList;
import java.sql.Date;
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
			MySQLHelper.COLUMN_PROJECTDUEDATE,
			MySQLHelper.COLUMN_PROJECTCOMPLETION,
			MySQLHelper.COLUMN_PROJECTLASTUPDATE};

	public ProjectDataSource(Context context) {
		dbHelper = new MySQLHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}

	
	public Project createProject(String id, String name, String description, Date startdate, Date duedate,String completion, Date lastupdate) {
		ContentValues values = new ContentValues();
		
		values.put(MySQLHelper.COLUMN_PROJECTNAME, name);
		values.put(MySQLHelper.COLUMN_PROJECTDESCRIPTION, description);
		values.put(MySQLHelper.COLUMN_PROJECTSTARTDATE, startdate.toString());
		values.put(MySQLHelper.COLUMN_PROJECTDUEDATE, duedate.toString());
		values.put(MySQLHelper.COLUMN_PROJECTCOMPLETION, completion);
		values.put(MySQLHelper.COLUMN_PROJECTLASTUPDATE, lastupdate.toString());
		
		String[] str = {id};
		
		// update the location row where locationid = input
				int affectedRows = database.update(MySQLHelper.TABLE_PROJECTS,
						values, MySQLHelper.COLUMN_PROJECTID + " = ?",
						str);
				
				
				if (affectedRows == 1) {
					// if 1 row is affected then it existed before so get it
					Cursor cursor = database.query(MySQLHelper.TABLE_PROJECTS,
					        allColumns, MySQLHelper.COLUMN_PROJECTID + " = " + id, null,
					        null, null, null);
					    cursor.moveToFirst();
					    Project newProject = cursorToProject(cursor);
					    cursor.close();
					    return newProject;
				}
				else
				{
				values.put(MySQLHelper.COLUMN_PROJECTID, id);
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
	}
	
	public void deleteProject(Project project) {
		long id = project.getProjectid();
		database.delete(MySQLHelper.TABLE_PROJECTS, MySQLHelper.COLUMN_PROJECTID
		    + " = " + id, null);
	}


	public List<Project> getAllProjects(int completion) {
		List<Project> projects = new ArrayList<Project>();

		//Retrieve all project
		Cursor cursor = database.query(MySQLHelper.TABLE_PROJECTS,
		    allColumns, MySQLHelper.COLUMN_PROJECTCOMPLETION + " = " + completion, null, null, null, null);
		

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
		Date sd = Date.valueOf(cursor.getString(3));
		Date dd = Date.valueOf(cursor.getString(4));
		project.setProjectDueDate(sd);
		project.setProjectStartDate(dd);
		project.setProjectCompletion(cursor.getString(5));
		Date lu = Date.valueOf(cursor.getString(6));
	    project.setProjectLastUpdate(lu);
		return project;
	}
	
	public Cursor fetchProjectById(long rowId) throws SQLException {

        Cursor mCursor =

            database.query(true,MySQLHelper.TABLE_PROJECTS , allColumns, MySQLHelper.COLUMN_PROJECTID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
}
