package csse3005.contactaniser.models;

import java.sql.Date;

/**
 * The Class Project is used as a model for projects retrieved from the
 * database, which will then in turn be turned in an array to access.
 */
public class Project {
	
	/** The ID for the project. */
	private long pid;
	
	/** The project name. */
	private String pName;
	
	/** The project description. */
	private String pDescription;
	
	/** The start date. */
	private Date startDate;
	
	/** The due date. */
	private Date dueDate;
	
	/** The completion. */
	private String completion;
	
	/** The last time the entry was updated. */
	private Date lastupdate;
	
	/** The status. */
	private String status;
	
	/* Will be used by the list view for the string out put of each entry
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return pName;
	}
	
	/**
	 * Gets the projectid.
	 *
	 * @return the projectid
	 */
	public long getProjectid() {
		return pid;
	}
	
	/**
	 * Sets the projectid.
	 *
	 * @param pid the new projectid
	 */
	public void setProjectid(long pid) {
		this.pid = pid;
	}
	
	/**
	 * Gets the project name.
	 *
	 * @return the project name
	 */
	public String getProjectName(){
		return pName;
	}
	
	/**
	 * Sets the project name.
	 *
	 * @param pName the new project name
	 */
	public void setProjectName(String pName){
		this.pName = pName;
	}
	
	/**
	 * Gets the project description.
	 *
	 * @return the project description
	 */
	public String getProjectDescription(){
		return pDescription;
	}
	
	/**
	 * Sets the project description.
	 *
	 * @param pDescription the new project description
	 */
	public void setProjectDescription(String pDescription){
		this.pDescription = pDescription;
	}

	/**
	 * Gets the project start date.
	 *
	 * @return the project start date
	 */
	public Date getProjectStartDate() {
		return startDate;
	}
	
	/**
	 * Sets the project start date.
	 *
	 * @param startDate the new project start date
	 */
	public void setProjectStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Gets the project due date.
	 *
	 * @return the project due date
	 */
	public Date getProjectDueDate() {
		return dueDate;
	}
	
	/**
	 * Sets the project due date.
	 *
	 * @param dueDate the new project due date
	 */
	public void setProjectDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * Gets the project completion.
	 *
	 * @return the project completion
	 */
	public String getProjectCompletion() {
		return completion;
	}
	
	/**
	 * Sets the project completion.
	 *
	 * @param completion the new project completion
	 */
	public void setProjectCompletion(String completion) {
		this.completion = completion;
	}
	
	/**
	 * Gets the project last update.
	 *
	 * @return the project last update
	 */
	public Date getProjectLastUpdate() {
		return lastupdate;
	}
	
	/**
	 * Sets the project last update.
	 *
	 * @param dueDate the new project last update
	 */
	public void setProjectLastUpdate(Date dueDate) {
		this.lastupdate = dueDate;
	}
	
	/**
	 * Gets the project status.
	 *
	 * @return the project status
	 */
	public String getProjectStatus(){
		return status;
	}
	
	/**
	 * Sets the project status.
	 *
	 * @param pDescription the new project status
	 */
	public void setProjectStatus(String status){
		this.status = status;
	}
}
