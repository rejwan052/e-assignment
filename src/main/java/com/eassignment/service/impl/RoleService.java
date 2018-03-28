package com.eassignment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eassignment.persistence.dao.RoleRepository;
import com.eassignment.persistence.model.Role;
import com.eassignment.service.IRoleService;

@Service
@Transactional
public class RoleService implements IRoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(long id) {
		return roleRepository.findOne(id);
	}

	
}
