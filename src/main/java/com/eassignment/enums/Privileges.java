package com.eassignment.enums;

public enum Privileges {
	
	ADMIN_PRIVILEGE("ADMIN_PRIVILEGE"),
	TEACHER_PRIVILEGE("TEACHER_PRIVILEGE"),
	STUDENT_PRIVILEGE("STUDENT_PRIVILEGE"),
	READ_PRIVILEGE("READ_PRIVILEGE"),
	WRITE_PRIVILEGE("WRITE_PRIVILEGE");
	
	private final String privilege;
	
	
	private Privileges(String privilege) {
		this.privilege = privilege;
	}
	
	public String getPrivilege(){
		return privilege;
	}

}
