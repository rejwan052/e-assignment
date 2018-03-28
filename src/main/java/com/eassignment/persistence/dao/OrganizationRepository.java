package com.eassignment.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eassignment.persistence.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {

}
