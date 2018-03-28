package com.eassignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eassignment.persistence.dao.OrganizationRepository;
import com.eassignment.persistence.model.Organization;
import com.eassignment.service.IOrganizationService;

@Service
@Transactional
public class OrganizationService implements IOrganizationService{

	@Autowired
	private OrganizationRepository organizationRepository;
	
	
	
	@Override
	public Page<Organization> getAllOrganization(Pageable pageable) {
		// TODO Get All Organizations
		return organizationRepository.findAll(pageable);
	}

}
