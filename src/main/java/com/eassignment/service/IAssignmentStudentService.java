package com.eassignment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.AssignmentStudent;
import com.eassignment.web.dto.AssignmentStudentInfo;
import com.eassignment.web.dto.AssignmentStudentSearchDTO;

public interface IAssignmentStudentService {
	
	AssignmentStudent findByEmailAndAssignment(String email,Assignment assignment);
	
	AssignmentStudent findByEmailAndAssignmentId(String email,Long assignmentId);
	
	void deleteAssignmentStudent(AssignmentStudent assignmentStudent);
	
	Page<AssignmentStudentInfo> getAssignmentStudentsByAssignment(Assignment assignment,AssignmentStudentSearchDTO assignmentStudentSearchDTO,Pageable pageable);
	
}
