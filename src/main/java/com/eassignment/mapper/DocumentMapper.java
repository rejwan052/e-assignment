package com.eassignment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.eassignment.persistence.model.Document;
import com.eassignment.web.dto.DocumentDTO;

public class DocumentMapper {
	
	static List<DocumentDTO> mapEntitiesIntoDTOs(List<Document> entities) {
		return entities.stream().map(DocumentMapper::mapEntityIntoDTO).collect(Collectors.toList());
	}

	
	public static DocumentDTO mapEntityIntoDTO(Document document) {
		DocumentDTO dto = new DocumentDTO();
		
		dto.setId(document.getId());
		dto.setAssignmentId(document.getAssignment().getId());
		dto.setLocation(document.getLocation()+"\\"+document.getName());
		dto.setName(document.getName());
		dto.setSize(document.getSize());
		dto.setStatus(document.getStatus());
		dto.setType(document.getType());
		dto.setUserId(document.getUserId());
		
		return dto;
	}

	public static Page<DocumentDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Document> source) {
		List<DocumentDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
		return new PageImpl<>(dtos, page, source.getTotalElements());
	}

}
