package com.eassignment.web.dto;

import java.io.Serializable;
import java.util.Date;

public class AssignmentStudentInfo implements Serializable{

	
	private static final long serialVersionUID = 1159016747084872844L;
	
	private long assignmentId;
	private long assignmentStudentId;
	private String name;
	private String email;
	private Boolean status;
	private Date submitedDate;
	private Date startDate;
	private Date lastDateOfSubmission;
	
	
	public long getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getSubmitedDate() {
		return submitedDate;
	}
	public void setSubmitedDate(Date submitedDate) {
		this.submitedDate = submitedDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getLastDateOfSubmission() {
		return lastDateOfSubmission;
	}
	public void setLastDateOfSubmission(Date lastDateOfSubmission) {
		this.lastDateOfSubmission = lastDateOfSubmission;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public long getAssignmentStudentId() {
		return assignmentStudentId;
	}
	public void setAssignmentStudentId(long assignmentStudentId) {
		this.assignmentStudentId = assignmentStudentId;
	}
	
}
