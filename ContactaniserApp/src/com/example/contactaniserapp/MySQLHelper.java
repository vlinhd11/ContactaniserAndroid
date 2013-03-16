package com.example.contactaniserapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLHelper extends SQLiteOpenHelper {

	  public static final String TABLE_TASKS = "tasks";
	  public static final String COLUMN_ID = "_tid";
	  //TODO Foreign key pid
	  //public static final String COLUMN_PID = "pid";
	  public static final String COLUMN_TASKNAME = "taskname";
	  public static final String COLUMN_DESCRIPTION = "description";
	  public static final String COLUMN_CREATOR = "creator";
	  public static final String COLUMN_DUEDATE = "duedate";
	  public static final String COLUMN_IMPORTANCELEVEL = "importancelevel";
	
	  private static final String DATABASE_NAME = "commments.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_TASKS + "(" + COLUMN_ID + " integer primary key autoincrement, "
			  + COLUMN_TASKNAME + " text not null, " 
			  + COLUMN_DESCRIPTION + "text not null, "
			  + COLUMN_CREATOR + "text not null, "
			  + COLUMN_DUEDATE + "text not null, "
			  + COLUMN_IMPORTANCELEVEL + "text not null, "
	  		  + COLUMN_IMPORTANCELEVEL + "text not null);";

	  public MySQLHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
	    onCreate(db);
	  }
}
