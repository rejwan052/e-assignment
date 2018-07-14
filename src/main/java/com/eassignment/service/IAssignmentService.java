package com.eassignment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eassignment.persistence.model.User;
import com.eassignment.web.dto.AssignmentInfoDTO;

public interface IAssignmentService {
	
	Page<AssignmentInfoDTO> getUserAssignmentsByTitleOrStatus(User user,String searchTerm,Boolean status,Pageable pageable);

}
