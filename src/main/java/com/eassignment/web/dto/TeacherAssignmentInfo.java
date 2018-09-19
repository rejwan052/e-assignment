package com.eassignment.web.dto;

public class TeacherAssignmentInfo {
	
	private long total;
	
	private long saved;
	
	private long published;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getSaved() {
		return saved;
	}

	public void setSaved(long saved) {
		this.saved = saved;
	}

	public long getPublished() {
		return published;
	}

	public void setPublished(long published) {
		this.published = published;
	}

	@Override
	public String toString() {
		return "AssignmentInfo [total=" + total + ", saved=" + saved + ", published=" + published + "]";
	}
	
}
