package csse3005.contactaniser.models;

import java.sql.Date;

public class Task {
	private int taskid;
	private int taskProjectid;
	private String taskName;
	private String taskdescription;
	private int taskimportancelevel;
	private Date taskdueDate;
	private Date taskcompletion;
	private Date tasklastupdate;
	
	//Will be used by array adapter in the list view
	@Override
	public String toString() {
		return taskName;
	}
	
	public long getTaskid() {
		return taskid;
	}
	
	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}
	
	public long getTaskProjectid() {
		return taskProjectid;
	}
	
	public void setTaskProjectid(int taskProjectid) {
		this.taskProjectid = taskProjectid;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskDescription() {
		return taskdescription;
	}
	
	public void setTaskDescription(String taskdescription) {
		this.taskdescription = taskdescription;
	}
	
	public int getTaskImportanceLevel() {
		return taskimportancelevel;
	}
	
	public void setTaskImportanceLevel(int taskimportancelevel){
		this.taskimportancelevel = taskimportancelevel;
	}
	
	public Date getTaskDueDate() {
		return taskdueDate;
	}
	
	public void setTaskDueDate(Date taskdueDate) {
		this.taskdueDate = taskdueDate;
	}
	
	public Date getTaskCompletion() {
		return taskcompletion;
	}
	
	public void setTaskCompletion(Date taskcompletion) {
		this.taskcompletion = taskcompletion;
	}
	
	public Date getTaskLastUpdate() {
		return tasklastupdate;
	}
	
	public void setTaskLastUpdate(Date tasklastupdate) {
		this.tasklastupdate = tasklastupdate;
	}

	
}
