package com.eassignment.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eassignment.mapper.DocumentMapper;
import com.eassignment.persistence.dao.AssignmentRepository;
import com.eassignment.persistence.dao.AssignmentStudentRepository;
import com.eassignment.persistence.dao.DocumentRepository;
import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.Document;
import com.eassignment.predicates.DocumentPredicates;
import com.eassignment.service.IDocumentService;
import com.eassignment.service.IUserService;
import com.eassignment.web.dto.DocumentDTO;
import com.querydsl.core.types.Predicate;


@Service
@Transactional
public class DocumentService implements IDocumentService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	private AssignmentStudentRepository assignmentStudentRepository;
	
	@Autowired
	private IUserService userService;
	
	
	
	@Override
	public Page<DocumentDTO> getAllDocumentsByAssignmenmt(Assignment assignment, Pageable pageable) {
		
		Predicate assignmentDocumentsPredicate = DocumentPredicates.getAssignmentDocumentsByAssignment(assignment);
		LOGGER.info("assignment document predicate : "+assignmentDocumentsPredicate);
		
		Page<Document> assignmentDocuments = documentRepository.findAll(assignmentDocumentsPredicate, pageable);
		
		return DocumentMapper.mapEntityPageIntoDTOPage(pageable, assignmentDocuments);
	}

	@Override
	public Document saveOrUpdate(DocumentDTO documentDTO) {
		Document document = new Document();
		
		document.setName(documentDTO.getName());
		document.setLocation(documentDTO.getLocation());
		document.setSize(documentDTO.getSize());
		document.setStatus(documentDTO.getStatus());
		document.setType(documentDTO.getType());
		document.setUserId(documentDTO.getUserId());
		document.setCreateOn(new Date());
		
		Assignment assignment = assignmentRepository.findOne(documentDTO.getAssignmentId());
		
		document.setAssignment(assignment);
		
		return documentRepository.save(document);
	}

}
