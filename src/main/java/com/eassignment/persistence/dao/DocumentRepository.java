package com.eassignment.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eassignment.persistence.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	
}
