package csse3005.contactaniser.models;

import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Logs.
 */
public class Logs {

	/** The logid. */
	private int logid;
	
	/** The logtaskid. */
	private int logtaskid;
	
	/** The loguserid. */
	private int loguserid;
	
	/** The logdatetime. */
	private Date logdatetime;
	
	/** The logdescription. */
	private String logdescription;
	
	/** The loglastupdate. */
	private Date loglastupdate;
	//Will be used by array adapter in the list view
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return logdescription;
	}
	
	/**
	 * Gets the logid.
	 *
	 * @return the logid
	 */
	public long getLogid() {
		return logid;
	}
	
	/**
	 * Sets the logid.
	 *
	 * @param logid the new logid
	 */
	public void setLogid(int logid) {
		this.logid = logid;
	}
	
	/**
	 * Gets the log taskid.
	 *
	 * @return the log taskid
	 */
	public long getLogTaskid() {
		return logtaskid;
	}
	
	/**
	 * Sets the log taskid.
	 *
	 * @param logtaskid the new log taskid
	 */
	public void setLogTaskid(int logtaskid) {
		this.logtaskid = logtaskid;
	}
	
	/**
	 * Gets the log userid.
	 *
	 * @return the log userid
	 */
	public int getLogUserid() {
		return loguserid;
	}
	
	/**
	 * Sets the log userid.
	 *
	 * @param loguserid the new log userid
	 */
	public void setLogUserid(int loguserid) {
		this.loguserid = loguserid;
	}
	
	/**
	 * Gets the log date time.
	 *
	 * @return the log date time
	 */
	public Date getLogDateTime() {
		return logdatetime;
	}
	
	/**
	 * Sets the log date time.
	 *
	 * @param logdatetime the new log date time
	 */
	public void setLogDateTime(Date logdatetime) {
		this.logdatetime = logdatetime;
	}
	
	/**
	 * Gets the log description.
	 *
	 * @return the log description
	 */
	public String getLogDescription() {
		return logdescription;
	}
	
	/**
	 * Sets the log description.
	 *
	 * @param logdescription the new log description
	 */
	public void setLogDescription(String logdescription){
		this.logdescription = logdescription;
	}
	
	/**
	 * Gets the log last update.
	 *
	 * @return the log last update
	 */
	public Date getLogLastUpdate() {
		return loglastupdate;
	}
	
	/**
	 * Sets the log last update.
	 *
	 * @param loglastupdate the new log last update
	 */
	public void setLogLastUpdate(Date loglastupdate) {
		this.loglastupdate = loglastupdate;
	}
	
}
