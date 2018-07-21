package com.eassignment.service;

import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.AssignmentStudent;

public interface IAssignmentStudentService {
	
	AssignmentStudent findByEmailAndAssignment(String email,Assignment assignment);
	
	AssignmentStudent findByEmailAndAssignmentId(String email,Long assignmentId);
	
	void deleteAssignmentStudent(AssignmentStudent assignmentStudent);

}
