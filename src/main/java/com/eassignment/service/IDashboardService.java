package com.eassignment.service;

import com.eassignment.web.dto.DashboardInfo;
import com.eassignment.web.dto.TeacherAssignmentInfo;

public interface IDashboardService {
	
	DashboardInfo getEassignmentUserInformation();
	
	TeacherAssignmentInfo getTeacherAssignmentInfo(long userId);

}
