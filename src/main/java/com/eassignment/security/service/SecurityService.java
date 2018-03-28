package com.eassignment.security.service;

public interface SecurityService {
	
	public Boolean hasAdminPrivilege();
	public Boolean hasTeacherPrivilege();
	public Boolean hasStudentPrivilege();
	
	public Boolean readPrivilege();

}
