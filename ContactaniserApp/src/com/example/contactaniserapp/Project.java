package com.example.contactaniserapp;

public class Project {
	private int pid;
	private String pName;
	private String pDescription;
	private String startDate;
	private String dueDate;
	
	
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

	public String getProjectStartDate() {
		return startDate;
	}
	
	public void setProjectStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getProjectDueDate() {
		return dueDate;
	}
	
	public void setProjectDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

}
