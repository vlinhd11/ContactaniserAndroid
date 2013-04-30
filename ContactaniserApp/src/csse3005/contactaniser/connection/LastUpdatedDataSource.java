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
//	private String[] allColumns = { MySQLHelper.COLUMN_ID,
//			MySQLHelper.COLUMN_LASTUPDATED };

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
//		values.put(MySQLHelper.COLUMN_LASTUPDATED, ts.toString());
//		long insertID = database.insert(MySQLHelper.TABLE_LASTUPDATED, null,
//				values);
//		Cursor cursor = database.query(MySQLHelper.TABLE_LASTUPDATED,
//				allColumns, MySQLHelper.COLUMN_ID + " = " + insertID, null,
//				null, null, null);
//		cursor.moveToFirst();
//		LastUpdated lastUpdated = cursorToLastUpdated(cursor);
//		cursor.close();
		return null;
	}

	/**
	 * get the last time phone synced
	 */
	public LastUpdated getLatest() {
//		Cursor cursor = database.rawQuery("SELECT * FROM "
//				+ MySQLHelper.TABLE_LASTUPDATED + " WHERE "
//				+ MySQLHelper.COLUMN_ID + " = (SELECT MAX("
//				+ MySQLHelper.COLUMN_ID + ") from "
//				+ MySQLHelper.TABLE_LASTUPDATED + ")", null);
//		cursor.moveToFirst();
//		LastUpdated lastUpdated = cursorToLastUpdated(cursor);
//		cursor.close();
		return null;
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
