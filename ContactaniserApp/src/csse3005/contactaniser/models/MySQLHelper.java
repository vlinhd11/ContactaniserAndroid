package csse3005.contactaniser.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLHelper extends SQLiteOpenHelper {
	
	//Project
	public static final String TABLE_PROJECTS = "projects";
	public static final String COLUMN_PROJECTID = "projectid";
	public static final String COLUMN_PROJECTNAME ="pname";
	public static final String COLUMN_PROJECTDESCRIPTION = "pdescription";
	public static final String COLUMN_PROJECTSTARTDATE = "startdate";
	public static final String COLUMN_PROJECTDUEDATE = "duedate";
	public static final String COLUMN_PROJECTCOMPLETION = "projectcompletion";
	public static final String COLUMN_PROJECTLASTUPDATE = "projectlastupdate";
	
	//Task
	public static final String TABLE_TASKS = "tasks";
	public static final String COLUMN_TASKID = "taskid";
	public static final String COLUMN_TASKPROJECTFID = "taskprojectfid";
	public static final String COLUMN_TASKNAME = "taskname";
	public static final String COLUMN_TASKDESCRIPTION = "taskdescription";
	public static final String COLUMN_TASKIMPORTANCELEVEL = "taskimportancelevel";
	public static final String COLUMN_TASKDUEDATE = "taskduedate";
	public static final String COLUMN_TASKCOMPLETION = "taskcompletion";
	public static final String COLUMN_TASKLASTUPDATE = "tasklastupdate";
	public static final String COLUMN_TASKCATEGORY = "taskcategory";
	//public static final String COLUMN_TASKOWNERID = "taskowner";
	
	//User
	public static final String TABLE_USER = "user";
	public static final String COLUMN_USERID ="userid";
	public static final String COLUMN_USER_USERNAME ="user_username";
	public static final String COLUMN_USERNAME ="username";
	public static final String COLUMN_USERPHONENUMBER ="userphonenumber";
	public static final String COLUMN_USEREMAIL ="useremail";
	public static final String COLUMN_USERLASTUPDATE = "userlastupdate";
	
	//Logs
	public static final String TABLE_LOGS = "logs";
	public static final String COLUMN_LOGID ="logid";
	public static final String COLUMN_LOGTASKFID ="logtaskfid";
	public static final String COLUMN_LOGUSERFID ="loguserfid";
	public static final String COLUMN_LOGDATETIME ="logdatetime";
	public static final String COLUMN_LOGDESCRIPTION ="logdescription";
	public static final String COLUMN_LOGLASTUPDATE = "loglastupdate";
	
	//User Project
	public static final String TABLE_USER_PROJECT ="user_project";
	public static final String COLUMN_USERPROJECTID = "userprojectid";
	public static final String COLUMN_USERPROJECTUSERFID = "userprojectuserfid";
	public static final String COLUMN_USERPROJECTPROJECTFID = "userprojectprojectfid";
	public static final String COLUMN_ROLE = "userprojectrole";
	public static final String COLUMN_USERPROJECTLASTUPDATE = "userprojectlastupdate";
	
	//User Task
	public static final String TABLE_USER_TASK = "user_task";
	public static final String COLUMN_USERTASKID = "usertaskid";
	public static final String COLUMN_USERTASKUSERFID = "usertaskuserfid";
	public static final String COLUMN_USERTASKTASKFID = "usertasktaskfid";
	public static final String COLUMN_USERTASKLASTUPDATE = "usertasklastupdate";	
	private static final String DATABASE_NAME = "commments.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	
	private static final String DATABASE_CREATE_PROJECTS = "create table "
			+ TABLE_PROJECTS + "("
	  		+ COLUMN_PROJECTID + " integer primary key autoincrement, "
			+ COLUMN_PROJECTNAME + " text not null, " 
			+ COLUMN_PROJECTDESCRIPTION + " text not null, "
			+ COLUMN_PROJECTSTARTDATE + " text not null, "
			+ COLUMN_PROJECTDUEDATE + " text not null, "
			+ COLUMN_PROJECTCOMPLETION + " integer not null, "
			+ COLUMN_PROJECTLASTUPDATE + " text not null);";
	
	private static final String DATABASE_CREATE_TASKS = "create table "
			+ TABLE_TASKS + "(" + COLUMN_TASKID + " integer primary key autoincrement, "
			+ COLUMN_TASKPROJECTFID + " integer, " 
			+ COLUMN_TASKNAME + " text not null, " 
			+ COLUMN_TASKDESCRIPTION + " text not null, "
			+ COLUMN_TASKIMPORTANCELEVEL + " integer not null, "
			+ COLUMN_TASKDUEDATE + " text not null, " 
			+ COLUMN_TASKCOMPLETION + " integer not null, " 
			+ COLUMN_TASKLASTUPDATE + " text not null, "
			+ COLUMN_TASKCATEGORY + " integer not null);";
	
	private static final String DATABASE_CREATE_USER = "create table "
			+ TABLE_USER + "(" + COLUMN_USERID + " integer primary key autoincrement, "
			+ COLUMN_USER_USERNAME + " text not null, " 
			+ COLUMN_USERNAME + " text not null, " 
			+ COLUMN_USERPHONENUMBER + " text not null, "
			+ COLUMN_USEREMAIL + " text not null, " 
			+ COLUMN_USERLASTUPDATE + " text not null);";
	
	private static final String DATABASE_CREATE_LOGS = "create table "
			+ TABLE_LOGS + "(" + COLUMN_LOGID + " integer primary key autoincrement, "
			+ COLUMN_LOGTASKFID + " integer not null, " 
			+ COLUMN_LOGUSERFID + " integer not null, " 
			+ COLUMN_LOGDATETIME + " text not null, "
			+ COLUMN_LOGDESCRIPTION + " text not null, " 
			+ COLUMN_LOGLASTUPDATE + "text not null);";
	
	private static final String DATABASE_CREATE_USER_PROJECT = "create table "
			+ TABLE_USER_PROJECT + "("  + COLUMN_USERPROJECTID + " integer primary key autoincrement, "
			+ COLUMN_USERPROJECTUSERFID + " integer, " 
			+ COLUMN_USERPROJECTPROJECTFID + " integer not null, " 
			+ COLUMN_ROLE + " string not null, " 
			+ COLUMN_USERPROJECTLASTUPDATE + " text not null);";
	
	private static final String DATABASE_CREATE_USER_TASK = "create table "
			+ TABLE_USER_TASK + "("+  COLUMN_USERTASKID + " integer primary key autoincrement, "
			+ COLUMN_USERTASKUSERFID + " integer not null, "
			+ COLUMN_USERTASKTASKFID + " integer not null, "
			+ COLUMN_USERTASKLASTUPDATE + " text not null);";
				
	
	
	public MySQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_PROJECTS);
		database.execSQL(DATABASE_CREATE_TASKS);
		database.execSQL(DATABASE_CREATE_USER);
		database.execSQL(DATABASE_CREATE_LOGS);
		database.execSQL(DATABASE_CREATE_USER_PROJECT);
		database.execSQL(DATABASE_CREATE_USER_TASK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROJECT);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_TASK);
	    onCreate(db);
	}
}
