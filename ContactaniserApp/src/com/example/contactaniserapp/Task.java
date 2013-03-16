package com.example.contactaniserapp;

import java.util.Date;

public class Task {
	private int tid;
	private int pid;
	private String taskName;
	private String description;
	private String creator;
	private Date dueDate;
	private int importanceLevel;
	
	//Will be used by array adapter in the list view
	@Override
	public String toString() {
		return taskName;
	}
	
	public int getTid() {
		return tid;
	}
	
	public void setTid(int tid) {
		this.tid = tid;
	}
	
	public int getPid() {
		return pid;
	}
	
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public int getImportanceLevel() {
		return importanceLevel;
	}
	
	public void setImportanceLevel(int importanceLevel) {
		this.importanceLevel = importanceLevel;
	}

	
}
