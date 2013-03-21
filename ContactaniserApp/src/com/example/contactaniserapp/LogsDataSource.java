package com.example.contactaniserapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LogsDataSource {

	// Database fields
		private SQLiteDatabase database;
		private MySQLHelper dbHelper;
		private String[] allColumns = { MySQLHelper.COLUMN_LOGID, 
				MySQLHelper.COLUMN_LOGTASKFID,
				MySQLHelper.COLUMN_LOGUSERFID,
				MySQLHelper.COLUMN_LOGDATETIME,
				MySQLHelper.COLUMN_LOGDESCRIPTION};
		
		

		public LogsDataSource(Context context) {
			dbHelper = new MySQLHelper(context);
		}
		
		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}
		
		public void close() {
			dbHelper.close();
		}
		
		public Logs createLogs(String logs) {
			ContentValues values = new ContentValues();
			values.put(MySQLHelper.COLUMN_LOGDESCRIPTION,logs);
		    long insertId = database.insert(MySQLHelper.TABLE_LOGS, null,
		        values);
		    Cursor cursor = database.query(MySQLHelper.TABLE_LOGS,
		        allColumns, MySQLHelper.COLUMN_LOGID + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Logs newLogs = cursorToLogs(cursor);
		    cursor.close();
		    return newLogs;
		}

		public void deleteLogs(Logs logs) {
			long id = logs.getLogid();
			database.delete(MySQLHelper.TABLE_LOGS, MySQLHelper.COLUMN_LOGID
			    + " = " + id, null);
		}


		public List<Logs> getAllLogs(String tid, String uid) {
			List<Logs> logss = new ArrayList<Logs>();

			//Retrieve all tasks with the tid and uid given
			Cursor cursor = database.query(MySQLHelper.TABLE_LOGS,
			    allColumns, MySQLHelper.COLUMN_LOGTASKFID + " = " + tid + " AND " + MySQLHelper.COLUMN_LOGUSERFID + " = " + uid, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Logs logs = cursorToLogs(cursor);
			    logss.add(logs);
			    cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return logss;
		}

		private Logs cursorToLogs(Cursor cursor) {
			Logs logs = new Logs();
			logs.setLogid(cursor.getInt(0));
			logs.setLogTaskid(cursor.getInt(1));
			logs.setLogUserid(cursor.getInt(2));
			logs.setLogDateTime(cursor.getString(3));
			logs.setLogDescription(cursor.getString(4));
		
			return logs;
		}
}
