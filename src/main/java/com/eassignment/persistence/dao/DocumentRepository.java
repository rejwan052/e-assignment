package com.eassignment.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import com.eassignment.persistence.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>,QueryDslPredicateExecutor<Document> {

	
}
