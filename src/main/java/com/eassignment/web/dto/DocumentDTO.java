package com.eassignment.web.dto;

import java.io.Serializable;

public class DocumentDTO implements Serializable {

	private static final long serialVersionUID = 4500602900704140946L;

	private Long id;

	private Long userId;
	
	private Long assignmentId;

	private String name;

	private String location;

	private Long size;

	private String type;

	private int status;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}
	
	@Override
	public String toString() {
		return "DocumentDto [id=" + id + ", userId=" + userId + ", assignmentId=" + assignmentId + ", name=" + name
				+ ", location=" + location + ", size=" + size + ", type=" + type + ", status=" + status + "]";
	}
	
}
