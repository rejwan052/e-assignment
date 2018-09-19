package com.eassignment.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.User;

public interface AssignmentRepository extends JpaRepository<Assignment, Long>,QueryDslPredicateExecutor<Assignment>{

	Assignment findByUserAndTitleIgnoreCase(User user,String title);
	
	Assignment findByIdAndUserId(long id,long userId);
	
	long countByUserId(long userid);
	
	long countByUserIdAndStatus(long userid,boolean status);
	
}
