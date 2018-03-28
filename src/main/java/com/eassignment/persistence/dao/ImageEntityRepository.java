package com.eassignment.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eassignment.persistence.model.ImageEntity;
import com.eassignment.persistence.model.User;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Long> {
	
	ImageEntity findByUser(User user);
	
	ImageEntity findByUserId(Long userId);

}
