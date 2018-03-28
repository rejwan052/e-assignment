package com.eassignment.web.dto;

import java.io.Serializable;

public class UserStatusDto implements Serializable{

	private static final long serialVersionUID = 6497636290339855248L;
	
	private String email;
	private String roleName;
	private boolean userCreateOrNot;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public boolean isUserCreateOrNot() {
		return userCreateOrNot;
	}
	public void setUserCreateOrNot(boolean userCreateOrNot) {
		this.userCreateOrNot = userCreateOrNot;
	}

}
