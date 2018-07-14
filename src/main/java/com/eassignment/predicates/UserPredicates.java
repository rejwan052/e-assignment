package com.eassignment.predicates;

import com.eassignment.persistence.model.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class UserPredicates {
	
	private UserPredicates(){}
	
	
	public static Predicate emailOrFirstNameOrLastNameContainsIgnoreCase(String searchTerm) {
		
		BooleanBuilder b = new BooleanBuilder();
		QUser qUser = QUser.user;
		
        if (searchTerm == null || searchTerm.isEmpty()) {
            return b;
        }else {
        	final String[] parts = searchTerm.split("\\s+");
        	
        	BooleanBuilder firstNameBooleanBuilder = new BooleanBuilder();
    		if(parts.length >= 2){
    			for (String string : parts) {
    				firstNameBooleanBuilder = firstNameBooleanBuilder.or(qUser.firstName.containsIgnoreCase(string));
    			}
    		}else{
    			firstNameBooleanBuilder = firstNameBooleanBuilder.or(qUser.firstName.containsIgnoreCase(searchTerm));
    		}
    		
    		BooleanBuilder lastNameBooleanBuilder = new BooleanBuilder();
    		if(parts.length >= 2){
    			for (String string : parts) {
    				lastNameBooleanBuilder = lastNameBooleanBuilder.or(qUser.lastName.containsIgnoreCase(string));
    			}
    		}else{
    			lastNameBooleanBuilder = lastNameBooleanBuilder.or(qUser.lastName.containsIgnoreCase(searchTerm));
    		}
    		
            return b.and(qUser.email.containsIgnoreCase(searchTerm).or(firstNameBooleanBuilder).or(lastNameBooleanBuilder));
        }
    }

}
