package csse3005.contactaniser.connection;

import java.sql.Timestamp;

/**
 *	Class for holding the last updated timestamp
 */
public class LastUpdated {
	private long id;
	private Timestamp timeStamp;
	
	public long getId() {
		return id;
	}
	
	public void setID(long id) {
		this.id = id;
	}
	
	public Timestamp setTimestamp() {
		return timeStamp;
	}
	
	public void setTimestamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public String toString() {
		return timeStamp.toString();
	}
}
