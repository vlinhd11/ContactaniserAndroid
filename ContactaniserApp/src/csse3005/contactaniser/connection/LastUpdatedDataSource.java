package csse3005.contactaniser.connection;

import java.sql.Timestamp;

import csse3005.contactaniser.models.MySQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * DAO for LastUpdated
 */
public class LastUpdatedDataSource {
	// Database fields
	private SQLiteDatabase database;
	private MySQLHelper dbHelper;
	private String[] allColumns = { MySQLHelper.COLUMN_USERID,
			MySQLHelper.COLUMN_USERPROJECTLASTUPDATE };

	public LastUpdatedDataSource(Context context) {
		dbHelper = new MySQLHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * create and insert a LastUpdate based on a timestamp into the database
	 */
	public LastUpdated createLastUpdated(Timestamp ts) {
		ContentValues values = new ContentValues();
		values.put(MySQLHelper.COLUMN_USERPROJECTLASTUPDATE, ts.toString());
		long insertID = database.insert(MySQLHelper.COLUMN_PROJECTLASTUPDATE, null,
				values);
		Cursor cursor = database.query(MySQLHelper.COLUMN_PROJECTLASTUPDATE,
				allColumns, MySQLHelper.COLUMN_LOGID + " = " + insertID, null,
				null, null, null);
		cursor.moveToFirst();
		LastUpdated lastUpdated = cursorToLastUpdated(cursor);
		cursor.close();
		return lastUpdated;
	}

	/**
	 * get the last time phone synced
	 */
	public LastUpdated getLatest() {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ MySQLHelper.COLUMN_PROJECTLASTUPDATE + " WHERE "
				+ MySQLHelper.COLUMN_LOGID + " = (SELECT MAX("
				+ MySQLHelper.COLUMN_LOGID + ") from "
				+ MySQLHelper.COLUMN_PROJECTLASTUPDATE + ")", null);
		cursor.moveToFirst();
		LastUpdated lastUpdated = cursorToLastUpdated(cursor);
		cursor.close();
		return lastUpdated;
	}
	
	/**
	 * turn the cursor location into the a LastUpdated
	 */
	private LastUpdated cursorToLastUpdated(Cursor cursor) {
		LastUpdated lastUpdated = new LastUpdated();
		lastUpdated.setID(cursor.getLong(0));
		
		Timestamp ts = Timestamp.valueOf(cursor.getString(1));
		
		lastUpdated.setTimestamp(ts);
		return lastUpdated;
	}

}
