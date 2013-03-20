package com.example.contactaniserapp;

public class Project {
	private String pid;
	private String startDate;
	private String dueDate;
	
	//Will be used by array adapter in the list view
	@Override
	public String toString() {
		return pid;
	}
	
	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

}
