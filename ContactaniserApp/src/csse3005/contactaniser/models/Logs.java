package csse3005.contactaniser.models;

import java.sql.Date;

public class Logs {

	private int logid;
	private int logtaskid;
	private int loguserid;
	private Date logdatetime;
	private String logdescription;
	private Date loglastupdate;
	//Will be used by array adapter in the list view
	@Override
	public String toString() {
		return logdescription;
	}
	
	public long getLogid() {
		return logid;
	}
	
	public void setLogid(int logid) {
		this.logid = logid;
	}
	
	public long getLogTaskid() {
		return logtaskid;
	}
	
	public void setLogTaskid(int logtaskid) {
		this.logtaskid = logtaskid;
	}
	
	public int getLogUserid() {
		return loguserid;
	}
	
	public void setLogUserid(int loguserid) {
		this.loguserid = loguserid;
	}
	
	public Date getLogDateTime() {
		return logdatetime;
	}
	
	public void setLogDateTime(Date logdatetime) {
		this.logdatetime = logdatetime;
	}
	
	public String getLogDescription() {
		return logdescription;
	}
	
	public void setLogDescription(String logdescription){
		this.logdescription = logdescription;
	}
	
	public Date getLogLastUpdate() {
		return loglastupdate;
	}
	
	public void setLogLastUpdate(Date loglastupdate) {
		this.loglastupdate = loglastupdate;
	}
	
}
