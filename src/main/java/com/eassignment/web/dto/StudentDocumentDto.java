package com.eassignment.web.dto;

import java.io.Serializable;

public class StudentDocumentDto implements Serializable{

	private static final long serialVersionUID = -2556798267710715760L;
	
	private Long id;

	private Long userId;
	
	private Long assignmentId;
	
	private Long assignmentStudentId;

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

	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public Long getAssignmentStudentId() {
		return assignmentStudentId;
	}

	public void setAssignmentStudentId(Long assignmentStudentId) {
		this.assignmentStudentId = assignmentStudentId;
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

}
