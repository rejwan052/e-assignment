package com.eassignment.service;

import java.util.List;

import com.eassignment.persistence.model.Role;

public interface IRoleService {
	
	List<Role> getAllRoles();
	
	Role findById(long id);

}
