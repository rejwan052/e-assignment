package com.eassignment.predicates;

import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.QAssignmentStudent;
import com.eassignment.web.dto.AssignmentStudentSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class AssignmentStudentPredicates {
	
	
	public static Predicate getAssignmentStudentsByAssignment(Assignment assignment,AssignmentStudentSearchDTO assignmentStudentSearchDTO){
		
		BooleanBuilder b = new BooleanBuilder();
		QAssignmentStudent qAssignmentStudent = QAssignmentStudent.assignmentStudent;
		if (null != assignment && null == assignmentStudentSearchDTO) {
			b = b.and(qAssignmentStudent.assignment.eq(assignment));
		}else if(null != assignmentStudentSearchDTO){
			
			b = b.and(qAssignmentStudent.assignment.id.eq(assignmentStudentSearchDTO.getAssignmentId()));
			
			if(null != assignmentStudentSearchDTO.getSearchString() && !assignmentStudentSearchDTO.getSearchString().isEmpty()){
				b = b.and(qAssignmentStudent.email.containsIgnoreCase(assignmentStudentSearchDTO.getSearchString()));
			}
			
			if(null != assignmentStudentSearchDTO.getAssignmentStatus()){
				
				String status = assignmentStudentSearchDTO.getAssignmentStatus();
				
				switch (status) {
				case "Submitted":
					b = b.and(qAssignmentStudent.status.eq(true));
					break;
				case "NotSubmitted":
					b = b.and(qAssignmentStudent.status.eq(false));
					break;
				default:
					break;
				}
				
			}
		}
		
		return b;
		
	}

}
