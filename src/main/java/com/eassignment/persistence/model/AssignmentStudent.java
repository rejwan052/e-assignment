package com.eassignment.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="assignmentstudent")
public class AssignmentStudent {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String email;
	
	private boolean status;
	
	private boolean isDateChange;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat ( pattern="M/dd/yyyy hh:mm a")
	private Date submitDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat ( pattern="M/dd/yyyy hh:mm a")
	private Date submitStartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat ( pattern="M/dd/yyyy hh:mm a")
	private Date submitEndDate;
	
	@ManyToOne
    @JoinColumn(name = "assignment_id")
	private Assignment assignment;
	
	/*@OneToOne
	@JoinColumn(name="groupId")
	private Group group;*/
	

	public AssignmentStudent() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public boolean isDateChange() {
		return isDateChange;
	}

	public void setDateChange(boolean isDateChange) {
		this.isDateChange = isDateChange;
	}

	@Override
	public String toString() {
		String result = String.format("AssignmentStudent[id=%d, email='%s',assignmentId=%d]%n",id, email,assignment.getId());
		return result;
	}

	/*public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}*/

	

}
