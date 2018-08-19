package com.eassignment.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eassignment.mapper.AssignmentStudentMapper;
import com.eassignment.persistence.dao.AssignmentStudentRepository;
import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.AssignmentStudent;
import com.eassignment.predicates.AssignmentStudentPredicates;
import com.eassignment.service.IAssignmentStudentService;
import com.eassignment.web.dto.AssignmentStudentInfo;
import com.eassignment.web.dto.AssignmentStudentSearchDTO;
import com.querydsl.core.types.Predicate;

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

	@Override
	public Page<AssignmentStudentInfo> getAssignmentStudentsByAssignment(Assignment assignment,AssignmentStudentSearchDTO assignmentStudentSearchDTO, Pageable pageable) {
		
		Predicate assignmentStudentsPredicate = AssignmentStudentPredicates.getAssignmentStudentsByAssignment(assignment,assignmentStudentSearchDTO);
		
		Page<AssignmentStudent> assignmentStudents = assignmentStudentRepository.findAll(assignmentStudentsPredicate, pageable);
		
		return AssignmentStudentMapper.mapEntityPageIntoDTOPage(pageable, assignmentStudents);
	}

}
