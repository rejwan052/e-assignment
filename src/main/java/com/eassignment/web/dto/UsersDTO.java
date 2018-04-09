package com.eassignment.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.eassignment.persistence.model.Role;

public class UsersDTO implements Serializable{

	private static final long serialVersionUID = 5945736079040320801L;
	
	@NotNull(message="Email can't null")
	@NotEmpty(message="Email can't empty")
	private String emails;
	
	@Size(min = 1,message="Please select a role")
	private List<Role> roles;
	

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
