package com.eassignment.predicates;

import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.QDocument;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class DocumentPredicates {
	
	private DocumentPredicates(){}
	
	public static Predicate getAssignmentDocumentsByAssignment(Assignment assignment){
		
		BooleanBuilder b = new BooleanBuilder();
		QDocument qDocument = QDocument.document;
		
		if(null != assignment){
			b = b.and(qDocument.assignment.eq(assignment));
		}
		
		return b;
	}

}
