package csse3005.contactaniser.models;

import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class User_Project.
 */
public class User_Project {
	
	/** The userprojectid. */
	private long userprojectid;
	
	/** The userprojectuserid. */
	private int userprojectuserid;
	
	/** The userprojectprojectid. */
	private int userprojectprojectid;
	
	/** The userprojectrole. */
	private String userprojectrole;
	
	/** The userprojectlastupdate. */
	private Date userprojectlastupdate;
	
	/** The userprojectstatus. */
	private String userprojectstatus;

	//Will be used by array adapter in the list view
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return userprojectrole;
	}
	
	/**
	 * Gets the User Project id.
	 *
	 * @return the User Project id
	 */
	public long getUPid() {
		return userprojectid;
	}
	
	/**
	 * Sets the User Project id.
	 *
	 * @param userprojectid the new uP id
	 */
	public void setUPid(long userprojectid) {
		this.userprojectid = userprojectid;
	}
	
	/**
	 * Gets the UP User id.
	 *
	 * @return the User Project User id
	 */
	public long getUPUid() {
		return userprojectuserid;
	}
	
	/**
	 * Sets the UP User id.
	 *
	 * @param userprojectuserid the new uP uid
	 */
	public void setUPUid(int userprojectuserid) {
		this.userprojectuserid = userprojectuserid;
	}
	
	/**
	 * Gets the UP Project id.
	 *
	 * @return the uP pid
	 */
	public long getUPPid() {
		return userprojectprojectid;
	}
	
	/**
	 * Sets the UP Project id.
	 *
	 * @param userprojectprojectid the new uP pid
	 */
	public void setUPPid(int userprojectprojectid) {
		this.userprojectprojectid = userprojectprojectid;
	}
	
	
	/**
	 * Gets the user project role.
	 *
	 * @return the user project role
	 */
	public String getUserProjectRole() {
		return userprojectrole;
	}
	
	/**
	 * Sets the user project role.
	 *
	 * @param userprojectrole the new user project role
	 */
	public void setUserProjectRole(String userprojectrole) {
		this.userprojectrole = userprojectrole;
	}
	
	/**
	 * Gets the user project last update.
	 *
	 * @return the user project last update
	 */
	public Date getUserProjectLastUpdate() {
		return userprojectlastupdate;
	}
	
	/**
	 * Sets the user project last update.
	 *
	 * @param userprojectlastupdate the new user project last update
	 */
	public void setUserProjectLastUpdate(Date userprojectlastupdate) {
		this.userprojectlastupdate = userprojectlastupdate;
	}
	
	/**
	 * Gets the user project status.
	 *
	 * @return the user project Status
	 */
	public String getUserProjectStatus() {
		return userprojectstatus;
	}
	
	/**
	 * Sets the user project status.
	 *
	 * @param userprojectrole the new user project status
	 */
	public void setUserProjectStatus(String userprojectstatus) {
		this.userprojectstatus = userprojectstatus;
	}
	

}
