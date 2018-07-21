package com.eassignment.web.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class AssignmentDTO implements Serializable{

	private static final long serialVersionUID = -3440398920608049592L;

	private long id;
	
	@NotNull(message="{NotNull.assignmentDto.title}")
    @Size(min = 1,max=50,message="{Size.assignmentDto.title}")
	private String title;
	
	/*@NotNull(message="{NotNull.assignmentDto.session}")
	@NotEmpty(message="{NotEmpty.assignmentDto.session}")*/
	private String session;
	
	@NotNull(message="{NotNull.assignmentDto.submitStartDate}")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat ( pattern="yyyy-MM-dd HH:mm")
	private Date submitStartDate;
	
	@NotNull(message="{NotNull.assignmentDto.submitEndDate}")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat ( pattern="yyyy-MM-dd HH:mm")
	private Date submitEndDate;
	
	private Boolean status;
	
	private String instructions;
	
	private String emailsTo;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getSession() {
		return session;
	}


	public void setSession(String session) {
		this.session = session;
	}


	public Date getSubmitStartDate() {
		return submitStartDate;
	}


	public void setSubmitStartDate(Date submitStartDate) {
		this.submitStartDate = submitStartDate;
	}


	public Date getSubmitEndDate() {
		return submitEndDate;
	}


	public void setSubmitEndDate(Date submitEndDate) {
		this.submitEndDate = submitEndDate;
	}


	public String getInstructions() {
		return instructions;
	}


	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public String getEmailsTo() {
		return emailsTo;
	}


	public void setEmailsTo(String emailsTo) {
		this.emailsTo = emailsTo;
	}
	
}
