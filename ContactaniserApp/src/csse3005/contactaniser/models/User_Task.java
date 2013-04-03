package csse3005.contactaniser.models;

import java.sql.Date;

public class User_Task {

	private int usertaskuserid;
	private int usertasktaskid;
	private Date usertasklastupdate;
	
	public long getUTUid() {
		return usertaskuserid;
	}
	
	public void setUTUid(int usertaskuserid) {
		this.usertaskuserid = usertaskuserid;
	}
	
	public long getUTTid() {
		return usertasktaskid;
	}
	
	public void setUTTid(int usertasktaskid) {
		this.usertasktaskid = usertasktaskid;
	}
	
	public Date getUTLastUpdate(){
		return usertasklastupdate;
	}
	
	public void setUTLastUpdate(Date usertasklastupdate){
		this.usertasklastupdate = usertasklastupdate;
	}
	
}
