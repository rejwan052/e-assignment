package com.eassignment.persistence.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="assignment")
public class Assignment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String title;
	
	private String session;
	
	private Boolean status;
	
	@Column(columnDefinition = "TEXT")
	private String instructions;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat ( pattern="yyyy-MM-dd HH:mm")
	private Date submitStartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat ( pattern="yyyy-MM-dd HH:mm")
	private Date submitEndDate;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat ( pattern="yyyy-MM-dd hh:mm:ss a")
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat ( pattern="yyyy-MM-dd hh:mm:ss a")
	private Date modifiedDate;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<AssignmentStudent> assignmentStudents;
	
	@OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Document> assignmentDocuments;
	

	public Assignment() {
	}


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


	public String getInstructions() {
		return instructions;
	}


	public void setInstructions(String instructions) {
		this.instructions = instructions;
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


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Set<AssignmentStudent> getAssignmentStudents() {
		return assignmentStudents;
	}


	public void setAssignmentStudents(Set<AssignmentStudent> assignmentStudents) {
		this.assignmentStudents = assignmentStudents;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}

	
	

	public List<Document> getAssignmentDocuments() {
		return assignmentDocuments;
	}


	public void setAssignmentDocuments(List<Document> assignmentDocuments) {
		this.assignmentDocuments = assignmentDocuments;
	}


	@Override
	public String toString() {
		return "Assignment [id=" + id + ", title=" + title + ", session=" + session + ", status=" + status
				+ ", instructions=" + instructions + ", submitStartDate=" + submitStartDate + ", submitEndDate="
				+ submitEndDate + ", createDate=" + createDate + ", modifiedDate=" + modifiedDate + ", user=" + user
				+ ", assignmentStudents=" + assignmentStudents + ", assignmentDocuments=" + assignmentDocuments + "]";
	}

}
