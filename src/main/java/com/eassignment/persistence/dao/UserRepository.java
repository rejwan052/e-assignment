package com.eassignment.persistence.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.eassignment.persistence.model.User;

public interface UserRepository extends JpaRepository<User, Long>,QueryDslPredicateExecutor<User>{
	
    User findByEmail(String email);
    void delete(User user);   
    List<User> findByRolesName(String name);
    
    List<User> findByRolesNameAndEmailIgnoreCaseContainingOrFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(String roleName,String email,String firstName,String lastName);
    
    //Query for User jqGrid table
    Page<User> findByEmailLike(String email,Pageable pageable);
    Page<User> findByFirstNameLike(String firstName,Pageable pageable);
    Page<User> findByLastNameLike(String lastName,Pageable pageable);
    Page<User> findByFirstNameLikeAndLastNameLike(String firstName, String lastName, Pageable pageable);
    
    
    long count();
    
    long countByRolesName(String name);
    
}
