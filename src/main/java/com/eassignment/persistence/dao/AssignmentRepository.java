package com.eassignment.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import com.eassignment.persistence.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long>,QueryDslPredicateExecutor<Assignment>{

	
	
}
