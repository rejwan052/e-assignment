package com.eassignment.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import com.eassignment.persistence.model.AssignmentStudent;


public class AssignmentInfoDTO implements Serializable {
	
	private static final long serialVersionUID = -1665064283866546070L;

	private long id;

    private String title;

    private String session;

    private Date submitStartDate;

    private Date submitEndDate;

    private String instructions;

    private int totalNumberOfStudent;

    private int totalNumberOfSubmittedStudent;
    
    private int assignmentDocuments;

    private Set<AssignmentStudent> emails;
    
    private boolean status;


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

    public int getTotalNumberOfStudent() {
        return totalNumberOfStudent;
    }

    public void setTotalNumberOfStudent(int totalNumberOfStudent) {
        this.totalNumberOfStudent = totalNumberOfStudent;
    }

    public int getTotalNumberOfSubmittedStudent() {
        return totalNumberOfSubmittedStudent;
    }

    public void setTotalNumberOfSubmittedStudent(int totalNumberOfSubmittedStudent) {
        this.totalNumberOfSubmittedStudent = totalNumberOfSubmittedStudent;
    }

    public Set<AssignmentStudent> getEmails() {
        return emails;
    }

    public void setEmails(Set<AssignmentStudent> emails) {
        this.emails = emails;
    }

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getAssignmentDocuments() {
		return assignmentDocuments;
	}

	public void setAssignmentDocuments(int assignmentDocuments) {
		this.assignmentDocuments = assignmentDocuments;
	}

}
