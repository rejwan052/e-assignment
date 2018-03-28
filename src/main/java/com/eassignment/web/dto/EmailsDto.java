package com.eassignment.web.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailsDto {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private String email;
	private String name;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		String result = String.format("%s", getEmail());
		LOGGER.info("result :"+result);
		return result;
	}
	
}
