package com.eassignment.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eassignment.persistence.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    void delete(Role role);

    List<Role> findAll();
    
    
}
