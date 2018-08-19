package com.eassignment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.eassignment.persistence.model.AssignmentStudent;
import com.eassignment.web.dto.AssignmentStudentInfo;

public class AssignmentStudentMapper {

	static List<AssignmentStudentInfo> mapEntitiesIntoDTOs(List<AssignmentStudent> entities) {
		return entities.stream().map(AssignmentStudentMapper::mapEntityIntoDTO).collect(Collectors.toList());
	}

	
	public static AssignmentStudentInfo mapEntityIntoDTO(AssignmentStudent assignmentStudent) {
		
		AssignmentStudentInfo assignmentStudentInfo = new AssignmentStudentInfo();
		
		assignmentStudentInfo.setAssignmentId(assignmentStudent.getAssignment().getId());
		assignmentStudentInfo.setAssignmentStudentId(assignmentStudent.getId());
		assignmentStudentInfo.setEmail(assignmentStudent.getEmail());
		assignmentStudentInfo.setStatus(assignmentStudent.isStatus());
		assignmentStudentInfo.setStartDate(assignmentStudent.getSubmitStartDate());
		assignmentStudentInfo.setLastDateOfSubmission(assignmentStudent.getSubmitEndDate());
		assignmentStudentInfo.setSubmitedDate(assignmentStudent.getSubmitDate());

		return assignmentStudentInfo;
	}

	public static Page<AssignmentStudentInfo> mapEntityPageIntoDTOPage(Pageable page, Page<AssignmentStudent> source) {
		List<AssignmentStudentInfo> dtos = mapEntitiesIntoDTOs(source.getContent());
		return new PageImpl<>(dtos, page, source.getTotalElements());
	}
	
}
