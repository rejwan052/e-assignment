package com.eassignment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eassignment.persistence.model.Organization;

public interface IOrganizationService {
	
	Page<Organization> getAllOrganization(Pageable pageable);

}
