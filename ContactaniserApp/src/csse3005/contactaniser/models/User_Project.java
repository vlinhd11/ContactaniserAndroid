package csse3005.contactaniser.models;

import java.sql.Date;

public class User_Project {
	
	private int userprojectuserid;
	private int userprojectprojectid;
	private String userprojectrole;
	private Date userprojectlastupdate;

	//Will be used by array adapter in the list view
	@Override
	public String toString() {
		return userprojectrole;
	}
	
	public long getUPUid() {
		return userprojectuserid;
	}
	
	public void setUPUid(int userprojectuserid) {
		this.userprojectuserid = userprojectuserid;
	}
	
	public long getUPPid() {
		return userprojectprojectid;
	}
	
	public void setUPPid(int userprojectprojectid) {
		this.userprojectprojectid = userprojectprojectid;
	}
	
	
	public String getUserProjectRole() {
		return userprojectrole;
	}
	
	public void setUserProjectRole(String userprojectrole) {
		this.userprojectrole = userprojectrole;
	}
	
	public Date getUserProjectLastUpdate() {
		return userprojectlastupdate;
	}
	
	public void setUserProjectLastUpdate(Date userprojectlastupdate) {
		this.userprojectlastupdate = userprojectlastupdate;
	}
	

}
