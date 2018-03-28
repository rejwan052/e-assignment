package com.eassignment.persistence.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="imageentity")
public class ImageEntity implements Serializable{


	private static final long serialVersionUID = -8587291470802277346L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private long imageId;
	 private String filename;
	 private String mimeType;
	 @Lob
	 private byte[] image;
	 
	 @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	 @MapsId
	 private User user;
	 
	
	public ImageEntity() {
		super();
	}


	public ImageEntity(String filename, String mimeType, byte[] image,User user) {
		super();
		this.filename = filename;
		this.mimeType = mimeType;
		this.image = image;
		this.user = user;
	}
	

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
