package com.eassignment.web.dto;

public class StudentAssignmentInfo {
	
	private long total;
	
	private long submitted;
	
	private long notSubmitted;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getSubmitted() {
		return submitted;
	}

	public void setSubmitted(long submitted) {
		this.submitted = submitted;
	}

	public long getNotSubmitted() {
		
		if(this.total > 0){
			this.notSubmitted = total - submitted;
		}
		return notSubmitted;
	}

	@Override
	public String toString() {
		return "StudentAssignmentInfo [total=" + total + ", submitted=" + submitted + ", notSubmitted=" + notSubmitted
				+ "]";
	}
	
}
