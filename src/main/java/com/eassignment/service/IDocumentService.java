package com.eassignment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.Document;
import com.eassignment.web.dto.DocumentDTO;

public interface IDocumentService {
	
	Document saveOrUpdate(DocumentDTO documentDTO);
	
	Page<DocumentDTO> getAllDocumentsByAssignmenmt(Assignment assignment,Pageable pageable);
	
}
