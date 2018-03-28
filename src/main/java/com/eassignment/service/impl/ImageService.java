package com.eassignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eassignment.persistence.dao.ImageEntityRepository;
import com.eassignment.persistence.model.ImageEntity;
import com.eassignment.persistence.model.User;
import com.eassignment.service.IImageService;

@Service
@Transactional
public class ImageService implements IImageService{
	
	@Autowired
	private ImageEntityRepository  imageEntityRepository;

	@Override
	public void uploadImage(ImageEntity image) {
		imageEntityRepository.save(image);
	}

	@Override
	public ImageEntity findByUser(User user) {
		return imageEntityRepository.findByUser(user);
	}

	@Override
	public ImageEntity findByUserId(Long userId) {
		return imageEntityRepository.findByUserId(userId);
	}

}
