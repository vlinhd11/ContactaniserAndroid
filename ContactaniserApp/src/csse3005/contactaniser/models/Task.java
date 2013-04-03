package csse3005.contactaniser.models;

public class Task {
	private int taskid;
	private int taskProjectid;
	private String taskName;
	private String taskdescription;
	private int taskimportancelevel;
	private String taskdueDate;
	private String taskcompletion;
	
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
	
	public String getTaskDueDate() {
		return taskdueDate;
	}
	
	public void setTaskDueDate(String taskdueDate) {
		this.taskdueDate = taskdueDate;
	}
	
	public String getTaskCompletion() {
		return taskcompletion;
	}
	
	public void setTaskCompletion(String taskcompletion) {
		this.taskcompletion = taskcompletion;
	}

	
}
