package com.example.contactaniserapp;

public class Task {
	private long tid;
	private long taskPid;
	private String taskName;
	private String description;
	private String creator;
	private String dueDate;
	private int importanceLevel;
	
	//Will be used by array adapter in the list view
	@Override
	public String toString() {
		return taskName;
	}
	
	public long getTid() {
		return tid;
	}
	
	public void setTid(long tid) {
		this.tid = tid;
	}
	
	public long gettaskPid() {
		return taskPid;
	}
	
	public void setPid(long taskPid) {
		this.taskPid = taskPid;
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
	
	public String getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public int getImportanceLevel() {
		return importanceLevel;
	}
	
	public void setImportanceLevel(int importanceLevel) {
		this.importanceLevel = importanceLevel;
	}

	
}
