package com.eassignment.predicates;

import com.eassignment.persistence.model.QAssignment;
import com.eassignment.persistence.model.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class AssignmentPredicates {
	
	private AssignmentPredicates(){}
	
	public static Predicate getAssignmentsByUserOrTitleOrStatus(User user,String searchTerm,Boolean status){
		
		BooleanBuilder b = new BooleanBuilder();
		QAssignment qAssignment = QAssignment.assignment;
		
		if ( (searchTerm == null || searchTerm.isEmpty()) && status == null) {
			
			b = b.and(qAssignment.user.id.eq(user.getId()));
			
        	return b;
        }else if((searchTerm != null || !searchTerm.isEmpty()) && status == null){
        	
        	b = b.and(qAssignment.title.containsIgnoreCase(searchTerm)
					.or(qAssignment.session.containsIgnoreCase(searchTerm))
					.and(qAssignment.user.id.eq(user.getId())));
        	
        	return b;
        }else if((searchTerm == null || searchTerm.isEmpty()) && status != null){
        	
        	b = b.and(qAssignment.status.eq(status)
					.and(qAssignment.user.id.eq(user.getId())));
        	
        	return b;
        }else if((searchTerm != null || !searchTerm.isEmpty()) && status != null){
        	b = b.and(qAssignment.title.containsIgnoreCase(searchTerm)
					.or(qAssignment.session.containsIgnoreCase(searchTerm))
					.or(qAssignment.status.eq(status))
					.and(qAssignment.user.id.eq(user.getId())));
        	
        	return b;
        }else{
        	return b;
        }
		
	}

}
