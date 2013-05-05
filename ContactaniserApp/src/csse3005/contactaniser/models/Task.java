package csse3005.contactaniser.models;

import java.sql.Date;

/**
 * The Class Task is used as a model for tasks retrieved from the android
 * 		database, which will be stored in an array.
 */
public class Task {
	
	/** The taskid. */
	private int taskid;
	
	/** The task projectid. */
	private int taskProjectid;
	
	/** The task name. */
	private String taskName;
	
	/** The taskdescription. */
	private String taskdescription;
	
	/** The taskimportancelevel. */
	private int taskimportancelevel;
	
	/** The taskdue date. */
	private Date taskdueDate;
	
	/** The taskcompletion. */
	private String taskcompletion;
	
	/** The tasklastupdate. */
	private Date tasklastupdate;
	
	//Will be used by array adapter in the list view
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return taskName;
	}
	
	/**
	 * Gets the taskid.
	 *
	 * @return the taskid
	 */
	public long getTaskid() {
		return taskid;
	}
	
	/**
	 * Sets the taskid.
	 *
	 * @param taskid the new taskid
	 */
	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}
	
	/**
	 * Gets the task projectid.
	 *
	 * @return the task projectid
	 */
	public long getTaskProjectid() {
		return taskProjectid;
	}
	
	/**
	 * Sets the task projectid.
	 *
	 * @param taskProjectid the new task projectid
	 */
	public void setTaskProjectid(int taskProjectid) {
		this.taskProjectid = taskProjectid;
	}
	
	/**
	 * Gets the task name.
	 *
	 * @return the task name
	 */
	public String getTaskName() {
		return taskName;
	}
	
	/**
	 * Sets the task name.
	 *
	 * @param taskName the new task name
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	/**
	 * Gets the task description.
	 *
	 * @return the task description
	 */
	public String getTaskDescription() {
		return taskdescription;
	}
	
	/**
	 * Sets the task description.
	 *
	 * @param taskdescription the new task description
	 */
	public void setTaskDescription(String taskdescription) {
		this.taskdescription = taskdescription;
	}
	
	/**
	 * Gets the task importance level.
	 *
	 * @return the task importance level
	 */
	public int getTaskImportanceLevel() {
		return taskimportancelevel;
	}
	
	/**
	 * Sets the task importance level.
	 *
	 * @param taskimportancelevel the new task importance level
	 */
	public void setTaskImportanceLevel(int taskimportancelevel){
		this.taskimportancelevel = taskimportancelevel;
	}
	
	/**
	 * Gets the task due date.
	 *
	 * @return the task due date
	 */
	public Date getTaskDueDate() {
		return taskdueDate;
	}
	
	/**
	 * Sets the task due date.
	 *
	 * @param taskdueDate the new task due date
	 */
	public void setTaskDueDate(Date taskdueDate) {
		this.taskdueDate = taskdueDate;
	}
	
	/**
	 * Gets the task completion.
	 *
	 * @return the task completion
	 */
	public String getTaskCompletion() {
		return taskcompletion;
	}
	
	/**
	 * Sets the task completion.
	 *
	 * @param taskcompletion the new task completion
	 */
	public void setTaskCompletion(String taskcompletion) {
		this.taskcompletion = taskcompletion;
	}
	
	/**
	 * Gets the task last update.
	 *
	 * @return the task last update
	 */
	public Date getTaskLastUpdate() {
		return tasklastupdate;
	}
	
	/**
	 * Sets the task last update.
	 *
	 * @param tasklastupdate the new task last update
	 */
	public void setTaskLastUpdate(Date tasklastupdate) {
		this.tasklastupdate = tasklastupdate;
	}

	
}
