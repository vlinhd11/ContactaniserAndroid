package csse3005.contactaniser.models;

import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class User_Task.
 */
public class User_Task {

	/** The usertaskuserid. */
	private int usertaskuserid;
	
	/** The usertasktaskid. */
	private int usertasktaskid;
	
	/** The usertasklastupdate. */
	private Date usertasklastupdate;
	
	/**
	 * Gets the uT uid.
	 *
	 * @return the uT uid
	 */
	public long getUTUid() {
		return usertaskuserid;
	}
	
	/**
	 * Sets the uT uid.
	 *
	 * @param usertaskuserid the new uT uid
	 */
	public void setUTUid(int usertaskuserid) {
		this.usertaskuserid = usertaskuserid;
	}
	
	/**
	 * Gets the uT tid.
	 *
	 * @return the uT tid
	 */
	public long getUTTid() {
		return usertasktaskid;
	}
	
	/**
	 * Sets the uT tid.
	 *
	 * @param usertasktaskid the new uT tid
	 */
	public void setUTTid(int usertasktaskid) {
		this.usertasktaskid = usertasktaskid;
	}
	
	/**
	 * Gets the uT last update.
	 *
	 * @return the uT last update
	 */
	public Date getUTLastUpdate(){
		return usertasklastupdate;
	}
	
	/**
	 * Sets the uT last update.
	 *
	 * @param usertasklastupdate the new uT last update
	 */
	public void setUTLastUpdate(Date usertasklastupdate){
		this.usertasklastupdate = usertasklastupdate;
	}
	
}
