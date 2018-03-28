package com.eassignment.web.dto;

public class DashboardInfo {
	
	private long totalUser;
	
	private long totalOrganization;
	
	private long totalTeacher;
	
	private long totalStudent;

	public long getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(long totalUser) {
		this.totalUser = totalUser;
	}

	public long getTotalOrganization() {
		return totalOrganization;
	}

	public void setTotalOrganization(long totalOrganization) {
		this.totalOrganization = totalOrganization;
	}

	public long getTotalTeacher() {
		return totalTeacher;
	}

	public void setTotalTeacher(long totalTeacher) {
		this.totalTeacher = totalTeacher;
	}

	public long getTotalStudent() {
		return totalStudent;
	}

	public void setTotalStudent(long totalStudent) {
		this.totalStudent = totalStudent;
	}

	@Override
	public String toString() {
		return "DashboardInfo [totalUser=" + totalUser + ", totalOrganization=" + totalOrganization + ", totalTeacher="
				+ totalTeacher + ", totalStudent=" + totalStudent + "]";
	}

}
