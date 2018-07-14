package com.eassignment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.AssignmentStudent;
import com.eassignment.web.dto.AssignmentInfoDTO;

public class AssignmentMapper {

	static List<AssignmentInfoDTO> mapEntitiesIntoDTOs(List<Assignment> entities) {
		return entities.stream().map(AssignmentMapper::mapEntityIntoDTO).collect(Collectors.toList());
	}

	
	public static AssignmentInfoDTO mapEntityIntoDTO(Assignment assignment) {
		AssignmentInfoDTO infoDto = new AssignmentInfoDTO();
		
		infoDto.setId(assignment.getId());
        infoDto.setTitle(assignment.getTitle());
        infoDto.setSession(assignment.getSession());
        infoDto.setSubmitStartDate(assignment.getSubmitStartDate());
        infoDto.setSubmitEndDate(assignment.getSubmitEndDate());
        infoDto.setEmails(assignment.getAssignmentStudents());
        infoDto.setStatus(assignment.getStatus());

        if (assignment.getAssignmentStudents().size()>0){
        	
            infoDto.setTotalNumberOfStudent(assignment.getAssignmentStudents().size());

            int count=0;

            for (AssignmentStudent a: assignment.getAssignmentStudents() ) {
                if (a.isStatus()){
                    count ++;
                }
            }
            infoDto.setTotalNumberOfSubmittedStudent(count);
        }
        
        if (assignment.getAssignmentDocuments().size()>0) {
        	infoDto.setAssignmentDocuments(assignment.getAssignmentDocuments().size());
		}

		return infoDto;
	}

	public static Page<AssignmentInfoDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Assignment> source) {
		List<AssignmentInfoDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
		return new PageImpl<>(dtos, page, source.getTotalElements());
	}
	
}
