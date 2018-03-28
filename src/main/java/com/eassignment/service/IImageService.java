package com.eassignment.service;

import com.eassignment.persistence.model.ImageEntity;
import com.eassignment.persistence.model.User;

public interface IImageService {
	
	void uploadImage(ImageEntity image);
	ImageEntity findByUser(User user);
	ImageEntity findByUserId(Long userId);

}
