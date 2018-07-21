package com.eassignment.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eassignment.persistence.dao.AssignmentStudentRepository;
import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.AssignmentStudent;
import com.eassignment.service.IAssignmentStudentService;

@Service
@Transactional
public class AssignmentStudentService implements IAssignmentStudentService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AssignmentStudentRepository assignmentStudentRepository;

	@Override
	public AssignmentStudent findByEmailAndAssignmentId(String email, Long assignmentId) {
		return assignmentStudentRepository.findByEmailAndAssignmentId(email, assignmentId);
	}

	@Override
	public AssignmentStudent findByEmailAndAssignment(String email, Assignment assignment) {
		return assignmentStudentRepository.findByEmailAndAssignment(email, assignment);
	}

	@Override
	public void deleteAssignmentStudent(AssignmentStudent assignmentStudent) {
		assignmentStudentRepository.delete(assignmentStudent);
	}

}
