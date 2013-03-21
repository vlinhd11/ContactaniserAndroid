package com.example.contactaniserapp;

public class Logs {

	private int logid;
	private int logtaskid;
	private int loguserid;
	private String logdatetime;
	private String logdescription;
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
	
	public String getLogDateTime() {
		return logdatetime;
	}
	
	public void setLogDateTime(String logdatetime) {
		this.logdatetime = logdatetime;
	}
	
	public String getLogDescription() {
		return logdescription;
	}
	
	public void setLogDescription(String logdescription){
		this.logdescription = logdescription;
	}
	
}
