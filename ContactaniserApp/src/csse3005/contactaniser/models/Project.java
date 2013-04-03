package csse3005.contactaniser.models;

import java.sql.Date;

public class Project {
	private int pid;
	private String pName;
	private String pDescription;
	private Date startDate;
	private Date dueDate;
	private Date lastupdate;
	
	//Will be used by array adapter in the list view
	@Override
	public String toString() {
		return pName;
	}
	
	public long getProjectid() {
		return pid;
	}
	
	public void setProjectid(int pid) {
		this.pid = pid;
	}
	
	public String getProjectName(){
		return pName;
	}
	
	public void setProjectName(String pName){
		this.pName = pName;
	}
	
	public String getProjectDescription(){
		return pDescription;
	}
	
	public void setProjectDescription(String pDescription){
		this.pDescription = pDescription;
	}

	public Date getProjectStartDate() {
		return startDate;
	}
	
	public void setProjectStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getProjectDueDate() {
		return dueDate;
	}
	
	public void setProjectDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Date getProjectLastUpdate() {
		return lastupdate;
	}
	
	public void setProjectLastUpdate(Date dueDate) {
		this.lastupdate = dueDate;
	}
}
