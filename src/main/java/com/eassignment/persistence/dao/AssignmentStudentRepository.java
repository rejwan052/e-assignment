package com.eassignment.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.AssignmentStudent;

public interface AssignmentStudentRepository extends JpaRepository<AssignmentStudent, Long>,QueryDslPredicateExecutor<AssignmentStudent> {

	AssignmentStudent findByEmailAndAssignment(String email,Assignment assignment);
	
	AssignmentStudent findByEmailAndAssignmentId(String email,Long assignmentId);
	
}
