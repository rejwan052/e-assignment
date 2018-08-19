package com.eassignment.web.dto;

public class AssignmentStudentSearchDTO {
	
	private long assignmentId;
	private String searchString;
	private String assignmentStatus;
	
	public long getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getAssignmentStatus() {
		return assignmentStatus;
	}
	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}
	@Override
	public String toString() {
		return "AssignmentStudentSearchDTO [assignmentId=" + assignmentId + ", searchString=" + searchString
				+ ", assignmentStatus=" + assignmentStatus + "]";
	}
}
