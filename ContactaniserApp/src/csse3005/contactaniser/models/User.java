package csse3005.contactaniser.models;

import java.sql.Date;

/**
 * The Class User is used as a model for users retrieved from the
 * database, which will then in turn be turned in an array to access.
 */
public class User {
	
	/** The userid. */
	private int userid;
	
	/** The user_username. */
	private String user_username;
	
	/** The username. */
	private String username;
	
	/** The userphonenumber. */
	private int userphonenumber;
	
	/** The useremail. */
	private String useremail;
	
	/** The userlastupdate. */
	private Date userlastupdate;
	
	//Will be used by array adapter in the list view
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return username;
	}
	
	/**
	 * Gets the userid.
	 *
	 * @return the userid
	 */
	public long getUserid() {
		return userid;
	}
	
	/**
	 * Sets the userid.
	 *
	 * @param userid the new userid
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	/**
	 * Gets the user_ user name.
	 *
	 * @return the user_ user name
	 */
	public String getUser_UserName() {
		return user_username;
	}
	
	/**
	 * Sets the user_ user name.
	 *
	 * @param user_username the new user_ user name
	 */
	public void setUser_UserName(String user_username) {
		this.user_username = user_username;
	}
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return username;
	}
	
	/**
	 * Sets the user name.
	 *
	 * @param username the new user name
	 */
	public void setUserName(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the user phone number.
	 *
	 * @return the user phone number
	 */
	public int getUserPhoneNumber() {
		return userphonenumber;
	}
	
	/**
	 * Sets the user phone number.
	 *
	 * @param userphonenumber the new user phone number
	 */
	public void setUserPhoneNumber(int userphonenumber){
		this.userphonenumber = userphonenumber;
	}
	
	/**
	 * Gets the user email.
	 *
	 * @return the user email
	 */
	public String getUserEmail() {
		return useremail;
	}
	
	/**
	 * Sets the user email.
	 *
	 * @param useremail the new user email
	 */
	public void setUserEmail(String useremail) {
		this.useremail = useremail;
	}
	
	/**
	 * Gets the user last update.
	 *
	 * @return the user last update
	 */
	public Date getUserLastUpdate() {
		return userlastupdate;
	}
	
	/**
	 * Sets the user last update.
	 *
	 * @param userlastupdate the new user last update
	 */
	public void setUserLastUpdate(Date userlastupdate){
		this.userlastupdate = userlastupdate;
	}
	
}

