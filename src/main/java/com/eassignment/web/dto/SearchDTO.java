package com.eassignment.web.dto;

public class SearchDTO {
	
	private String searchString;
	private Boolean assignmentStatus;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Boolean getAssignmentStatus() {
		return assignmentStatus;
	}

	public void setAssignmentStatus(Boolean assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	@Override
	public String toString() {
		return "SearchDTO [searchString=" + searchString + ", assignmentStatus=" + assignmentStatus + "]";
	}
	
}
