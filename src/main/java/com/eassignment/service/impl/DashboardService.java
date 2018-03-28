package com.eassignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eassignment.service.IDashboardService;
import com.eassignment.web.dto.DashboardInfo;

@Service
public class DashboardService implements IDashboardService {
	
	@Autowired
	private UserService userService;
	
	@Transactional(readOnly=true)
	@Override
	public DashboardInfo getEassignmentUserInformation() {
		
		DashboardInfo dashboardInfo = new DashboardInfo();
		dashboardInfo.setTotalUser(userService.countAllUser());
		dashboardInfo.setTotalOrganization(0);
		dashboardInfo.setTotalTeacher(userService.countByRoleName("ROLE_TEACHER"));
		dashboardInfo.setTotalStudent(userService.countByRoleName("ROLE_STUDENT"));
		
		return dashboardInfo;
	}
	
	
	
	

}
