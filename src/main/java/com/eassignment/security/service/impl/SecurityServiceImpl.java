package com.eassignment.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.eassignment.authentication.IAuthenticationFacade;
import com.eassignment.enums.Privileges;
import com.eassignment.security.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {

	
	@Autowired
    private IAuthenticationFacade authenticationFacade;
	
	
	@Override
	public Boolean hasTeacherPrivilege() {
		
		boolean flag = authenticationFacade.getAuthentication().getAuthorities().
					   contains(new SimpleGrantedAuthority(Privileges.TEACHER_PRIVILEGE.getPrivilege()));
		
		return flag;
	}

	@Override
	public Boolean hasAdminPrivilege() {
		boolean flag = authenticationFacade.getAuthentication().getAuthorities().
				   contains(new SimpleGrantedAuthority(Privileges.ADMIN_PRIVILEGE.getPrivilege()));
	
		return flag;
	}

	@Override
	public Boolean hasStudentPrivilege() {
		boolean flag = authenticationFacade.getAuthentication().getAuthorities().
				   contains(new SimpleGrantedAuthority(Privileges.STUDENT_PRIVILEGE.getPrivilege()));
	
		return flag;
	}

	@Override
	public Boolean readPrivilege() {
		boolean flag = authenticationFacade.getAuthentication().getAuthorities().
				   contains(new SimpleGrantedAuthority(Privileges.READ_PRIVILEGE.getPrivilege()));
		
		return flag;
	}

}
