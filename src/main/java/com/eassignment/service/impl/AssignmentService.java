package com.eassignment.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eassignment.mapper.AssignmentMapper;
import com.eassignment.persistence.dao.AssignmentRepository;
import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.User;
import com.eassignment.predicates.AssignmentPredicates;
import com.eassignment.service.IAssignmentService;
import com.eassignment.web.dto.AssignmentInfoDTO;
import com.querydsl.core.types.Predicate;

@Service
@Transactional
public class AssignmentService implements IAssignmentService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Override
	public Page<AssignmentInfoDTO> getUserAssignmentsByTitleOrStatus(User user, String searchTerm,Boolean status,Pageable pageable) {
		
		Predicate userAssignmentsPredicate = AssignmentPredicates.getAssignmentsByUserOrTitleOrStatus(user, searchTerm, status);
		LOGGER.info("assignment search predicate : "+userAssignmentsPredicate);
		
		Page<Assignment> assignments = assignmentRepository.findAll(userAssignmentsPredicate,pageable);
		
		return AssignmentMapper.mapEntityPageIntoDTOPage(pageable, assignments);
	}

}
