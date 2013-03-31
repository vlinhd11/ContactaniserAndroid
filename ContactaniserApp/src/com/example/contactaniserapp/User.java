package com.example.contactaniserapp;

public class User {
	private int userid;
	private String user_username;
	private String username;
	private int userphonenumber;
	private String useremail;
	
	//Will be used by array adapter in the list view
	@Override
	public String toString() {
		return username;
	}
	
	public long getUserid() {
		return userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public String getUser_UserName() {
		return user_username;
	}
	
	public void setUser_UserName(String user_username) {
		this.user_username = user_username;
	}
	
	public String getUserName() {
		return username;
	}
	
	public void setUserName(String username) {
		this.username = username;
	}
	
	public int getUserPhoneNumber() {
		return userphonenumber;
	}
	
	public void setUserPhoneNumber(int userphonenumber){
		this.userphonenumber = userphonenumber;
	}
	
	public String getUserEmail() {
		return useremail;
	}
	
	public void setUserEmail(String useremail) {
		this.useremail = useremail;
	}
	
}

