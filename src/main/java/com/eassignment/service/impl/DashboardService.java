package com.eassignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eassignment.persistence.model.User;
import com.eassignment.service.IDashboardService;
import com.eassignment.web.dto.DashboardInfo;
import com.eassignment.web.dto.TeacherAssignmentInfo;

@Service
public class DashboardService implements IDashboardService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AssignmentService assignmentService;
	
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

	@Override
	public TeacherAssignmentInfo getTeacherAssignmentInfo(long userId) {
		
		TeacherAssignmentInfo assignmentInfo = new TeacherAssignmentInfo();
		assignmentInfo.setTotal(assignmentService.countAssignmentByUserId(userId));
		assignmentInfo.setSaved(assignmentService.countAssignmentByUserIdAndStatus(userId, false));
		assignmentInfo.setPublished(assignmentService.countAssignmentByUserIdAndStatus(userId, true));
		
		return assignmentInfo;
	}
	
	
	
	

}
