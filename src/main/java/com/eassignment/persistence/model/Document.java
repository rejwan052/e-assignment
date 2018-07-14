package com.eassignment.persistence.model;

import java.util.Date;

import javax.persistence.Column;
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
@Table(name = "uploaded_file")
public class Document {
	
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private Long id;
	  
	  private Long userId;
	  
	  @ManyToOne
	  @JoinColumn(name = "assignmentId")
	  private Assignment assignment;
	  
	  @Column(nullable = false)
	  private String name;
	  
	  @Column(nullable = false)
	  private String location;
	  
	  private Long size;
	  
	  @Column(nullable = false)
	  private String type;
	  
	  private int status;
	  
	  @Temporal(TemporalType.TIMESTAMP)
	  @DateTimeFormat (pattern="M/dd/yyyy hh:mm:ss a")
	  private Date createOn;
	  
	  
	  
	  public Long getId() {
	    return id;
	  }

	  
	  public String getName() {
	    return name;
	  }

	  
	  public String getLocation() {
	    return location;
	  }

	  
	  public Long getSize() {
	    return size;
	  }

	 
	  public String getType() {
	    return type;
	  }

	  public void setId(Long id) {
	    this.id = id;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public void setLocation(String location) {
	    this.location = location;
	  }

	  public void setSize(Long size) {
	    this.size = size;
	  }

	  public void setType(String type) {
	    this.type = type;
	  }

	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	  
	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}


	public Assignment getAssignment() {
		return assignment;
	}


	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

}
