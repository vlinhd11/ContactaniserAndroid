package csse3005.contactaniser.models;

import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class User_Project.
 */
public class User_Project {
	
	/** The userprojectuserid. */
	private int userprojectuserid;
	
	/** The userprojectprojectid. */
	private int userprojectprojectid;
	
	/** The userprojectrole. */
	private String userprojectrole;
	
	/** The userprojectlastupdate. */
	private Date userprojectlastupdate;

	//Will be used by array adapter in the list view
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return userprojectrole;
	}
	
	/**
	 * Gets the uP uid.
	 *
	 * @return the uP uid
	 */
	public long getUPUid() {
		return userprojectuserid;
	}
	
	/**
	 * Sets the uP uid.
	 *
	 * @param userprojectuserid the new uP uid
	 */
	public void setUPUid(int userprojectuserid) {
		this.userprojectuserid = userprojectuserid;
	}
	
	/**
	 * Gets the uP pid.
	 *
	 * @return the uP pid
	 */
	public long getUPPid() {
		return userprojectprojectid;
	}
	
	/**
	 * Sets the uP pid.
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
	

}
